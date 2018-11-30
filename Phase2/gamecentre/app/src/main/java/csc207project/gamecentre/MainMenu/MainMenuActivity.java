package csc207project.gamecentre.MainMenu;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import csc207project.gamecentre.R;
import csc207project.gamecentre.MainMenu.GameLibFragment.GameLibFragment;
import csc207project.gamecentre.MainMenu.LoginFragment.LoginActivity;

/**
 * The Main Menu for Game Centre.
 * # Exclude from code coverage because it's a view class.
 */
public class MainMenuActivity extends AppCompatActivity {

    /**
     * The save file for users.
     */
    public static final String SAVE_USER_FILENAME = "save_user.ser";

    /**
     * A user manager.
     */
    private UserManager userManager;

    /**
     * The FragmentManager that manages fragments.
     */
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        loadFromFile(SAVE_USER_FILENAME);
        if (!this.userManager.isStayLogin()) {
            Intent startLogin = new Intent(this, LoginActivity.class);
            startLogin.putExtra("user_manager", this.userManager);
            startActivityForResult(startLogin, 0);
        }

        this.mFragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = this.mFragmentManager.beginTransaction();
        GameLibFragment fragment = new GameLibFragment();
        fragmentTransaction.add(R.id.MainMenuActivity, fragment);
        fragmentTransaction.commit();

        addBottomNavigationViewListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.userManager = (UserManager) data.getSerializableExtra("user_manager");
        saveToFile(SAVE_USER_FILENAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(SAVE_USER_FILENAME);
    }

    /**
     * Activate Bottom Navigation View.
     */
    private void addBottomNavigationViewListener() {
        BottomNavigationView navigation = findViewById(R.id.MainMenuNavigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigate_game_lib:
                        setNavigationTitle(R.string.navigate_game_lib);
                        replaceGameLibFragment();
                        return true;
                    case R.id.navigate_user:
                        setNavigationTitle(R.string.navigate_user);
                        replaceUserFragment();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    /**
     * Set the title for current view.
     *
     * @param title the title for current view
     */
    private void setNavigationTitle(int title) {
        TextView navigationTitle = findViewById(R.id.NavigationTitle);
        navigationTitle.setText(title);
    }

    /**
     * Replace the current fragment to GameLibFragment.
     */
    public void replaceGameLibFragment() {
        FragmentTransaction fragmentTransaction = this.mFragmentManager.beginTransaction();
        GameLibFragment fragment = new GameLibFragment();
        fragmentTransaction.replace(R.id.MainMenuActivity, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Replace the current fragment to UserFragment.
     */
    public void replaceUserFragment() {
        FragmentTransaction fragmentTransaction = this.mFragmentManager.beginTransaction();
        UserFragment fragment = new UserFragment();
        fragmentTransaction.replace(R.id.MainMenuActivity, fragment);
        fragmentTransaction.commit();
    }

    /**
     * @return the user manger that this activity is holding
     */
    public UserManager getUserManager() {
        return this.userManager;
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
            Log.e("Main Menu Activity", "File not found: " + e.toString());
            this.userManager = new UserManager();
            saveToFile(SAVE_USER_FILENAME);
        } catch (IOException e) {
            Log.e("Main Menu Activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Main Menu Activity", "File contained unexpected data type: " + e.toString());
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
            Log.e("Main Menu Activity", "File write failed: " + e.toString());
        }
    }
}
