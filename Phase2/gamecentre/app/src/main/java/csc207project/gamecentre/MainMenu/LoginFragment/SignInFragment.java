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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import csc207project.gamecentre.R;
import csc207project.gamecentre.MainMenu.UserManager;

/**
 * The fragment that handles sign in process.
 * # Exclude from code coverage because it's a view class.
 */
public class SignInFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        this.view = view;

        String currentUser = this.userManager.getCurrentUser();
        if (currentUser != null) {
            EditText usernameInput = view.findViewById(R.id.UsernameSignInInput);
            usernameInput.setText(currentUser);
        }

        setUsernameInputIndicator(R.string.empty);
        setPasswordInputIndicator(R.string.empty);
        setStayLoginCheckBoxListener();
        addSignInButtonListener();
        addSignUpButtonListener();
        return view;
    }

    /**
     * Activate SignIn Button.
     */
    private void addSignInButtonListener() {
        Button signInButton = this.view.findViewById(R.id.SignInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getUsernameInput();
                String password = getPasswordInput();
                String result = userManager.signIn(username, password);

                switch (result) {
                    case "Username Error":
                        setUsernameInputIndicator(R.string.user_not_registered);
                        break;
                    case "Password Error":
                        setPasswordInputIndicator(R.string.wrong_password);
                        break;
                    default:
                        ((LoginActivity)getActivity()).finish();
                }
            }
        });
    }

    /**
     * Activate SignUp Button.
     */
    private void addSignUpButtonListener() {
        Button signUpButton = this.view.findViewById(R.id.SwitchToSignUpButton);
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
     * @return inputted username
     */
    private String getUsernameInput() {
        EditText usernameInput = this.view.findViewById(R.id.UsernameSignInInput);
        return usernameInput.getText().toString();
    }

    /**
     * Get the password input.
     *
     * @return inputted password
     */
    private String getPasswordInput() {
        EditText passwordInput = this.view.findViewById(R.id.PasswordSignInInput);
        return passwordInput.getText().toString();
    }

    /**
     * Get stay login status.
     */
    private void setStayLoginCheckBoxListener() {
        CheckBox stayLogin = this.view.findViewById(R.id.StayLogin);
        stayLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                userManager.setStayLogin(isChecked);
            }
        });
    }

    /**
     * Set warning for username input.
     *
     * @param warning warning for username input
     */
    private void setUsernameInputIndicator(int warning) {
        TextView usernameIndicator = this.view.findViewById(R.id.UsernameSignInIndicator);
        usernameIndicator.setText(warning);
    }

    /**
     * Set warning for password input.
     *
     * @param warning warning for password input
     */
    private void setPasswordInputIndicator(int warning) {
        TextView passwordIndicator = this.view.findViewById(R.id.PasswordSignInIndicator);
        passwordIndicator.setText(warning);
    }
}
