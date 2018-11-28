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

import csc207project.gamecentre.R;

public class SignInFragment extends Fragment {

    /**
     * Current Activity for data interaction.
     */
    private LoginActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (LoginActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        String currentUser = mActivity.getCurrentUsername();
        if (currentUser != null) {
            EditText usernameInput = view.findViewById(R.id.UsernameSignInInput);
            usernameInput.setText(currentUser);
        }

        addSignInButtonListener(view);
        addSignUpButtonListener(view);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
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
                String password = getPasswordInput(view);

            }
        });
    }

    /**
     * Activate SignUp Button.
     *
     * @param view current view
     */
    private void addSignUpButtonListener(View view) {
        Button signUpButton = view.findViewById(R.id.SignUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.replaceSignUpFragment();
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
}
