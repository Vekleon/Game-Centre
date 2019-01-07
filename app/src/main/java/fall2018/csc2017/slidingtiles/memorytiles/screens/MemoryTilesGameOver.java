package fall2018.csc2017.slidingtiles.memorytiles.screens;

import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.screens.GameOverScreen;
import fall2018.csc2017.slidingtiles.screens.Hub;
/**
 * The class responsible for calling all events to pass when a game of memorytiles ends
 *
 * @author Group_0645
 * @since 2018-11-23
 */
public class MemoryTilesGameOver extends GameOverScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory_tiles_game_over);

        TextView score = findViewById(R.id.scoreTextMemoryTiles);
        score.setText(getScoreMessage());

        TextView timer = findViewById(R.id.timeTextMemoryTiles);
        timer.setText(getTimeMessage());

        HashMap<Integer, Class> activityMap = new HashMap<>();
        activityMap.put(R.id.memory_tiles_game_over_back, Hub.class);
        super.createButtonListeners(activityMap, new HashMap<>());
    }
}
