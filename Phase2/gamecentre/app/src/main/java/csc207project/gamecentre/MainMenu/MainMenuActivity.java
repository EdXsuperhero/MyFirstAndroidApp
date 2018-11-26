package csc207project.gamecentre.MainMenu;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import csc207project.gamecentre.MainMenu.GameLibFragment.GameLibFragment;
import csc207project.gamecentre.R;

/**
 * The Main Menu for Game Centre
 */
public class MainMenuActivity extends AppCompatActivity {

    /**
     * The FragmentTransaction that manages fragments.
     */
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        this.mFragmentTransaction = getFragmentManager().beginTransaction();

        GameLibFragment fragment = new GameLibFragment();
        this.mFragmentTransaction.add(R.id.MainMenuActivity, fragment);
        this.mFragmentTransaction.commit();

        addBottomNavigationViewListener();
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
    private void replaceGameLibFragment() {
        GameLibFragment fragment = new GameLibFragment();
        this.mFragmentTransaction.replace(R.id.MainMenuActivity, fragment);
        this.mFragmentTransaction.commit();
    }
}
