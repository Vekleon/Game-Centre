package fall2018.csc2017.slidingtiles.screens;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import fall2018.csc2017.slidingtiles.filewriters.FileWriter;
import fall2018.csc2017.slidingtiles.GameCentre;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.User;
import fall2018.csc2017.slidingtiles.UserCredentialValidator;

/***
 * A class that displays the sign up screen nd its views.
 * Note: SignUpScreen.java uses Firebase authentication to sign up a user and add them to the
 * database, and so, there is no testable logic in this class that should count towards
 * code coverage line percentage.
 */
public class SignUpScreen extends Screen {

    /**
     * The EditText view having id passwordSignUp
     */
    private EditText etPassword;

    /**
     * The EditText view having id usernameSignUp
     */
    private EditText etUsername;

    /**
     * Firebase authentication
     */
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);

        // Search the view hierarchy and assign reference to all views based on the id required
        etPassword = findViewById(R.id.passwordSignUp);
        etUsername = findViewById(R.id.userNameSignUp);

        // Initialize Firebase authentication service
        fbAuth = FirebaseAuth.getInstance();

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

            // Entries for username and password are valid. Sign up the user.
            fbAuth.createUserWithEmailAndPassword(username + "@gamecentre.com", password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                User userToSignUp = new User(username);
                                addUserToDatabase(userToSignUp);

                                Toast.makeText(SignUpScreen.this, "SUCCESS!!!",
                                        Toast.LENGTH_SHORT).show();

                                // Take user back to the registration hub from where they can login
                                // with the account they have created.
                                GameCentre.getGameCentre().addUserToData(username);
                                FileWriter.writeIntoGlobalInfo(SignUpScreen.this);

                                SignUpScreen.super.goToScreen(SignUpScreen.this,
                                        RegistrationHubScreen.class);

                            } else {
                                handleSignUpFailure(task.getException());
                            }
                        }
                    });
        } else {
            // User credentials are not valid
            // Set EditText errors for username and password accordingly
            validator.setUsernameAndPasswordValidityErrors(etUsername, etPassword);
        }
    }


    /***
     * Adds user <u> to the Firebase Database as a child of the reference GameCentre-Users.
     * @param u The user to be added to the database
     */
    private void addUserToDatabase(User u) {
        FirebaseDatabase.getInstance().getReference("GameCentre-Users")
                .child(fbAuth.getCurrentUser().getUid())
                .setValue(u);
    }

    /***
     * Notifies user with an error specific message regarding an error in the registration process.
     * @param e Exception of error to handle
     */
    private void handleSignUpFailure(Exception e) {

        if (e instanceof FirebaseAuthUserCollisionException) {
            // An account for a user with the given username already exists
            etUsername.setError("User with this username already exists!");
            etPassword.requestFocus();
        } else {
            Toast.makeText(SignUpScreen.this,
                    "An error occurred. Your account could not be created. ",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
