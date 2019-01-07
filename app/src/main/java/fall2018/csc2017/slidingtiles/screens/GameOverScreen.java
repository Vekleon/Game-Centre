package fall2018.csc2017.slidingtiles.screens;

import android.os.Bundle;

/**
 * A class that generalizes common functionality in game over screens
 * @author Jon
 */
public abstract class GameOverScreen extends Screen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Overridden to prevent users from going back on the winning screen
     */
    @Override
    public void onBackPressed() {

    }

    /**
     * @return The score message to put into the score text
     */
    protected String getScoreMessage(){
        Bundle info = getIntent().getExtras();
        int scoreValue = info.getInt("score");
        return "Your Score: " + scoreValue;
    }

    /**
     * @return The time message to put into the time text
     */
    protected String getTimeMessage(){
        Bundle info = getIntent().getExtras();
        int seconds = info.getInt("seconds");
        return seconds / 60  + ":" + (seconds % 60);
    }
}
