package fall2018.csc2017.slidingtiles.memorytiles.screens;

import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import fall2018.csc2017.slidingtiles.Board;
import fall2018.csc2017.slidingtiles.CustomAdapter;
import fall2018.csc2017.slidingtiles.GameCentre;
import fall2018.csc2017.slidingtiles.GameScreen;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.gamestates.MemoryTileGameState;
import fall2018.csc2017.slidingtiles.memorytiles.MemoryTile;
import fall2018.csc2017.slidingtiles.memorytiles.MemoryTileBoard;
import fall2018.csc2017.slidingtiles.memorytiles.MemoryTilesManager;
/**
 * The class responsible for the functionality of the screen displayed when playing memory tiles
 *
 * @author Group_0645
 * @since 2018-11-23
 */
public class MemoryTilesGameScreen extends GameScreen {

    /**
     * The image for which the back of the tiles is to be
     */
    private BitmapDrawable blankBitmap;

    /**
     * Default complexity for memory tiles
     */
    private int complexity = 4;

    @Override
    protected void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        int score = gameManager.getScore();
        TextView scoreText = findViewById(R.id.memory_tiles_score);
        String scoreString = "   " + String.valueOf(score);
        scoreText.setText(scoreString);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory_tiles_game);

        Bundle info = getIntent().getExtras();
        complexity = info.getInt("complexity");

        blankBitmap = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.memory_tile_blank));
        TextView scoreText = findViewById(R.id.memory_tiles_score);
        scoreText.setText("0");
        makeNewBoard();
        createTileButtons(this);
        initGridView(R.id.memory_tiles_grid);

        startTimer(R.id.timeCounterMemoryTiles);

        //MemoryTilesManager mtm = new MemoryTilesManager();
        //int i = mtm.getScore();
        //boolean f = mtm.gameOver();
    }

    @Override
    protected BitmapDrawable getTileBackground(int row, int col){
        GameCentre gc = GameCentre.getGameCentre();
        Board board = gameManager.getBoard();
        MemoryTile tile = (MemoryTile)board.getTile(row, col);

        if(tile.isFlipped())
            return gc.getBitmapFromId(tile.getBackgroundId());
        else
            return blankBitmap;
    }


    /**
     * Load the board from the user saves file
     */
    private void loadFromFile(){
        GameCentre gameCentre = GameCentre.getGameCentre();
        MemoryTileGameState userState = (MemoryTileGameState)gameCentre.getCurrUserSavedGame(getGameTitle());
        if(userState == null){
            TypedArray tileImages = getResources().obtainTypedArray(R.array.memory_tile_tiles);
            GameCentre.getGameCentre().setIdToBitmapArray(createTileList(tileImages, 10));
            gameManager = new MemoryTilesManager(complexity);
        }
        else
           gameManager = new MemoryTilesManager(gameCentre.getCurrUserSavedGame(getGameTitle()));
    }

    /**
     * Generates the board for the game
     */
    private void makeNewBoard(){
        TypedArray tileImages = getResources().obtainTypedArray(R.array.memory_tile_tiles);
        GameCentre.getGameCentre().setIdToBitmapArray(createTileList(tileImages, 10));
        gameManager = new MemoryTilesManager(complexity);
        gameManager.addObserver(this);
    }

    /**
     * Getter for the title
     * @return The title of the game
     */
    public String getGameTitle(){
        return "MemoryTiles";
    }

    @Override
    protected Class getGameOverClass(){
        return MemoryTilesGameOver.class;
    }
}
