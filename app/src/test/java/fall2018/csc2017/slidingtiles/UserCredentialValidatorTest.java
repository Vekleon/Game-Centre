package fall2018.csc2017.slidingtiles;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A test class for UserCredentialValidator
 **/
public class UserCredentialValidatorTest {

    /**
     * Sets up the user's credentials with the given username <testUsername> and password
     * <testPassword>
     * @param testUsername User's username to validate.
     * @param testPassword User's password to validate.
     * @return A UserCredentialValidator object to validate user credentials on the given username
     * and password.
     */
    private UserCredentialValidator setUpCredentials(String testUsername, String testPassword) {
        return new UserCredentialValidator(testUsername, testPassword);
    }

    /**
     * Test whether the username is deemed invalid if the username is empty.
     */
    @Test
    public void usernameEmpty() {
        UserCredentialValidator validator = setUpCredentials("", "password");
        assertEquals(true, validator.isUsernameEmpty());
    }

    @Test
    public void usernameNotEmpty() {
        UserCredentialValidator validator =
                setUpCredentials("testUsername", "password");

        assertEquals(false, validator.isUsernameEmpty());

    }

    /**
     * Test whether the username is deemed valid if the username contains
     * all valid characters.
     */
    @Test
    public void usernameComposedValidCharacters() {
        UserCredentialValidator validator =
                setUpCredentials("testUsername", "password");

        assertEquals(true, validator.isUsernameComposedValidCharacters());
    }


    /**
     * Test whether the username is deemed invalid if the username contains
     * invalid characters.
     */
    @Test
    public void usernameNotComposedValidCharacters() {
        UserCredentialValidator validator =
                setUpCredentials("!test_username", "password");

        assertEquals(false, validator.isUsernameComposedValidCharacters());
    }

    /**
     * Test whether the username is deemed valid if the username is not empty and is
     * composed only of valid characters
     */
    @Test
    public void validUsername() {
        UserCredentialValidator validator =
                setUpCredentials("testUsername", "password");

        assertEquals(false, validator.isUsernameEmpty());
        assertEquals(true, validator.isUsernameComposedValidCharacters());
        assertEquals(true, validator.isValidUsername());
    }


    /**
     * Test whether the password is deemed invalid if len(password) < 6.
     */
    @Test
    public void invalidPassword() {
        UserCredentialValidator validator =
                setUpCredentials("testUsername", "123");

        assertEquals(false, validator.isValidPassword());

    }

    /**
     * Test whether the password is deemed valid if len(password) >= 6.
     */
    @Test
    public void validPassword() {
        UserCredentialValidator validator =
                setUpCredentials("testUsername", "1234567");

        assertEquals(true, validator.isValidPassword());
    }

}