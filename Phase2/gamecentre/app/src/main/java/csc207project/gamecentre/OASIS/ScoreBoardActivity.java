package csc207project.gamecentre.OASIS;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import csc207project.gamecentre.R;

/**
 * Abstract scoreboard activity for factory design.
 * # Exclude from tests because it's an abstract view class.
 */
public abstract class ScoreBoardActivity extends AppCompatActivity implements ReadWritePrivilege {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    /**
     * Activate the button for navigating back to starting activity.
     */
    protected void addBackToStartButtonListener() {
        Button backToStartButton = findViewById(R.id.BackToStart);
        backToStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile();
                finish();
            }
        });
    }

    /**
     * Show user's highest score.
     */
    protected abstract void addUserHighestScoreListener();

    /**
     * Show highest 5 scores on the activity.
     */
    protected abstract void addHighestFiveScoresListener();

    /**
     * Take in a time in milliseconds and convert to a readable format.
     *
     * @param time time in milliseconds
     * @return time in a readable format
     */
    @NonNull
    protected String formatUsedTime (@NonNull Long time) {
        String format = "";

        if (time == Long.MAX_VALUE) {
            format = "00:00";
        } else {

            Long minute = (time / 1000) / 60;
            Long second = (time / 1000) % 60;

            if (minute > 9) {
                format += minute.toString();
            } else {
                format += "0" + minute.toString();
            }

            format += ":";

            if (second > 9) {
                format += second.toString();
            } else {
                format += "0" + second.toString();
            }
        }
        return format;
    }

    /**
     * Save the score manager to file.
     */
    public abstract void saveToFile();

    /**
     * Load the score manager from file.
     */
    public abstract void loadFromFile();
}
