package csc207project.gamecentre.MainMenu;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Manage a users list with username and user instance.
 */
public class UserManager implements Serializable {

    /**
     * A HashMap storing usernames and user instances.
     */
    private HashMap<String, User> users = new HashMap<>();

    /**
     * Current username of user who is using game centre
     */
    private String currentUser;

    private boolean stayLogin = false;

    /**
     * Sign up a new user.
     *
     * @param username the username to be signed up
     * @param password the password to be signed up
     * @return a string indicates current login status
     */
    public String signUp(String username, String password) {

        String returnText;

        if (isStoredUser(username)) {
            returnText = "UsernameError: Username Exists!";
        } else {
            User user = new User(username, password);
            this.users.put(username, user);
            returnText = "Sign Up Successful, " + username + "! Please Sign In.";
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
    public String signIn(String username, String password) {

        String returnText;

        if (isStoredUser(username)) {
            User user = this.users.get(username);
            if (user.getPassword().equals(password)) {
                returnText = "Welcome, " + username + "!";
                currentUser = username;
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

    /**
     * @return current user that is using game centre
     */
    public String getCurrentUser() {
        return this.currentUser;
    }

}
