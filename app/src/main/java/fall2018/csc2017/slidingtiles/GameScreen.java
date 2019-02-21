package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.common.data.DataBufferObserver;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import fall2018.csc2017.slidingtiles.controller.CustomGridView;
import fall2018.csc2017.slidingtiles.filewriters.FileWriter;
import fall2018.csc2017.slidingtiles.screens.Screen;
import fall2018.csc2017.slidingtiles.screens.SlidingTilesGameOver;
import fall2018.csc2017.slidingtiles.twentyfourtyeight.TwentyFourtyEightManager;
import fall2018.csc2017.slidingtiles.twentyfourtyeight.screens.TwentyFourtyEightGameOver;

public abstract class GameScreen extends Screen implements Observer {

    /**
     * The buttons to display.
     */
    protected ArrayList<Button> tileButtons = new ArrayList<>();

    /**
     * The number of seconds that have elapsed since the start of the game
     */
    protected int seconds;

    /**
     * The timer responsible for incrementing the seconds that have passed
     */
    protected Timer timer;

    /**
     * The on screen timer display text
     */
    protected TextView timerText;

    // Grid View and calculated column height and width based on device size
    protected CustomGridView gridView;
    protected static int columnWidth, columnHeight;

    protected GameManager gameManager;

    protected BitmapDrawable getTileBackground(int row, int col){
        GameCentre gc = GameCentre.getGameCentre();
        Board board = gameManager.getBoard();
        return gc.getBitmapFromId(board.getTile(row, col).getBackgroundId());
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    public void createTileButtons(Context context) {
        Board board = gameManager.getBoard();
        for (int row = 0; row != board.getComplexity(); row++) {
            for (int col = 0; col != board.getComplexity(); col++) {
                Button tmp = new Button(context);
                tmp.setBackground(getTileBackground(row, col));
                tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    public void updateTileButtons() {
        Board board = gameManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / board.getComplexity();
            int col = nextPos % board.getComplexity();
            b.setBackground(getTileBackground(row, col));
            nextPos++;
        }
    }

    /**
     * Used to start the in game timer to determine how long it took the user to finish the puzzle
     */
    public void startTimer(int timerTextId) {
        timer = new Timer();
        timerText = findViewById(timerTextId);

        seconds = gameManager.getGameState().getTimeElapsed();

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        seconds += 1;

                        if (seconds % 15 == 0) {
                            //boardManager.autoSave(seconds); //saves to the game state
                            saveToFile(getGameTitle(), gameManager); //the auto save
                        }
                        if (gameManager.gameOver())
                            gotoEndState();

                        String time = seconds / 60 + ":" + (seconds % 60);
                        timerText.setText(time);
                    }
                });
            }
        }, 0, 1000);
    }

    public void saveToFile(String gameName, GameManager boardManager) {
        GameCentre gameCentre = GameCentre.getGameCentre();
        boardManager.getGameState().changeState(boardManager.getGameState().getBoard(), seconds);

        gameCentre.addSavedGameToData(gameCentre.getCurrUser(), gameName, boardManager.getGameState());
        FileWriter.writeIntoGlobalInfo(this);
    }

    protected void initGridView(int gridViewId){
        // Add View to activity
        gridView = findViewById(gridViewId);
        gridView.setNumColumns(gameManager.getBoard().getComplexity());
        gridView.setBoardManager(gameManager);
        gameManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        int spacingFactor = (gameManager.getBoard().getComplexity()-1);
                        int displayWidth = gridView.getMeasuredWidth() - gridView.getHorizontalSpacing() * spacingFactor;
                        int displayHeight = gridView.getMeasuredHeight() - gridView.getVerticalSpacing() * spacingFactor;

                        columnWidth = displayWidth / gameManager.getBoard().getComplexity();
                        columnHeight = displayHeight / gameManager.getBoard().getComplexity();

                        display();
                    }
                });
    }

    @Override
    public void update(Observable o, Object arg){
        if (gameManager.gameOver())
            gotoEndState();
        display();
    }

    protected void gotoEndState() {
        timer.cancel();
        int score = gameManager.getScore();
        boolean successfulWin = gameManager.hasWon();

        GameCentre gameCentre = GameCentre.getGameCentre();
        String complexity = String.valueOf(gameManager.getBoard().getComplexity());
        String difficulty = complexity + "x" + complexity;
        ScoreboardEntry scoreEntry = new ScoreboardEntry(getGameTitle(), difficulty, gameCentre.getCurrUser(), score);
        gameCentre.addSavedScoreToData(gameCentre.getCurrUser(), scoreEntry);
        FileWriter.writeIntoGlobalInfo(this);

        Intent intent = new Intent(this, getGameOverClass());
        Bundle info = new Bundle();
        info.putInt("score", score);
        info.putInt("seconds", seconds);
        info.putBoolean("win", successfulWin);
        intent.putExtras(info);
        startActivity(intent);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(getGameTitle(), gameManager);
        timer.cancel();
    }

    protected abstract void display();

    public abstract String getGameTitle();

    protected abstract Class getGameOverClass();
}
