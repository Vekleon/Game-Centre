package fall2018.csc2017.slidingtiles.twentyfourtyeight.screens;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

import fall2018.csc2017.slidingtiles.CustomAdapter;
import fall2018.csc2017.slidingtiles.GameCentre;
import fall2018.csc2017.slidingtiles.GameScreen;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.gamestates.TwentyFourtyEightGameState;
import fall2018.csc2017.slidingtiles.twentyfourtyeight.TwentyFourtyEightManager;


public class TwentyFourtyEightGameScreen extends GameScreen {

    /**
     * The on screen score display text
     */
    private TextView scoreText;

    public void display(){
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        int score = gameManager.getScore();
        scoreText = findViewById(R.id.twenty_fourty_eight_score);
        scoreText.setText(String.valueOf(score));
    }

    /**
     * The screen initialization
     * @param savedInstanceState the bundle of data sent from the previous instance
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twenty_fourty_eight_game);
        loadFromFile();

        createTileButtons(this);
        scoreText = findViewById(R.id.twenty_fourty_eight_score);
        scoreText.setText("0");

        startTimer(R.id.twenty_fourty_eight_time);
        initGridView(R.id.twenty_fourty_eight_grid);

        //Abstracted listener declaration
        HashMap<Integer, Runnable> runnableMap = new HashMap<>();
        runnableMap.put(R.id.twenty_fourty_eight_new_game, this::newGame);
        runnableMap.put(R.id.twenty_fourty_eight_undo, this::undo);
        super.createButtonListeners(new HashMap<>(), runnableMap);
    }

    public void undo(){
        ((TwentyFourtyEightManager) gameManager).undo();
    }

    /**
     * Creates a new board to be displayed
     */
    private void newGame() {
        makeNewBoard();
        gridView.setBoardManager(gameManager);
        display();
    }

    /**
     *Make a new board for 2048
     */
    private void makeNewBoard(){
        TypedArray tileImages = getResources().obtainTypedArray(R.array.twenty_fourty_eight_tiles);
        GameCentre.getGameCentre().setIdToBitmapArray(createTileList(tileImages, 12));
        gameManager = new TwentyFourtyEightManager(4);
        gameManager.getBoard().addObserver(this);

    }

    /**
     * Load the board from the user saves file
     */
    private void loadFromFile(){
        GameCentre gameCentre = GameCentre.getGameCentre();
        TwentyFourtyEightGameState userState = (TwentyFourtyEightGameState)
                gameCentre.getCurrUserSavedGame(getGameTitle());
        if(userState == null){
            TypedArray tileImages = getResources().obtainTypedArray(R.array.twenty_fourty_eight_tiles);
            GameCentre.getGameCentre().setIdToBitmapArray(createTileList(tileImages, 12));
            gameManager = new TwentyFourtyEightManager(4);
        }
        else{
            gameManager = new TwentyFourtyEightManager(gameCentre.getCurrUserSavedGame(getGameTitle()));
        }
    }

    /**
     * returns the name of the game
     * @return the name of the game
     */
    public String getGameTitle(){
        return "2048";
    }

    @Override
    protected Class getGameOverClass(){
        return TwentyFourtyEightGameOver.class;
    }
}
