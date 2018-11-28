package csc207project.gamecentre.MainMenu.LoginFragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import csc207project.gamecentre.R;
import csc207project.gamecentre.MainMenu.UserManager;

public class SignInFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        String currentUser = this.userManager.getCurrentUser();
        if (currentUser != null) {
            EditText usernameInput = view.findViewById(R.id.UsernameSignInInput);
            usernameInput.setText(currentUser);
        }

        setUsernameInputIndicator(view, "");
        setPasswordInputIndicator(view, "");
        addSignInButtonListener(view);
        addSignUpButtonListener(view);
        return view;
    }

    /**
     * Activate SignIn Button.
     *
     * @param view current view
     */
    private void addSignInButtonListener(View view) {
        Button signInButton = view.findViewById(R.id.SignInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getUsernameInput(view);
                if (userManager.isStoredUser(username)) {
                    String password = getPasswordInput(view);
                    if (userManager.signIn(username, password)) {
                        userManager.setCurrentUser(username);
                        userManager.setStayLogin(getStayLoginCheckBox(view));
                        getActivity().finish();
                    } else {
                        setPasswordInputIndicator(view, "Wrong Password");
                    }
                } else {
                    setUsernameInputIndicator(view, "User not Registered");
                }
            }
        });
    }

    /**
     * Activate SignUp Button.
     *
     * @param view current view
     */
    private void addSignUpButtonListener(View view) {
        Button signUpButton = view.findViewById(R.id.SwitchToSignUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getActivity()).replaceSignUpFragment();
            }
        });
    }

    /**
     * Get the username input.
     *
     * @param view current view
     * @return inputted username
     */
    private String getUsernameInput(View view) {
        EditText usernameInput = view.findViewById(R.id.UsernameSignInInput);
        return usernameInput.getText().toString();
    }

    /**
     * Get the password input.
     *
     * @param view current view
     * @return inputted password
     */
    private String getPasswordInput(View view) {
        EditText passwordInput = view.findViewById(R.id.PasswordSignInInput);
        return passwordInput.getText().toString();
    }

    /**
     * Get stay login status.
     *
     * @param view current view
     * @return whether stay login is checked
     */
    private boolean getStayLoginCheckBox(View view) {
        CheckBox stayLogin = view.findViewById(R.id.StayLogin);
        return stayLogin.isChecked();
    }

    /**
     * Set warning for username input.
     *
     * @param view current view
     * @param warning warning for username input
     */
    private void setUsernameInputIndicator(View view, String warning) {
        TextView usernameIndicator = view.findViewById(R.id.UsernameSignInIndicator);
        usernameIndicator.setText(warning);
    }

    /**
     * Set warning for password input.
     *
     * @param view current view
     * @param warning warning for password input
     */
    private void setPasswordInputIndicator(View view, String warning) {
        TextView passwordIndicator = view.findViewById(R.id.PasswordSignInIndicator);
        passwordIndicator.setText(warning);
    }
}
