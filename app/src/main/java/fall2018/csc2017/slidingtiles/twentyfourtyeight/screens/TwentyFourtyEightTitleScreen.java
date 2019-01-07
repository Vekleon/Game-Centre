package fall2018.csc2017.slidingtiles.twentyfourtyeight.screens;

import android.content.res.TypedArray;
import android.os.Bundle;

import java.util.HashMap;

import fall2018.csc2017.slidingtiles.GameCentre;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.filewriters.FileWriter;
import fall2018.csc2017.slidingtiles.gamestates.GameState;
import fall2018.csc2017.slidingtiles.screens.Screen;
import fall2018.csc2017.slidingtiles.twentyfourtyeight.TwentyFourtyEightManager;

/**
 * The class responsible for setting up button functionality and screen transitioning for
 * the 2048 title screen
 * @author Thomas
 * @since 2018-11-24
 */
public class TwentyFourtyEightTitleScreen extends Screen {

    /**
     * The board manager
     */
    private TwentyFourtyEightManager boardManager;

    /**
     * The preliminary set up of the screen. Adds functionality to buttons and sets
     * variables
     * @param savedInstanceState the saved instance of the previous state
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twenty_fourty_eight_start);

        HashMap<Integer, Runnable> runnableMap = new HashMap<>();
        runnableMap.put(R.id.twenty_fourty_eight_start, this::newGame);
        runnableMap.put(R.id.twenty_fourty_eight_load, this::loadGame);
        runnableMap.put(R.id.twenty_fourty_eight_save, this::saveToFile);
        super.createButtonListeners(new HashMap<>(), runnableMap);
    }


    /**
     * Adds functionality to the New Game button on screen
     */
    private void newGame(){
        TypedArray tileImages = getResources().obtainTypedArray(R.array.twenty_fourty_eight_tiles);
        GameCentre.getGameCentre().setIdToBitmapArray(createTileList(tileImages, 12));
        boardManager = new TwentyFourtyEightManager(4);

        saveToFile();
        super.goToScreen(this, TwentyFourtyEightGameScreen.class);
    }


    /**
     * Loads the game
     */
    private void loadGame(){
        //Checks if a save file is present, if so load it, otherwise present a warning
        if(loadFromFile()) {
            makeToastText("Loaded Game");
            switchToGame();
        }
    }

    public void saveToFile(){
        GameCentre gameCentre = GameCentre.getGameCentre();
        gameCentre.addSavedGameToData(gameCentre.getCurrUser(), "2048",
                boardManager.getGameState());
        FileWriter.writeIntoGlobalInfo(this);
    }

    private boolean loadFromFile(){
        GameCentre gameCentre = GameCentre.getGameCentre();
        GameState userState = gameCentre.getCurrUserSavedGame("2048");
        if (userState == null) {
            makeToastText("No Save Found!");
            return false;
        } else {
            boardManager = new TwentyFourtyEightManager(gameCentre.getCurrUserSavedGame("2048"));
            return true;
        }
    }

    /**
     * Switch to the SlidingTilesGameScreen view to play the game.
     */
    private void switchToGame() {
        saveToFile();
        super.goToScreen(this, TwentyFourtyEightGameScreen.class);
    }
}
