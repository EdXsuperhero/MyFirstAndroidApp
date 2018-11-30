package csc207project.gamecentre.MainMenu;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private User user;

    @Before
    public void construct() {
        user = new User("testUsername", "testPassword");
    }

    @Test
    public void testGetUsername() {
        String expected = "testUsername";
        String actual = this.user.getUsername();
        assertSame("getUsername fails", expected, actual);
    }

    @Test
    public void testSetUsername() {
        String expected = "testNewUsername";
        this.user.setUsername(expected);
        String actual = this.user.getUsername();
        assertSame("setUsername fails", expected, actual);
    }

    @Test
    public void testGetPassword() {
        String expected = "testPassword";
        String actual = this.user.getPassword();
        assertSame("getPassword fails", expected, actual);
    }

    @Test
    public void testSetPassword() {
        String expected = "testNewPassword";
        this.user.setPassword(expected);
        String actual = this.user.getPassword();
        assertSame("setPassword fails", expected, actual);
    }
}