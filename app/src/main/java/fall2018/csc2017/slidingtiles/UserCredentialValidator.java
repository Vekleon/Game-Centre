package fall2018.csc2017.slidingtiles;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * A class that validates a user's credentials for sign up and login based on the
 * properties of a valid username and password.
 */
public class UserCredentialValidator {

    /**
     * The username to be validated
     */
    private String username;

    /**
     * The password to be validated
     */
    private String password;

    public UserCredentialValidator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /***
     * Returns whether username <username> is valid. A username is valid if it is not empty
     * and is composed only of letters and numbers.
     * @return True if the username is is not empty and is composed only of letters and numbers,
     *         false otherwise.
     */
    public boolean isValidUsername() {
        return !isUsernameEmpty() && isUsernameComposedValidCharacters();
    }

    /***
     * Returns whether username is empty.
     * @return True if empty, false otherwise
     */
    public boolean isUsernameEmpty() {
        return username.isEmpty();
    }

    /***
     * Returns whether username is composed of only of letters and numbers.
     * @return True if username composed of only letters and numbers, false otherwise.
     */
    public boolean isUsernameComposedValidCharacters() {
        // Check if username is composed only of letters and numbers
        Pattern validUsernamePattern = Pattern.compile("[a-zA-Z0-9]+");
        Matcher userNameMatcher = validUsernamePattern.matcher(username);

        return userNameMatcher.matches();
    }

    /***
     * Returns whether Password <password> is valid. A password is valid if its length is
     * greater than or equal to 6.
     * @return True if len(password) >= 6, false otherwise.
     */
    public boolean isValidPassword() {
        return password.length() >= 6;
    }


    /***
     * Sets errors for EditText username and password fields in the user interface.
     * @param etUsername The EditText field for username
     * @param etPassword The EditText field for password
     */
    public void setUsernameAndPasswordValidityErrors(EditText etUsername, EditText etPassword) {
        if (!this.isValidUsername() && this.isValidPassword()) {
            //TODO make helper
            if (this.isUsernameEmpty()) {
                etUsername.setError("Error: Username field cannot be empty");
                etUsername.requestFocus();
            } else if (!this.isUsernameComposedValidCharacters()) {
                etUsername.setError("Error: A valid username is composed only of letters and numbers");
                etUsername.requestFocus();
            }
        } else if (this.isValidUsername() && !this.isValidPassword()) {
            etPassword.setError("Error: Password must be of length 6 or greater");
            etPassword.requestFocus();
        } else {
            etUsername.setError("Both fields Invalid. Please try again!");
            etUsername.requestFocus();
        }
    }
}
