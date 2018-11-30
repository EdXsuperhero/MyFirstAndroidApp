package csc207project.gamecentre.MainMenu.LoginFragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import csc207project.gamecentre.R;
import csc207project.gamecentre.MainMenu.UserManager;

/**
 * The login activity for Game Centre.
 * # Exclude from code coverage because it's a view class.
 */
public class LoginActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_login);

        this.userManager = (UserManager) getIntent().getSerializableExtra("user_manager");

        this.mFragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = this.mFragmentManager.beginTransaction();
        SignInFragment fragment = new SignInFragment();
        fragmentTransaction.add(R.id.LoginActivity, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void finish() {
        Intent backToMain = new Intent();
        backToMain.putExtra("user_manager", this.userManager);
        setResult(0, backToMain);
        super.finish();
    }

    /**
     * Replace the current fragment with signinfragment.
     */
    public void replaceSignInFragment() {
        FragmentTransaction fragmentTransaction = this.mFragmentManager.beginTransaction();
        SignInFragment fragment = new SignInFragment();
        fragmentTransaction.replace(R.id.LoginActivity, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Replace the current fragment with signupfragment.
     */
    public void replaceSignUpFragment() {
        FragmentTransaction fragmentTransaction = this.mFragmentManager.beginTransaction();
        SignUpFragment fragment = new SignUpFragment();
        fragmentTransaction.replace(R.id.LoginActivity, fragment);
        fragmentTransaction.commit();
    }

    /**
     * @return get current user manager
     */
    public UserManager getUserManager() {
        return userManager;
    }
}
