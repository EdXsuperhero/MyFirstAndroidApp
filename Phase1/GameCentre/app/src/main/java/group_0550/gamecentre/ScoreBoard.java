package group_0550.gamecentre;

import java.util.Observable;
import java.util.Observer;

public class ScoreBoard implements Observer {

    /**
     * the board to play.
     */
    private Board b;

    /**
     * the total steps that the player played.
     */
    private int TotalSteps = 1;

    /**
     * the Total Score that will be divided by total steps.
     */
    private int TotalScore = 10000;


    /***
     * initialise the score board.
     * @param b the board to play with.
     */
    public ScoreBoard(Board b){
        this.b = b;
        this.b.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        TotalSteps++;
    }

    /**
     * get the final score.
     * @return total score divide by total steps.
     */
    public int getTotalScore() {
        return TotalScore / TotalSteps;
    }
}
