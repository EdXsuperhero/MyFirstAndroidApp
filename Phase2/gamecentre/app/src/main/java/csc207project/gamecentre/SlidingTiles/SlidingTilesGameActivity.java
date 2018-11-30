package csc207project.gamecentre.SlidingTiles;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
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
import csc207project.gamecentre.OASIS.*;

/**
 * The game activity for sliding tiles.
 */
public class SlidingTilesGameActivity extends GameActivity implements Observer {

    /**
     * A temporary save file.
     */
    public static final String SAVE_FILE_NAME = "sliding_tiles_game.ser";
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
    private Chronometer timer;
    /**
     * Set context.
     */
    private Context mContext = this;

    /**
     * Constants for swiping directions. Should be an enum, probably.
     */
    public static final int ABOVE = 0;
    public static final int BELOW = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int CANNOTFIND = 4;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addUndoButtonListener();

        startNewGame();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int complexity = data.getIntExtra("complexity", 3);
        this.boardManager = new BoardManager(complexity);
        setupGame();
    }

    /**
     * Start a new game if there's no previous saved games,
     * else ask the user whether reload the game.
     */
    private void startNewGame() {
        if (checkTempFileExists()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("You have an unsolved puzzle!");
            builder.setMessage("Do you wan to continue?");
            builder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            loadFromFile();
                            setupGame();
                        }
                    });
            builder.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switchToChooseComplexity();
                        }
                    });
            builder.show();
        } else {
            switchToChooseComplexity();
        }
    }

    /**
     * Setup the game board and view for user to play.
     */
    private void setupGame() {
        createTileButtons(mContext);

        if (this.boardManager.getDuration() != 0) {
            resumeChronometer(this.boardManager.getDuration());
        } else {
            startChronometer();
        }

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
     * Check whether there is an unsolved puzzle.
     *
     * @return whether there is an unsolved puzzle
     */
    private boolean checkTempFileExists() {

        String[] filesLists = this.fileList();
        boolean exists = false;
        for (String file : filesLists) {
            if (file.equals(SAVE_FILE_NAME)) {
                exists = true;
            }
        }

        return exists;
    }

    /**
     * Switch to ChooseComplexity to choose game complexity.
     */
    private void switchToChooseComplexity() {
        Intent intent = new Intent(this, ChooseComplexity.class);
        startActivityForResult(intent, 0);
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
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int width = board.getWidth();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / width;
            int col = nextPos % width;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
    }

    /**
     * Activate Undo button.
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.UndoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean popResult = boardManager.popFromStack();
                if (popResult) {
                    boardManager.getBoard().addObserver(SlidingTilesGameActivity.this);
                    boardManager.getBoard().updateObservers();
                } else {
                    Toast.makeText(mContext, "Can't undo right now", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void startChronometer(){
        this.timer = findViewById(R.id.Timer);
        this.timer.setBase(SystemClock.elapsedRealtime());
        this.timer.start();
    }

    @Override
    public void resumeChronometer(long duration) {
        this.timer = findViewById(R.id.Timer);
        this.timer.setBase(SystemClock.elapsedRealtime() - duration);
        this.timer.start();
    }

    @Override
    public long getElapsedTime() {
        return (SystemClock.elapsedRealtime() - this.timer.getBase());
    }

    @Override
    protected void switchToScoreBoard(long score) {
        Intent toScoreIntent = new Intent(mContext, SlidingTilesScoreBoardActivity.class);
        toScoreIntent.putExtra("score", score);
        toScoreIntent.putExtra("current_user", getIntent().getStringExtra("current_user"));
        startActivity(toScoreIntent);
        finish();
    }

    @Override
    public void loadFromFile() {
        try {
            InputStream inputStream = this.openFileInput(SAVE_FILE_NAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (BoardManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("SlidingTiles Game Activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("SlidingTiles Game Activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("SlidingTiles Game Activity", "File contained unexpected data type: " + e.toString());
        }
    }

    @Override
    public void saveToFile() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(SAVE_FILE_NAME, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("SlidingTiles Game Activity", "File write failed: " + e.toString());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
        this.boardManager.pushToStack();
        this.boardManager.setDuration(getElapsedTime());
        saveToFile();
    }
}
