package csc207project.gamecentre.MainMenu.LoginFragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import csc207project.gamecentre.R;
import csc207project.gamecentre.MainMenu.UserManager;

/**
 * The fragment that handles sign up process.
 * # Exclude from code coverage because it's a view class.
 */
public class SignUpFragment extends Fragment {

    /**
     * Current user manager we are working on.
     */
    private UserManager userManager;
    /**
     * Current View.
     */
    private View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LoginActivity activity = (LoginActivity) getActivity();
        this.userManager = activity.getUserManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        this.view = view;

        setUsernameInputIndicator(R.string.empty);
        setPasswordConfirmIndicator(R.string.empty);
        addSignInButtonListener();
        addSignUpButtonListener();

        return view;
    }

    /**
     * Activate Sign In button.
     */
    private void addSignInButtonListener() {
        Button signInButton = this.view.findViewById(R.id.SwitchToSignInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getActivity()).replaceSignInFragment();
            }
        });
    }

    /**
     * Activate Sign Up button.
     */
    private void addSignUpButtonListener() {
        Button signUpButton = this.view.findViewById(R.id.SignUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getUsernameInput();
                String password = getPasswordInput();
                String confirmPassword = getConfirmPasswordInput();
                String result = userManager.signUp(username, password, confirmPassword);

                switch (result) {
                    case "Username Error":
                        setUsernameInputIndicator(R.string.username_exists);
                        break;
                    case "Password Error":
                        setPasswordConfirmIndicator(R.string.password_doesnt_match);
                        break;
                    default:
                        ((LoginActivity) getActivity()).replaceSignInFragment();
                }
            }
        });
    }

    /**
     * Get username input.
     *
     * @return a username input
     */
    private String getUsernameInput() {
        EditText usernameInput = this.view.findViewById(R.id.UsernameSignUpInput);
        return usernameInput.getText().toString();
    }

    /**
     * Get password input.
     *
     * @return a password input
     */
    private String getPasswordInput() {
        EditText passwordInput = this.view.findViewById(R.id.PasswordSignUpInput);
        return passwordInput.getText().toString();
    }

    /**
     * Get confirm password input.
     *
     * @return a confirm password input
     */
    private String getConfirmPasswordInput() {
        EditText confirmPasswordInput = this.view.findViewById(R.id.PasswordConfirmInput);
        return confirmPasswordInput.getText().toString();
    }

    /**
     * Set warning for username input.
     *
     * @param warning warning for username input
     */
    private void setUsernameInputIndicator(int warning) {
        TextView usernameIndicator = this.view.findViewById(R.id.UsernameSignUpIndicator);
        usernameIndicator.setText(warning);
    }

    /**
     * Set warning for confirm password input.
     *
     * @param warning warning for confirm password input
     */
    private void setPasswordConfirmIndicator(int warning) {
        TextView passwordIndicator = this.view.findViewById(R.id.PasswordConfirmIndicator);
        passwordIndicator.setText(warning);
    }
}
