package fall2018.csc2017.slidingtiles.screens;

import android.os.Bundle;

import java.util.HashMap;

import fall2018.csc2017.slidingtiles.R;

/***
 * A class that displays the registration hub, that is, the screen where users can select
 * to sign up or login
 */
public class RegistrationHubScreen extends Screen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_hub_screen);

        HashMap<Integer, Class> activityMap = new HashMap<>();
        activityMap.put(R.id.btnLoginRegHub, LoginScreen.class);
        activityMap.put(R.id.btnSignUpRegHub, SignUpScreen.class);
        super.createButtonListeners(activityMap, new HashMap<Integer, Runnable>());
    }

    @Override
    protected int getSongId(){
        return R.raw.sax;
    }
}
