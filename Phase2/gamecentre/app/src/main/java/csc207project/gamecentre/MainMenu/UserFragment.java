package csc207project.gamecentre.MainMenu;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import csc207project.gamecentre.R;

/**
 * A fragment that handles user modify processes.
 * # Exclude from code coverage because it's a view class.
 */
public class UserFragment extends Fragment {

    /**
     * A user manager from MainMenuActivity.
     */
    private UserManager userManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainMenuActivity activity = (MainMenuActivity) getActivity();
        this.userManager = activity.getUserManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        setWelcomeUserText(view);
        addChangeUsernameButtonListener(view);
        addChangePasswordButtonListener(view);
        addLogoutButtonListener(view);

        return view;
    }

    /**
     * Set username under welcome title.
     *
     * @param view current view
     */
    private void setWelcomeUserText(View view) {
        TextView welcomeUserText = view.findViewById(R.id.welcomeUser);
        welcomeUserText.setText(this.userManager.getCurrentUser());
    }

    /**
     * Activate change username button.
     * Well, actually it is a TextView.
     *
     * @param view current view
     */
    private void addChangeUsernameButtonListener(View view) {
        TextView changeUsername = view.findViewById(R.id.ChangeUsername);
        changeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_change_username, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(dialogView);
                builder.setTitle("Please Input Your New Username");
                builder.setPositiveButton(R.string.change_username,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText newUsernameInput = dialogView.findViewById(R.id.NewUsername);
                        String newUsername = newUsernameInput.getText().toString();
                        if (userManager.isStoredUser(newUsername)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage(R.string.username_exists);
                            builder.setPositiveButton(R.string.yes,
                                    new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        } else {
                            userManager.changeUsername(newUsername);
                            setWelcomeUserText(view);
                        }
                    }
                });
                builder.setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    /**
     * Activate change password button.
     * Well, actually it is also a TextView.
     *
     * @param view current view
     */
    private void addChangePasswordButtonListener(View view) {
        TextView changePassword = view.findViewById(R.id.ChangePassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_change_password, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(dialogView);
                builder.setTitle("Please Input Your New Password");
                builder.setPositiveButton(R.string.change_password,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText newPasswordInput = dialogView.findViewById(R.id.NewPassword);
                        String newPassword = newPasswordInput.getText().toString();
                        EditText newPasswordConfirmInput = dialogView.findViewById(R.id.NewPasswordConfirm);
                        String newConfirmPassword = newPasswordConfirmInput.getText().toString();
                        if (newPassword.equals(newConfirmPassword)) {
                            userManager.changePassword(newPassword);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage(R.string.password_doesnt_match);
                            builder.setPositiveButton(R.string.yes,
                                    new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        }
                    }
                });
                builder.setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    /**
     * Activate Logout button.
     * Do I have to say it for the third time? It is still a TextView!
     *
     * @param view current view
     *
     * Adapted code from : https://stackoverflow.com/questions/46070938/restarting-android-app-programmatically
     */
    private void addLogoutButtonListener(View view) {
        TextView logout = view.findViewById(R.id.Logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userManager.setStayLogin(false);
                MainMenuActivity activity = (MainMenuActivity) getActivity();
                Intent restart = activity.getBaseContext()
                        .getPackageManager()
                        .getLaunchIntentForPackage(
                                activity.getBaseContext()
                                        .getPackageName()
                        );
                restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                restart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(restart);
                activity.finishAffinity();
            }
        });
    }
}
