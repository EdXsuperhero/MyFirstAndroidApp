package csc207project.gamecentre.MemoryMatchingPics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import csc207project.gamecentre.R;
public class MatchingMainActivity extends AppCompatActivity implements Observer{

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The Chronometer
     */
    Chronometer timer;

    /**
     * Set context.
     */
    private Context mContext = MatchingMainActivity.this;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;


    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    /**
     * Update the backgrounds on the buttons to match the cards.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int width = board.getWidth();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / width;
            int col = nextPos % width;
            b.setBackgroundResource(board.getCards()[row][col].getBackground());
            nextPos++;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(MatchingStartingActivity.TEMP_SAVE_FILENAME);
        createTileButtons(mContext);
        setContentView(R.layout.activity_matching_main);

        this.timer = findViewById(R.id.timer1);
        startTimer();

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(this.boardManager.getBoard().getWidth());
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();
                        int width = boardManager.getBoard().getWidth();

                        columnWidth = displayWidth / width;
                        columnHeight = displayHeight / width;

                        display();
                    }
                });


    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        int width = this.boardManager.getBoard().getWidth();
        for (int row = 0; row != width; row++) {
            for (int col = 0; col != width; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getCards()[row][col].getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }


    /**
     * Start the timer for this round.
     */
    private void startTimer() {
        long duration = this.boardManager.getDuration();
        this.timer.setBase(SystemClock.elapsedRealtime() - duration);
        this.timer.start();
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
                boardManager = (BoardManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
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
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
        this.boardManager.pushToStack();
        this.boardManager.setDuration(SystemClock.elapsedRealtime() - this.timer.getBase());
        saveToFile(MatchingStartingActivity.TEMP_SAVE_FILENAME);
    }
}
