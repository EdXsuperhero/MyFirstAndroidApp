package csc207project.gamecentre.SlidingTiles;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import csc207project.gamecentre.GoFor24.game24Activity;
import csc207project.gamecentre.R;


/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {

    /**
     * The main save file.
     */
    public static final String SAVE_FILENAME = "save_file.ser";
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp.ser";
    /**
     * The board manager.
     */
    BoardManager boardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.boardManager = new BoardManager(4);

        setContentView(R.layout.activity_starting_);
        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
        add24PointsButtonListener();

    }
    private void add24PointsButtonListener(){
        Button btn24Points = findViewById(R.id.btn24Points);
        btn24Points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tmp;
                tmp = new Intent(getApplicationContext(), game24Activity.class);
                startActivity(tmp);

            }
        });
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTempFileExists()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StartingActivity.this);
                    builder.setTitle("You have an unsolved puzzle!")
                            .setMessage("Do you want to continue?")
                            .setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            loadFromFile(TEMP_SAVE_FILENAME);
                                            switchToGame();
                                        }
                                    })
                            .setNegativeButton("NO",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switchToChooseComplexity();
                                        }
                                    })
                            .show();
                } else {
                    switchToChooseComplexity();
                }
            }
        });
    }

    /**
     * Check whether there is an unsolved puzzle.
     *
     * @return whether there is an unsolved puzzle
     */
    private boolean checkTempFileExists() {

        String[] filesLists = this.fileList();
        boolean exists = false;
        for (String file : filesLists) {
            if (file.equals(TEMP_SAVE_FILENAME)) {
                exists = true;
            }
        }

        return exists;
    }

    /**
     * Switch to ChooseComplexity to choose game complexity.
     */
    private void switchToChooseComplexity() {
        Intent chooseComplexityIntent = new Intent(this, ChooseComplexity.class);
        startActivity(chooseComplexityIntent);
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(SAVE_FILENAME);
                saveToFile(TEMP_SAVE_FILENAME);
                makeToastLoadedText();
                switchToGame();
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(SAVE_FILENAME);
                saveToFile(TEMP_SAVE_FILENAME);
                makeToastSavedText();
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    void switchToGame() {
        saveToFile(TEMP_SAVE_FILENAME);
        Intent tmp = new Intent(this, GameActivity.class);
        startActivity(tmp);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                this.boardManager = (BoardManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("SlidingTiles", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("SlidingTiles", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("SlidingTiles", "File contained unexpected data type: " + e.toString());
        }
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
            outputStream.writeObject(this.boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("SlidingTiles", "File write failed: " + e.toString());
        }
    }


}


