package fall2018.csc2017.slidingtiles.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.HashMap;

import fall2018.csc2017.slidingtiles.GameCentre;
import fall2018.csc2017.slidingtiles.R;

/**
 * The display for the main screen of the game centre
 *
 * @author Thomas Leung
 * @since 2018-10-29
 */
public class Hub extends Screen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hub_screen);

        GameCentre gameCentre = GameCentre.getGameCentre();
        String currUser = gameCentre.getCurrUser();
        gameCentre.setCurrUserSavedGames(currUser);
        gameCentre.setCurrUserSavedScores(currUser);

        HashMap<Integer, Class> activityMap = new HashMap<>();
        activityMap.put(R.id.Games, GameSelectionScreen.class);
        activityMap.put(R.id.Score_Board, ScoreBoardScreen.class);

        HashMap<Integer, Runnable> runnableMap = new HashMap<>();
        runnableMap.put(R.id.signOutBtn, this::signOut);

        super.createButtonListeners(activityMap, runnableMap);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String username = GameCentre.getGameCentre().getCurrUser();
        TextView usernameTextBox = findViewById(R.id.textViewUsername);
        usernameTextBox.setText(username);

    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate. Do not allow logged in user to back out of Hub without signing out.
     */
    @Override
    public void onBackPressed() {
        //no code is provided in order to disable the back button
    }

    /***
     * Signs out the user from the current Firebase authentication session. Takes user
     * back to the registration hub from where they can sign up or log in.
     */
    private void signOut() {
        GameCentre.getGameCentre().setCurrUser(null);
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, RegistrationHubScreen.class));
    }
}
