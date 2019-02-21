package fall2018.csc2017.slidingtiles.screens;


import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * The base class for screens that handle user authentication
 */
abstract class UserDataScreen extends Screen {

    /**
     * The text field used to enter a user name
     */
    EditText etUsername;

    /**
     * The text field used to enter a password
     */
    EditText etPassword;

    /**
     * The array list to store user information
     */
    ArrayList<String> users;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    /**
     * Used to check if the provided name exists
     * @param new_user the name of the new_user
     * @return whether the user exists in list of users.
     */
    boolean userExists(String new_user){
        if (users.size() == 0) {
            return false;
        } else{
            for(String user: users){
                if((user.split(" : "))[0].equals(new_user)){
                    return true;
                }
            }
            return false;
        }
    }
}
