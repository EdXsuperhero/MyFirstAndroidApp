package group_0550.gamecentre.UserManagerSystem;

import java.io.Serializable;
import java.util.HashMap;

/**
 * To manage all the users and handle sign in / sign up issues.
 */
public class UserManager implements Serializable {

    /**
     * A HashMap to store all the Users' information
     * based on their login name.
     */
    private HashMap<String, User> userMap;

    /**
     * A pointer to current user who is playing this game.
     */
    public User currentUser;

    /**
     * A new user manager that has empty user HashMap and not logged in.
     */
    public UserManager() {
        userMap = new HashMap<>();
        currentUser = null;
    }

    /**
     * Sign in functionality, may change currentUser if sign in successful.
     * @param username User's typed login name
     * @param password User's typed login password
     * @return A string indicates current login status
     */
    public String signIn(String username, String password) {
        String returnString;
        if (!this.registeredUser(username)) {
            returnString = "Invalid Username";
        }
        else {
            User loginUser = userMap.get(username);
            if (!loginUser.getPassword().equals(password)) {
                returnString = "Wrong Password";
            }
            else {
                returnString = "Welcome, " + username + "!";
                currentUser = loginUser;
            }
        }
        return returnString;
    }

    /**
     * Sign up functionality, will auto sign in after successfully sign up.
     * @param username User's typed login name
     * @param password User's typed login password
     * @return A string indicates current login status
     */
    public String signUp(String username, String password) {
        String returnString;
        if (this.registeredUser(username)) {
            returnString = "Username Exists";
        }
        else {
            User newUser = new User(username, password);
            userMap.put(username, newUser);
            returnString = this.signIn(username, password) + " Sign Up successful.";
        }
        return returnString;
    }

    /**
     * Return whether this username is registered before.
     * @param username User's typed login name
     * @return If the username is registered
     */
    public boolean registeredUser(String username) {
        return userMap.containsKey(username);
    }

    /**
     * Return the current user that is playing this game.
     * @return current user
     */
    public User getCurrentUser() {
        return currentUser;
    }
}
