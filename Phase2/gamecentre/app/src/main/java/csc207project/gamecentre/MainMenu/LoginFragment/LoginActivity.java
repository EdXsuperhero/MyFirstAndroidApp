package csc207project.gamecentre.MainMenu.LoginFragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import csc207project.gamecentre.R;
import csc207project.gamecentre.MainMenu.UserManager;

/**
 * The login activity for Game Centre.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * A user manager.
     */
    private UserManager userManager;

    /**
     * The FragmentTransaction that manages fragments.
     */
    private FragmentTransaction mFragmentTranscation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.userManager = (UserManager) getIntent().getSerializableExtra("user_manager");

        this.mFragmentTranscation = getFragmentManager().beginTransaction();

        SignInFragment fragment = new SignInFragment();
        this.mFragmentTranscation.add(R.id.LoginActivity, fragment);
        this.mFragmentTranscation.commit();
    }

    /**
     * Replace the current fragment with signinfragment.
     */
    public void replaceSignInFragment() {
        SignInFragment fragment = new SignInFragment();
        this.mFragmentTranscation.replace(R.id.LoginActivity, fragment);
        this.mFragmentTranscation.commit();
    }

    /**
     * Replace the current fragment with signupfragment.
     */
    public void replaceSignUpFragment() {
        SignUpFragment fragment = new SignUpFragment();
        this.mFragmentTranscation.replace(R.id.LoginActivity, fragment);
        this.mFragmentTranscation.commit();
    }

    public String getCurrentUsername() {
        return this.userManager.getCurrentUser();
    }
}
