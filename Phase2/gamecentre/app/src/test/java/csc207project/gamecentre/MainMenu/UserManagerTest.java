package csc207project.gamecentre.MainMenu;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserManagerTest {

    private UserManager userManager;

    @Before
    public void construct() {
        this.userManager = new UserManager();
        this.userManager.signUp("testUsername1", "testPassword1", "testPassword1");
    }

    @Test
    public void testUsernameErrorSignIn() {
        String expected = "Username Error";
        String actual = this.userManager.signIn("test", "test");
        assertSame("sign in username error", expected, actual);

    }

    @Test
    public void testPasswordErrorSignIn() {
        String expected = "Password Error";
        String actual = this.userManager.signIn("testUsername1", "test");
        assertSame("sign in password error", expected, actual);
    }

    @Test
    public void testSignIn() {
        String expected = "Successful";
        String actual = this.userManager.signIn("testUsername1", "testPassword1");
        assertSame("sign in fails", expected, actual);
    }

    @Test
    public void testUsernameErrorSignUp() {
        String expected = "Username Error";
        String actual = this.userManager.signUp("testUsername1", "test", "test");
        assertSame("sign up username error", expected, actual);
    }

    @Test
    public void testUsernameEmptySignUp() {
        String expected = "Username Error";
        String actual = this.userManager.signUp("", "test", "test");
        assertSame("username empty", expected, actual);
    }

    @Test
    public void testPasswordErrorSignUp() {
        String expected = "Password Error";
        String actual = this.userManager.signUp("testUsername2", "test", "test1");
        assertSame("sign up password error", expected, actual);
    }

    @Test
    public void testSignUp() {
        String expected = "Successful";
        String actual = this.userManager.signUp("testUsername2", "testPassword2", "testPassword2");
        assertSame("sign up fails", expected, actual);
    }

    @Test
    public void testChangeUsername() {
        String expected = "testNewUsername";
        this.userManager.changeUsername(expected);
        String actual = this.userManager.getCurrentUser();
        assertSame("change username fails", expected, actual);
    }

    @Test
    public void changePassword() {
        this.userManager.changePassword("testNewPassword");
        String expected = "Successful";
        String actual = this.userManager.signIn(this.userManager.getCurrentUser(), "testNewPassword");
        assertSame("change password fail", expected, actual);
    }

    @Test
    public void isStoredUser() {
        assertTrue("is stored user fails", this.userManager.isStoredUser("testUsername1"));
    }

    @Test
    public void setCurrentUser() {
        String expected = "testNewUsername";
        this.userManager.setCurrentUser(expected);
        String actual = this.userManager.getCurrentUser();
        assertSame("set current user fails", expected, actual);
    }

    @Test
    public void getCurrentUser() {
        String expected = "testUsername1";
        String actual = this.userManager.getCurrentUser();
        assertSame("get current user fails", expected, actual);
    }

    @Test
    public void isStayLogin() {
        boolean expected = true;
        this.userManager.setStayLogin(expected);
        assertTrue("is stay login fails", this.userManager.isStayLogin());
    }

    @Test
    public void setStayLogin() {
        boolean expected = true;
        this.userManager.setStayLogin(expected);
        assertTrue("set stay login fails", this.userManager.isStayLogin());
    }
}