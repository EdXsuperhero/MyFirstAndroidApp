package csc207project.gamecentre.MainMenu.LoginFragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.io.OutputStream;


import csc207project.gamecentre.GoFor24.game24Activity;
import csc207project.gamecentre.R;
import csc207project.gamecentre.SlidingTiles.StartingActivity;
import csc207project.gamecentre.MemoryMatchingPics.MatchingMainActivity;
import csc207project.gamecentre.UserManager.UserManager;


/**
 * The login activity for Game Centre.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * The save file for users.
     */
    public static final String SAVE_USER_FILENAME = "save_user.ser";

    /**
     * A user manager.
     */
    private UserManager userManager;

    /**
     * Current user that is using game centre
     */
    private static String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(SAVE_USER_FILENAME);

        setContentView(R.layout.activity_login);
        addSignUpButtonListener();
        addSignInButtonListener();
    }
//    // test method run to 24 point without MainMenus
//    private void add24PointsButtonListener(){
//        Button btntoGame24 = findViewById(R.id.btntoGame24);
//        btntoGame24.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent tmp;
//                tmp = new Intent(getApplicationContext(), game24Activity.class);
//                startActivity(tmp);
//
//            }
//        });
//    }


    /**
     * Activate the sign up button.
     */
    private void addSignUpButtonListener() {
        Button buttonSignUp = findViewById(R.id.SignUpButton);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadFromFile(SAVE_USER_FILENAME);

                String username = getUsernameTextListener();
                String password = getPasswordTextListener();
                String returnText = userManager.signUp(username, password);
                makeToastLoadedText(returnText);
                if (returnText.startsWith("Sign")) {
                    saveToFile(SAVE_USER_FILENAME);
                    currentUser = username;
                }
            }
        });
    }

    /**
     * Activate the sign in button.
     */
    private void addSignInButtonListener() {
        Button buttonSignIn = findViewById(R.id.SignInButton);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadFromFile(SAVE_USER_FILENAME);

                String username = getUsernameTextListener();
                String password = getPasswordTextListener();
                String returnText = userManager.signIn(username, password);
                makeToastLoadedText(returnText);
                if (returnText.startsWith("Welcome")) {
                    currentUser = username;

                    switchToStart();
                }
            }
        });
    }

    /**
     * Activate the username input and get the username.
     *
     * @return typed in username
     */
    private String getUsernameTextListener() {
        EditText usernameInput = findViewById(R.id.UsernameInput);
        return usernameInput.getText().toString();
    }

    /**
     * Activate the password input and get the password.
     *
     * @return typed in password
     */
    private String getPasswordTextListener() {
        EditText passwordInput = findViewById(R.id.PasswordInput);
        return passwordInput.getText().toString();
    }

    /**
     * To pop up a toast to inform user current login status.
     *
     * @param text login status returned by sign in / up
     */
    private void makeToastLoadedText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Switch to StartActivity.
     */
    private void switchToStart() {
        Intent startIntent = new Intent(this, game24Activity.class);
    }

    /**
     * Load the user manager from fileName.
     *
     * @param fileName the name of the file
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
            Log.e("Login Activity", "File not found: " + e.toString());
            this.userManager = new UserManager();
            saveToFile(SAVE_USER_FILENAME);
        } catch (IOException e) {
            Log.e("Login Activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Login Activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the user manager to fileName.
     *
     * @param fileName the name of the file
     */
    private void saveToFile(String fileName) {

        try {
            OutputStream outputStream = this.openFileOutput(fileName, MODE_PRIVATE);
            ObjectOutputStream output = new ObjectOutputStream(outputStream);
            output.writeObject(this.userManager);
            output.close();
        } catch (IOException e) {
            Log.e("Login Activity", "File write failed: " + e.toString());
        }
    }

    /**
     * Return the user that is currently using game centre.
     *
     * @return the user that is currently using game centre
     */
    public static String getCurrentUser() {
        return currentUser;
    }


}
