package csc207project.gamecentre.MemoryMatching;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

import csc207project.gamecentre.R;
import csc207project.gamecentre.OASIS.*;

/**
 * The scoreboard activity for memory matching.
 */
public class MemoryMatchingScoreBoardActivity extends ScoreBoardActivity {

    /**
     * The save file name for storing score manager;
     */
    private String saveFileName = "memory_matching_score.ser";
    /**
     * The username of whom is playing this game.
     */
    private String currentUser;
    /**
     * A score manager.
     */
    private ScoreManager scoreManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile();
        Long score = getIntent().getLongExtra("score", Long.MAX_VALUE);
        this.currentUser = getIntent().getStringExtra("current_user");
        if (score != Long.MAX_VALUE) {
            this.scoreManager.addScore(this.currentUser, score);
        }
        saveToFile();

        setContentView(R.layout.activity_scoreboard);
        super.addBackToStartButtonListener();
        addUserHighestScoreListener();
        addHighestFiveScoresListener();
    }

    @Override
    protected void addUserHighestScoreListener() {
        TextView userHighestScoreText = findViewById(R.id.HighestScore);
        userHighestScoreText.setText(super.formatUsedTime(this.scoreManager.getScore(this.currentUser)));
    }

    @Override
    protected void addHighestFiveScoresListener() {
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

    @Override
    public void saveToFile() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(this.saveFileName, MODE_PRIVATE));
            outputStream.writeObject(this.scoreManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("MemoryMatching Scoreboard Activity", "File write failed: " + e.toString());
        }
    }

    @Override
    public void loadFromFile() {
        try {
            InputStream inputStream = this.openFileInput(this.saveFileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                this.scoreManager = (ScoreManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("MemoryMatching Scoreboard Activity", "File not found: " + e.toString());
            this.scoreManager = new ScoreManager();
            saveToFile();
        } catch (IOException e) {
            Log.e("MemoryMatching Scoreboard Activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("MemoryMatching Scoreboard Activity", "File contained unexpected data type: " + e.toString());
        }
    }
}
