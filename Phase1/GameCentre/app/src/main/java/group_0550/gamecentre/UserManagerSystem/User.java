package group_0550.gamecentre.UserManagerSystem;

import java.io.Serializable;

/**
 * To store each user's information.
 */
public class User implements Serializable {

    /**
     * User's login name.
     */
    private String username;

    /**
     * User's login password.
     */
    private String password;

    /**
     * Setting up a new User.
     * @param username User's login name
     * @param password User's login password
     */
    User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    void setUsername(String username) {
        this.username = username;
    }

    String getUsername() {
        return username;
    }

    void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return Current User's password
     */
    String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
