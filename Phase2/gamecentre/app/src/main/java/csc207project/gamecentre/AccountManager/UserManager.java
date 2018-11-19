package csc207project.gamecentre.AccountManager;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Manage a users list with username and password.
 */
class UserManager implements Serializable {

    /**
     * A HashMap storing usernames and passwords.
     */
    private HashMap<String, String> users = new HashMap<>();

    /**
     * Sign up a new user.
     * Notice: After signed up, you will be automatically signed in.
     *
     * @param username the username to be signed up
     * @param password the password to be signed up
     * @return a string indicates current login status
     */
    String signUp(String username, String password) {

        String returnText;

        if (isStoredUser(username)) {
            returnText = "Username Exists!";
        } else {
            this.users.put(username, password);
            returnText = signIn(username, password) + " Sign Up Successful!";
        }

        return returnText;
    }

    /**
     * Sign in a user.
     *
     * @param username the username to be signed in
     * @param password the password to be signed in
     * @return a string indicates current login status
     */
    String signIn(String username, String password) {

        String returnText;

        if (isStoredUser(username)) {
            if (this.users.get(username).equals(password)) {
                returnText = "Welcome, " + username + "!";
            } else {
                returnText = "Wrong Password!";
            }
        } else {
            returnText = "This username is not Registered!";
        }

        return returnText;
    }

    /**
     * Return whether the username is in the users.
     *
     * @param username the username to check
     * @return whether the username is in the users
     */
    private boolean isStoredUser(String username) {
        return this.users.containsKey(username);
    }
}
