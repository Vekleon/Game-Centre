package fall2018.csc2017.slidingtiles.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;


import java.util.HashMap;

import fall2018.csc2017.slidingtiles.DataPacket;
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
public class LoginScreen extends Screen {

    private FirebaseAuth fbAuth;

    private EditText etUsername;
    private EditText etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        etPassword = findViewById(R.id.Password);
        etUsername = findViewById(R.id.User_Name);
        etUsername.requestFocus();

        fbAuth = FirebaseAuth.getInstance();

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
            // Entries for username and password are valid. Sign up the user.
            fbAuth.signInWithEmailAndPassword(username + "@gamecentre.com", password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser currUser = fbAuth.getCurrentUser();
                                if (currUser != null) {
                                    buildUserProfile(currUser);
                                    gotoHubScreen();
                                }

                            } else {
                                handleLoginFailure(task.getException());
                            }
                        }
                    });

        } else {
            // Login credentials are not valid.
            // Set EditText errors for username and password accordingly
            validator.setUsernameAndPasswordValidityErrors(etUsername, etPassword);
        }
    }

    /***
     * Initializes the current user logged in and builds their profile
     * @param user The user for whom the profile is built
     */
    private void buildUserProfile(FirebaseUser user) {
        if (user.getEmail() != null) {
            String loggedInUsername = user.getEmail();
            GameCentre.getGameCentre()
                    .setCurrUser(loggedInUsername.substring(0,
                            loggedInUsername.indexOf("@")));
        }

        setUserData();
    }


    private void setUserData() {
        HashMap<String, DataPacket> userData = FileWriter.getGlobalUserInfo(this);
        GameCentre.setAllUserData(userData);
    }

    /***
     * Notifies user with an error specific message regarding an error in the login process.
     * @param e Exception of error to handle
     */
    private void handleLoginFailure(Exception e) {

        if (e instanceof FirebaseAuthInvalidCredentialsException) {
            etUsername.setError("Account exists. Password incorrect.");
            etUsername.requestFocus();
        } else if (e instanceof FirebaseAuthInvalidUserException) {

            // Handling FirebaseAuth exceptions referenced from
            // https://www.techotopia.com/index.php/Handling_Firebase_Authentication_Errors_and_Failures
            String errorCode = ((FirebaseAuthInvalidUserException) e).getErrorCode();

            if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                etUsername.setError("An account with this username does not exist!");
                etUsername.requestFocus();
            }

        } else
            makeToastText("An error occured. Login unsuccessful. ");
    }


}
