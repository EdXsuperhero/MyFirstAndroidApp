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
     * @return a string indicates signin status
     */
    public String signIn(String username, String password) {
        String result = "Successful";

        if (!isStoredUser(username)) {
            result = "Username Error";
        } else {
            User user = this.users.get(username);
            if (!password.equals(user.getPassword())) {
                result = "Password Error";
            } else {
                this.currentUser = username;
            }
        }

        return result;
    }

    /**
     * Sign Up a user.
     *
     * @param username inputted username
     * @param password inputted password
     * @param confirmPassword inputted confirm password
     * @return a string indicates signup status
     */
    public String signUp(String username, String password, String confirmPassword) {
        String result = "Successful";

        if (isStoredUser(username) | username.equals("")) {
            result = "Username Error";
        } else {
            if (!password.equals(confirmPassword)) {
                result = "Password Error";
            } else {
                User user = new User(username, password);
                this.users.put(username, user);
                this.currentUser = username;
            }
        }

        return result;
    }

    /**
     * Change current user's username.
     *
     * @param newUsername new username to be changed
     */
    void changeUsername(String newUsername) {
        User user = this.users.get(this.currentUser);
        this.users.remove(this.currentUser);

        this.currentUser = newUsername;
        user.setUsername(this.currentUser);
        this.users.put(this.currentUser, user);
    }

    /**
     * Change current user's password.
     *
     * @param newPassword new password to be changed
     */
    void changePassword(String newPassword) {
        User user = this.users.get(this.currentUser);
        user.setPassword(newPassword);
        this.users.replace(this.currentUser, user);
    }

    /**
     * Return whether the username is in the users.
     *
     * @param username the username to check
     * @return whether the username is in the users
     */
    boolean isStoredUser(String username) {
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
