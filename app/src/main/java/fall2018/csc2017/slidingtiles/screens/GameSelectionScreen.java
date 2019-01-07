package fall2018.csc2017.slidingtiles.screens;

import android.os.Bundle;

import java.util.HashMap;

import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.StartingActivity;
import fall2018.csc2017.slidingtiles.memorytiles.screens.MemoryTilesTitleScreen;
import fall2018.csc2017.slidingtiles.twentyfourtyeight.screens.TwentyFourtyEightTitleScreen;

/**
 * The screen that shows all the available games in the GameCentre
 *
 * @author Thomas Leung
 * @since 2018-10-29
 */
public class GameSelectionScreen extends Screen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.games_screen);

        HashMap<Integer, Class> activityMap = new HashMap<>();
        activityMap.put(R.id.Sliding_Puzzle, StartingActivity.class);
        activityMap.put(R.id.memory_tiles, MemoryTilesTitleScreen.class);
        activityMap.put(R.id.twenty_fourty_eight, TwentyFourtyEightTitleScreen.class);

        createButtonListeners(activityMap, new HashMap<Integer, Runnable>());
    }
}
