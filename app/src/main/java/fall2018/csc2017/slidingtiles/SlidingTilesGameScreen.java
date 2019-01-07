package fall2018.csc2017.slidingtiles;

import android.os.Bundle;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Observable;

import fall2018.csc2017.slidingtiles.gamestates.GameState;
import fall2018.csc2017.slidingtiles.screens.SlidingTilesGameOver;

/**
 * The game activity.
 */
public class SlidingTilesGameScreen extends GameScreen {

    /**
     * The edit text to determine the number of desired undos
     */
    private EditText undoText;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadFromFile();
        createTileButtons(this);
        setContentView(R.layout.activity_main);

        undoText = findViewById(R.id.undo_amount);

        startTimer(R.id.sliding_tiles_time);
        initGridView(R.id.grid);

        HashMap<Integer, Runnable> runnableMap = new HashMap<>();
        runnableMap.put(R.id.sliding_tiles_undo, this::undoMoves);
        createButtonListeners(new HashMap<>(), runnableMap);
    }

    /**
     * Undoes the number of moves given in undoText
     */
    private void undoMoves(){
        ((BoardManager)gameManager).undo(Integer.valueOf(undoText.getText().toString()));
    }

    /**
     * Load the board manager from fileName.
     */
    private void loadFromFile() {

        //HashMap<String, DataPacket> data = FileWriter.getGlobalUserInfo(this);
        GameCentre gameCentre = GameCentre.getGameCentre();
        //GameCentre.setAllUserData(data);
        //gameCentre.setCurrUserSavedGames(gameCentre.getCurrUser());
        GameState userState = gameCentre.getCurrUserSavedGame(getGameTitle());
        if (userState == null) {
            /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lion_main);
            ArrayList<BitmapDrawable> bmps = (new ImageParser(bitmap)).splitGridToBitmapDrawables(3);
            Bitmap blankBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tile_blank);*/
            gameManager = new BoardManager(3);
        } else {
            gameManager = new BoardManager(gameCentre.getCurrUserSavedGame(getGameTitle()));
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }

    @Override
    public String getGameTitle(){
        return "SlidingTiles";
    }

    @Override
    protected Class getGameOverClass(){
        return SlidingTilesGameOver.class;
    }
}
