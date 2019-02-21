package fall2018.csc2017.slidingtiles.screens;


import android.os.Bundle;

import java.util.HashMap;

import fall2018.csc2017.slidingtiles.GameCentre;
import fall2018.csc2017.slidingtiles.filewriters.FileLogin;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.UserCredentialValidator;
import fall2018.csc2017.slidingtiles.filewriters.FileWriter;

/***
 * A class that displays the sign up screen nd its views.
 * Note: SignUpScreen.java uses Firebase authentication to sign up a user and add them to the
 * database, and so, there is no testable logic in this class that should count towards
 * code coverage line percentage.
 */
public class SignUpScreen extends UserDataScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);

        // Search the view hierarchy and assign reference to all views based on the id required
        etPassword = findViewById(R.id.passwordSignUp);
        etUsername = findViewById(R.id.userNameSignUp);

        users = FileLogin.getUserLogin(this);

        HashMap<Integer, Runnable> runnableMap = new HashMap<>();
        runnableMap.put(R.id.btnSignUp, this::signUpUser);
        super.createButtonListeners(new HashMap<Integer, Class>(), runnableMap);
    }

    /***
     * Signs up the user with their desired username and password using Firebase authentication
     * service, given that both username and password are valid. If the sign up process is
     * successful, the user is taken back to the registration hub from where they can login with
     * their account details or sign up a different account.
     */
    private void signUpUser() {

        // Obtain the text values entered in username and password EditText views
        final String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        UserCredentialValidator validator = new UserCredentialValidator(username, password);

        // Determine whether the username and password are both valid
        if (validator.isValidUsername() && validator.isValidPassword()) {
            if(!userExists(username)){
                users.add(username + " : " + password);
                FileLogin.writeUserLogin(this, users);
                goToScreen(this, RegistrationHubScreen.class);
                makeToastText("Account created!");
                GameCentre.getGameCentre().addUserToData(username);
                FileWriter.writeIntoGlobalInfo(this);
            }else{
                makeToastText("User name already exists!");
            }
        } else {
            // User credentials are not valid
            // Set EditText errors for username and password accordingly
            validator.setUsernameAndPasswordValidityErrors(etUsername, etPassword);
        }
    }

}
