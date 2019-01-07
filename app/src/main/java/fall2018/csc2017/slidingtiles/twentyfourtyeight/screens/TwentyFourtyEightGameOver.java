package fall2018.csc2017.slidingtiles.twentyfourtyeight.screens;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;

import fall2018.csc2017.slidingtiles.SlidingTilesGameScreen;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.StartingActivity;
import fall2018.csc2017.slidingtiles.screens.GameOverScreen;
import fall2018.csc2017.slidingtiles.screens.Hub;
import fall2018.csc2017.slidingtiles.screens.Screen;

/**
 * The Screen that will be displayed for when you finishing a 2048 game
 *
 **/
public class TwentyFourtyEightGameOver extends GameOverScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twenty_fourty_eight_game_over);

        TextView score = findViewById(R.id.scoreText2048);
        score.setText(getScoreMessage());

        TextView timer = findViewById(R.id.Time);
        timer.setText(getTimeMessage());

        HashMap<Integer, Class> activityMap = new HashMap<>();
        activityMap.put(R.id.twenty_fourty_eight_Game_Over_Back, Hub.class);
        super.createButtonListeners(activityMap, new HashMap<>());

        Bundle info = getIntent().getExtras();
        boolean win = info.getBoolean("win");
        TextView winDecision = findViewById(R.id.winDecision);
        if (win)
            winDecision.setText("Congratulations, YOU WIN!");
        else
            winDecision.setText("GAME OVER, you lost!");
    }
}
