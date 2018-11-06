package group_0550.gamecentre;

import android.content.Intent;
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
import java.util.ArrayList;

public class ScoreBoardActivity extends AppCompatActivity {

    public static final String SAVE_SCORE = "save_score.ser";
    public static final String TEMP_SAVE_SCORE = "tem_save_score.ser";
    public static final String username = LoginSystemActivity.getCurrentUserName();

    private ScoreBoard scoreBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadFromFile(SAVE_SCORE);
        if (this.scoreBoard == null) {
            this.scoreBoard = new ScoreBoard();
        }
        loadFromTempFile(TEMP_SAVE_SCORE);
        saveToFile(SAVE_SCORE);

        setContentView(R.layout.activity_scoreboard);
        addBackToStartButton();
        editUserHighestScore();
        editHighestScore();
    }

    private void addBackToStartButton() {
        Button backToStartButton = findViewById(R.id.BacktoStart);
        backToStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(SAVE_SCORE);
                switchToStart();
            }
        });
    }

    private void editUserHighestScore() {
        TextView userHighestScoreText = findViewById(R.id.HighestScore);
        userHighestScoreText.setText(this.scoreBoard.getHighestScore(username));
    }


    private void editHighestScore() {
        ArrayList<String> highest5Scores = this.scoreBoard.getHighest5Scores();

        TextView top1UserText = findViewById(R.id.Top1User);
        TextView top1ScoreText = findViewById(R.id.Top1Score);
        top1UserText.setText(highest5Scores.get(0).split(",")[1]);
        top1ScoreText.setText(highest5Scores.get(0).split(",")[0]);

        TextView top2UserText = findViewById(R.id.Top2User);
        TextView top2ScoreText = findViewById(R.id.Top2Score);
        top2UserText.setText(highest5Scores.get(1).split(",")[1]);
        top2ScoreText.setText(highest5Scores.get(1).split(",")[0]);

        TextView top3UserText = findViewById(R.id.Top3User);
        TextView top3ScoreText = findViewById(R.id.Top3Score);
        top3UserText.setText(highest5Scores.get(2).split(",")[1]);
        top3ScoreText.setText(highest5Scores.get(2).split(",")[0]);

        TextView top4UserText = findViewById(R.id.Top4User);
        TextView top4ScoreText = findViewById(R.id.Top4Score);
        top4UserText.setText(highest5Scores.get(3).split(",")[1]);
        top4ScoreText.setText(highest5Scores.get(3).split(",")[0]);

        TextView top5UserText = findViewById(R.id.Top5User);
        TextView top5ScoreText = findViewById(R.id.Top5Score);
        top5UserText.setText(highest5Scores.get(4).split(",")[1]);
        top5ScoreText.setText(highest5Scores.get(4).split(",")[0]);
    }

    private void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(this.scoreBoard);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                this.scoreBoard = (ScoreBoard) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("Source Board", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Source Board", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Source Board", "File contained unexpected data type: " + e.toString());
        }
    }


    private void loadFromTempFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                this.scoreBoard.addScore(username, (Integer) input.readObject());
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("Source Board", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Source Board", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("Source Board", "File contained unexpected data type: " + e.toString());
        }
    }

    private void switchToStart() {
        Intent tmp = new Intent(this, StartingActivity.class);
        startActivity(tmp);
    }
}
