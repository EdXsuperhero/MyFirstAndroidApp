package csc207project.gamecentre.MainMenu;

import java.io.Serializable;

/**
 * User's information.
 * A JavaBean.
 */
public class User implements Serializable {

    /**
     * The username of this user.
     */
    private String username;

    /**
     * The password of this user.
     */
    private String password;

    /**
     * A new user instance with username, email address and password.
     *
     * @param username user's username
     * @param password user's password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * @return this user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set a new username for this user.
     *
     * @param username new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return this user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set a new password for this user.
     *
     * @param password new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
