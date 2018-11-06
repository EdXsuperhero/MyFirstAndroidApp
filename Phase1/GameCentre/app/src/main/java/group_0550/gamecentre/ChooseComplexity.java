package group_0550.gamecentre;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ChooseComplexity extends StartingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_complexity);

        add_three();
        add_four();
        add_five();
    }

    public void add_three(){
        Button three_by_three = findViewById(R.id.button10);
        three_by_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(3, 3);
                saveToFile(StartingActivity.SAVE_FILENAME);
                switchToOtherActivities(GameActivity.class);
            }

        });
    }

    public void add_four(){
        Button four_by_four = findViewById(R.id.button12);
        four_by_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(4, 4);
                saveToFile(StartingActivity.SAVE_FILENAME);
                switchToOtherActivities(GameActivity.class);

            }

        });
    }

    public void add_five(){
        Button five_by_five = findViewById(R.id.button13);
        five_by_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(5, 5);
                saveToFile(StartingActivity.SAVE_FILENAME);
                switchToOtherActivities(GameActivity.class);

            }

        });
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager.getBoard());
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}