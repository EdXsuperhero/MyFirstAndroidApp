package csc207project.gamecentre.TwentyFourGame;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

import csc207project.gamecentre.R;


public class ScoreBoard24GameActivity extends AppCompatActivity {

    public final static String SAVE_SCORE = "24game_save_score.ser";

    private ScoreManager scoreManager;

    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board24_game);

        loadScoreFromFile(SAVE_SCORE);
        Long score = getIntent().getLongExtra("score", Long.MAX_VALUE);
        this.currentUser = getIntent().getStringExtra("current_user");
        if (score != Long.MAX_VALUE) {
            this.scoreManager.addScore(this.currentUser, score);
            saveScoreToFile(SAVE_SCORE);
        }
        addBackToStartButtonListener();
        addUserHighestScoreListener();
        addHighestFiveScoresListener();

    }
    /**
     * Activate the button for navigating back to starting activity.
     */
    private void addBackToStartButtonListener() {
        Button backToStartButton = findViewById(R.id.BackToStart);
        backToStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveScoreToFile(SAVE_SCORE);
                finish();
            }
        });
    }

    /**
     * Show user's highest score.
     */
    private void addUserHighestScoreListener() {
        TextView userHighestScoreText = findViewById(R.id.HighestScore);
        userHighestScoreText.setText(formatUsedTime(this.scoreManager.getScore(this.currentUser)));
    }

    /**
     * Show highest 5 scores on the activity.
     */
    private void addHighestFiveScoresListener() {
        List<Map.Entry<String, Long>> highest5Scores = this.scoreManager.getHighestFiveScores();

        TextView top1UserText = findViewById(R.id.Top1User);
        TextView top1ScoreText = findViewById(R.id.Top1Score);
        top1UserText.setText(highest5Scores.get(0).getKey());
        top1ScoreText.setText(formatUsedTime(highest5Scores.get(0).getValue()));

        TextView top2UserText = findViewById(R.id.Top2User);
        TextView top2ScoreText = findViewById(R.id.Top2Score);
        top2UserText.setText(highest5Scores.get(1).getKey());
        top2ScoreText.setText(formatUsedTime(highest5Scores.get(1).getValue()));

        TextView top3UserText = findViewById(R.id.Top3User);
        TextView top3ScoreText = findViewById(R.id.Top3Score);
        top3UserText.setText(highest5Scores.get(2).getKey());
        top3ScoreText.setText(formatUsedTime(highest5Scores.get(2).getValue()));

        TextView top4UserText = findViewById(R.id.Top4User);
        TextView top4ScoreText = findViewById(R.id.Top4Score);
        top4UserText.setText(highest5Scores.get(3).getKey());
        top4ScoreText.setText(formatUsedTime(highest5Scores.get(3).getValue()));

        TextView top5UserText = findViewById(R.id.Top5User);
        TextView top5ScoreText = findViewById(R.id.Top5Score);
        top5UserText.setText(highest5Scores.get(4).getKey());
        top5ScoreText.setText(formatUsedTime(highest5Scores.get(4).getValue()));
    }
    /**
     * Take in a time in milliseconds and convert to a readable format.
     *
     * @param time time in milliseconds
     * @return time in a readable format
     */
    @NonNull
    private String formatUsedTime (@NonNull Long time) {
        String format = "";
        if (time.equals(Long.MAX_VALUE)) {
            format = "00:00";
        } else {
            long minute = (time / 1000) / 60;
            long second = (time / 1000) % 60;
            format = String.valueOf(minute) + ":";
            if (second > 9) {
                format = format + String.valueOf(second);
            } else {
                format = format + "0" + String.valueOf(second);
            }
        }
        return format;
    }
    /**
     * Save the score manager to fileName.
     *
     * @param fileName the name of the file
     */
    private void saveScoreToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(this.scoreManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Load the score manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadScoreFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                this.scoreManager = (ScoreManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("Source Board", "File not found: " + e.toString());
            this.scoreManager = new ScoreManager();
            saveScoreToFile(SAVE_SCORE);
        } catch (IOException e) {
            Log.e("Source Board", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Source Board", "File contained unexpected data type: " + e.toString());
        }
    }
}
