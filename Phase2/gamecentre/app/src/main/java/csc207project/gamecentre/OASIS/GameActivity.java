package csc207project.gamecentre.OASIS;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Abstract game activity for factory design.
 * # Exclude from tests because it's an abstract view class.
 */
public abstract class GameActivity extends AppCompatActivity implements ReadWritePrivilege, ChronometerContainer {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Start the chronometer.
     */
    public abstract void startChronometer();

    /**
     * Resume the chronometer based on duration.
     *
     * @param duration the time has already used
     */
    public abstract void resumeChronometer(long duration);

    /**
     * @return get used time on this game
     */
    public abstract long getElapsedTime();

    /**
     * Switch from current Game to ScoreBoard.
     *
     * @param score the score of this game
     */
    protected abstract void switchToScoreBoard(long score);

    /**
     * Save the game to file.
     */
    public abstract void saveToFile();

    /**
     * Load the game from file.
     */
    public abstract void loadFromFile();
}
