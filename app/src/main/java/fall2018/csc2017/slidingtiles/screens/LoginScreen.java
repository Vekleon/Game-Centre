package fall2018.csc2017.slidingtiles.screens;

import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;

import fall2018.csc2017.slidingtiles.filewriters.FileLogin;
import fall2018.csc2017.slidingtiles.filewriters.FileWriter;
import fall2018.csc2017.slidingtiles.GameCentre;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.UserCredentialValidator;


/**
 * A class that displays the login screen and its views
 * Note: LoginScreen.java uses Firebase authentication to login a user and so there is no testable
 * logic in this class that should count towards code coverage line percentage.
 *
 * @author Muneeb
 * @since 2018-10-28
 */
public class LoginScreen extends UserDataScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        etPassword = findViewById(R.id.Password);
        etUsername = findViewById(R.id.User_Name);
        etUsername.requestFocus();
        users = FileLogin.getUserLogin(this);

        HashMap<Integer, Runnable> runnableMap = new HashMap<>();
        runnableMap.put(R.id.Enter, this::loginUser);
        super.createButtonListeners(new HashMap<Integer, Class>(), runnableMap);
    }

    /**
     * Takes user to the hub screen, from where they can access features only available to
     * a logged in user.
     */
    private void gotoHubScreen() {
        Intent hubIntent = new Intent(LoginScreen.this, Hub.class);
        hubIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(hubIntent);
    }


    /***
     * Logs in the user with Username <username> and Password <password> using Firebase
     * authentication service, given that both username and password are valid. If the
     * login process is successful the user is taken to the Hub from where they can access
     * games and scoreboards.
     */
    private void loginUser() {

        // Obtain the text values entered in username and password EditText views
        final String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        UserCredentialValidator validator = new UserCredentialValidator(username, password);
        // Determine whether the username and password are both valid
        if (validator.isValidUsername() && validator.isValidPassword()) {
            if (userExists(username) && correctPassword(username, password)) {
                setUserAndGlobalData(username);
                gotoHubScreen();
            } else{
                makeToastText("Username or password is incorrect!");
            }
        } else {
            // Login credentials are not valid.
            // Set EditText errors for username and password accordingly
            validator.setUsernameAndPasswordValidityErrors(etUsername, etPassword);
        }
    }

    /**
     * checks if the provided information exists in the user details
     * @param name the user_name of the user
     * @param pass the provided password of the user
     * @return whether the details match that in the user details.
     */
    private boolean correctPassword(String name, String pass){
        String[] credentials;
        for(String user: users){
            credentials = user.split(" : ");
            if(credentials[0].equals(name) && credentials[1].equals(pass)){
                return true;
            }
        }
        return false;
    }

    private void setUserAndGlobalData(String user){
        GameCentre.getGameCentre().setCurrUser(user);
        GameCentre.setAllUserData(FileWriter.getGlobalUserInfo(this));
    }

}
