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
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return Current User's password
     */
    public String getPassword() {
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
