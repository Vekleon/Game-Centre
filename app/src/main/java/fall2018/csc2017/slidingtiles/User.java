package fall2018.csc2017.slidingtiles;


/**
 * A representation of the user which holds all of the information relevant to the user.
 * Necessitated by Firebase.
 */
public class User {

    private String username;

    public User() {

    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
