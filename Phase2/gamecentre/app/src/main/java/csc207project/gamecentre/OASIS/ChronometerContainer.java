package csc207project.gamecentre.OASIS;

public interface ChronometerContainer {

    /**
     * For starting a new Chronometer.
     */
    void startChronometer();

    /**
     * For resuming a Chronometer.
     *
     * @param duration the time that user has used
     */
    void resumeChronometer(long duration);

    /**
     * Get time that user has spent on the game.
     *
     * @return the time that user has spent on the game
     */
    long getElapsedTime();
}
