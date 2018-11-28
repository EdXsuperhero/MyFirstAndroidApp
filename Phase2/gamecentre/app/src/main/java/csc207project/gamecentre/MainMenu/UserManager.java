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

    /**
     * If the user want to stay logged in.
     */
    private boolean stayLogin = false;

    /**
     * Sign In a user.
     *
     * @param username inputted username
     * @param password inputted password
     * @return if the inputted password matched the stored password
     */
    public boolean signIn(String username, String password) {
        User user = this.users.get(username);
        return user.getPassword().equals(password);
    }

    /**
     * Sign Up a user.
     *
     * @param username inputted username
     * @param password inputted password
     */
    public void signUp(String username, String password) {
        User user = new User(username, password);
        this.users.put(username, user);
    }

    /**
     * Return whether the username is in the users.
     *
     * @param username the username to check
     * @return whether the username is in the users
     */
    public boolean isStoredUser(String username) {
        return this.users.containsKey(username);
    }

    /**
     * To set current user.
     *
     * @param currentUser the user that is using game centre
     */
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * @return current user that is using game centre
     */
    public String getCurrentUser() {
        return this.currentUser;
    }

    /**
     * @return if the user want to stay logged in
     */
    public boolean isStayLogin() {
        return this.stayLogin;
    }

    /**
     * Change the stay logged in status.
     *
     * @param stayLogin a boolean indicates whether user want to stay logged in
     */
    public void setStayLogin(boolean stayLogin) {
        this.stayLogin = stayLogin;
    }
}
