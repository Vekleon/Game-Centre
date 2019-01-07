package fall2018.csc2017.slidingtiles.screens;

import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

import fall2018.csc2017.slidingtiles.GameScreen;
import fall2018.csc2017.slidingtiles.SlidingTilesGameScreen;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.StartingActivity;

/**
 * The Screen that will be displayed for when you win a Sliding Tiles game
 *
 * @author Thomas Leung
 * @since 2018-11-03
 */
public class SlidingTilesGameOver extends GameOverScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_tiles_game_over);

        TextView score = findViewById(R.id.scoreTextSlidingTiles);
        score.setText(getScoreMessage());

        TextView timer = findViewById(R.id.Time);
        timer.setText(getTimeMessage());

        HashMap<Integer, Class> activityMap = new HashMap<>();
        activityMap.put(R.id.Sliding_Tiles_Game_Over_Back, StartingActivity.class);
        super.createButtonListeners(activityMap, new HashMap<>());
    }
}
