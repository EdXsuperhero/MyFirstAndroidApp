package group_0550.gamecentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import group_0550.gamecentre.UserManagerSystem.UserManager;

/**
 * The initial activity for user login functionality.
 */
public class LoginSystemActivity extends AppCompatActivity {

    /**
     * Save file for users list.
     */
    public static final String SAVE_USER = "save_user.ser";

    /**
     * Username of whom is playing this game.
     */
    private static String currentUserName = null;

    /**
     * A user manager.
     */
    private UserManager userManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadFromFile(SAVE_USER);
        if (this.userManager == null) {
            this.userManager = new UserManager();
            saveToFile(SAVE_USER);
        }

        setContentView(R.layout.activity_loginsystem);
        addSignInButtonListener();
        addSignUpButtonListener();
    }

    /**
     * Activate the sign in button.
     */
    private void addSignInButtonListener() {
        Button signInButton = findViewById(R.id.SignInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadFromFile(SAVE_USER);

                String username = addUsernameInputTextListener();
                String password = addPasswordInputTextListener();

                String text = userManager.signIn(username, password);
                makeToastLoadedText(text);
                if (text.startsWith("Welcome")) {
                    currentUserName = username;
                    switchToStart();
                }
            }
        });
    }

    /**
     * Activate the sign up button.
     */
    private void addSignUpButtonListener() {
        Button signUpButton = findViewById(R.id.SignUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadFromFile(SAVE_USER);

                String username = addUsernameInputTextListener();
                String password = addPasswordInputTextListener();

                String text = userManager.signUp(username, password);
                makeToastLoadedText(text);
                if (text.startsWith("Welcome")) {
                    saveToFile(SAVE_USER);
                    currentUserName = username;
                    switchToStart();
                }
            }
        });
    }

    /**
     * Activate the username input box and
     * get typed username.
     */
    @NonNull
    private String addUsernameInputTextListener() {
        EditText usernameInput = findViewById(R.id.UsernameInput);
        return usernameInput.getText().toString();
    }

    /**
     * Activate the password input box and
     * get typed password.
     */
    @NonNull
    private String addPasswordInputTextListener() {
        EditText passwordInput = findViewById(R.id.PasswordInput);
        return passwordInput.getText().toString();
    }

    /**
     * Pop up a Toast to inform user current login status.
     * @param text Login status return by sign in / sign up
     */
    private void makeToastLoadedText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * @return Username of whom is currently playing this game.
     */
    public static String getCurrentUserName() {
        return currentUserName;
    }

    /**
     * Load the users list from fileName.
     * @param fileName Path to load users list
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                this.userManager = (UserManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("Login System", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Login System", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Login System", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the users list to fileName.
     * @param fileName Path to save users list
     */
    private void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(this.userManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Switch to StartActivity view to choose game.
     */
    private void switchToStart() {
        Intent tmp = new Intent(this, StartingActivity.class);
        startActivity(tmp);
    }
}
