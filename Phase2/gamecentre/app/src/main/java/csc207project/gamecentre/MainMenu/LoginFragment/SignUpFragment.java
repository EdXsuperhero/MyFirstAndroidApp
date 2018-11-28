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

public class SignUpFragment extends Fragment {

    /**
     * Current user manager we are working on.
     */
    private UserManager userManager;

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

        setUsernameInputIndicator(view, "");
        setPasswordConfirmIndicator(view, "");
        addSignUpButtonListener(view);

        return view;
    }

    /**
     * Activate Sign Up button.
     *
     * @param view current view
     */
    private void addSignUpButtonListener(View view) {
        Button signUpButton = view.findViewById(R.id.SignUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getUsernameInput(view);
                if (userManager.isStoredUser(username)) {
                    setUsernameInputIndicator(view, "Username Registered");
                } else {
                    String password = getPasswordInput(view);
                    String confirmPassword = getConfirmPasswordInput(view);
                    if (password.equals(confirmPassword)) {
                        userManager.signUp(username, password);
                        userManager.setCurrentUser(username);
                        ((LoginActivity) getActivity()).replaceSignInFragment();
                    } else {
                        setPasswordConfirmIndicator(view, "Password doesn't Match");
                    }
                }
            }
        });
    }

    /**
     * Get username input.
     *
     * @param view current view
     * @return a username input
     */
    private String getUsernameInput(View view) {
        EditText usernameInput = view.findViewById(R.id.UsernameSignUpInput);
        return usernameInput.getText().toString();
    }

    /**
     * Get password input.
     *
     * @param view current view
     * @return a password input
     */
    private String getPasswordInput(View view) {
        EditText passwordInput = view.findViewById(R.id.PasswordSignUpInput);
        return passwordInput.getText().toString();
    }

    /**
     * Get confirm password input.
     *
     * @param view current view
     * @return a confirm password input
     */
    private String getConfirmPasswordInput(View view) {
        EditText confirmPasswordInput = view.findViewById(R.id.PasswordConfirmInput);
        return confirmPasswordInput.getText().toString();
    }

    /**
     * Set warning for username input.
     *
     * @param view current view
     * @param warning warning for username input
     */
    private void setUsernameInputIndicator(View view, String warning) {
        TextView usernameIndicator = view.findViewById(R.id.UsernameSignUpIndicator);
        usernameIndicator.setText(warning);
    }

    /**
     * Set warning for confirm password input.
     *
     * @param view current view
     * @param warning warning for confirm password input
     */
    private void setPasswordConfirmIndicator(View view, String warning) {
        TextView passwordIndicator = view.findViewById(R.id.PasswordConfirmIndicator);
        passwordIndicator.setText(warning);
    }
}
