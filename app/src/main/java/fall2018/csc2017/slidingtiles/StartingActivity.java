package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.HashMap;

import fall2018.csc2017.slidingtiles.filewriters.FileWriter;
import fall2018.csc2017.slidingtiles.gamestates.GameState;
import fall2018.csc2017.slidingtiles.gamestates.SlidingTilesGameState;
import fall2018.csc2017.slidingtiles.screens.Hub;
import fall2018.csc2017.slidingtiles.screens.ImageUpload;
import fall2018.csc2017.slidingtiles.screens.Screen;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends Screen {

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    private RadioButton radioButtonImage1;
    private RadioButton radioButtonImage2;
    private RadioButton radioButtonNumbers;

    private RadioButton radioButtonComplexity1;
    private RadioButton radioButtonComplexity2;
    private RadioButton radioButtonComplexity3;

    private static int currComplexityChecked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_);

        if(GameCentre.getGameCentre().getCustomImageBitmap() == null) {
            Bitmap newBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lion_main);
            GameCentre.getGameCentre().setCustomImageBitmap(newBitmap);
        }

        radioButtonImage1 = findViewById(R.id.radioButtonImage1);
        radioButtonImage2 = findViewById(R.id.radioButtonImage2);
        radioButtonNumbers = findViewById(R.id.radioButtonNumbers);

        radioButtonComplexity1 = findViewById(R.id.radioButtonComplexity1);
        radioButtonComplexity2 = findViewById(R.id.radioButtonComplexity2);
        radioButtonComplexity3 = findViewById(R.id.radioButtonComplexity3);

        HashMap<Integer, Class> activityMap = new HashMap<>();
        activityMap.put(R.id.uploadButton, ImageUpload.class);

        HashMap<Integer, Runnable> runnableMap = new HashMap<>();
        runnableMap.put(R.id.StartButton, this::startGame);
        runnableMap.put(R.id.LoadButton, this::loadGame);
        runnableMap.put(R.id.SaveButton, this::saveGame);

        super.createButtonListeners(activityMap, runnableMap);

        updateCustomImageView();
    }

    /**
     * Activate the start button.
     */
    private void startGame() {
        int complexity;
        if (radioButtonComplexity1.isChecked())
            complexity = 3;
        else if (radioButtonComplexity2.isChecked())
            complexity = 4;
        else
            complexity = 5;

        GameCentre.getGameCentre().setIdToBitmapArray(getTileImage(complexity));
        boardManager = new BoardManager(complexity);

        switchToGame();
    }

    /**
     * Used to find the correct image to overlay on the tiles
     *
     * @return the array of images that are to be displayed on the tiles
     */
    private ArrayList<BitmapDrawable> getTileImage(int complexity) {
        ArrayList<BitmapDrawable> tileImgs = new ArrayList<>();
        TypedArray numberTiles = getResources().obtainTypedArray(R.array.numbered_tiles);
        if (radioButtonNumbers.isChecked()) {
            for(int i = 0; i < complexity * complexity - 1; i++) {
                int background = numberTiles.getResourceId(i, 0);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), background);
                tileImgs.add(new BitmapDrawable(bitmap));
            }
        }else{
            Bitmap bitmap;
            if (radioButtonImage1.isChecked())
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lion_main);
            else if (radioButtonImage2.isChecked())
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kobe_main);
            else
                bitmap = GameCentre.getGameCentre().getCustomImageBitmap();
            // check if bitmap is null?
            ImageParser imageParser = new ImageParser(bitmap);
            tileImgs = imageParser.splitGridToBitmapDrawables(complexity);
            tileImgs.remove(tileImgs.size()-1);
        }

        Bitmap blankBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tile_blank);
        tileImgs.add(new BitmapDrawable(blankBitmap));

        numberTiles.recycle();
        return tileImgs;
    }

    @Override
    public void onBackPressed() {
        super.goToScreen(this, Hub.class );
    }

    /**
     * Loads a game
     */
    private void loadGame() {
        //Checks if a save file is present, if so load it, otherwise present a warning
        if(loadFromFile()) {
            makeToastText("Loaded Game");
            switchToGame();
        }
    }

    private void updateCustomImageView(){
        Bitmap bitmap = GameCentre.getGameCentre().getCustomImageBitmap();
        if(bitmap != null) {
            ImageView imageViewCustom = findViewById(R.id.imageViewCustom);
            imageViewCustom.setImageBitmap(bitmap);
        }
        RadioButton radioButtonCustom = findViewById(R.id.radioButtonCustom);
        radioButtonCustom.setChecked(true);
    }

    /**
     * Saves the game
     */
    private void saveGame() {
        saveToFile();
        makeToastText("Game Saved");
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile();
        updateCustomImageView();

        // Reload state of complexity selected when activity resumes
        if (currComplexityChecked == 0) {
            radioButtonComplexity1.setChecked(true);
        } else if (currComplexityChecked == 1) {
            radioButtonComplexity2.setChecked(true);
        } else {
            radioButtonComplexity3.setChecked(true);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Preserve state of complexity selected while activity is paused
        if (radioButtonComplexity1.isChecked()) {
            currComplexityChecked = 0;
        } else if (radioButtonComplexity2.isChecked()) {
            currComplexityChecked = 1;
        } else {
            currComplexityChecked = 2;
        }
    }

    /**
     * Switch to the SlidingTilesGameScreen view to play the game.
     */
    private void switchToGame() {
        saveToFile();
        super.goToScreen(this,SlidingTilesGameScreen.class );
    }

    /**
     * Load the board manager from fileName.
     */
    private boolean loadFromFile() {
        GameCentre gameCentre = GameCentre.getGameCentre();
        GameState userState = gameCentre.getCurrUserSavedGame("SlidingTiles");
        if (userState == null) {
            makeToastText("No Save Found!");
            return false;
        } else {
            gameCentre.setIdToBitmapArray(((SlidingTilesGameState)userState).getBitmapDrawables());
            boardManager = new BoardManager(gameCentre.getCurrUserSavedGame("SlidingTiles"));
            return true;
        }

    }

    /**
     * Save the board manager to fileName.
     */
    public void saveToFile() {
        GameCentre gameCentre = GameCentre.getGameCentre();
        SlidingTilesGameState currState = boardManager.getGameState();
        currState.setProxyBitmaps();
        gameCentre.addSavedGameToData(gameCentre.getCurrUser(), "SlidingTiles", currState);
        FileWriter.writeIntoGlobalInfo(this);
    }
}
