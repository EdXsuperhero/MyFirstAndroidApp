package csc207project.gamecentre.MemoryMatching;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class MovementController {

    private BoardManager boardManager;

    private Context mContext;

    MovementController(){
    }

    void setBoardManager(BoardManager boardManager){this.boardManager = boardManager;}

    BoardManager getBoardManager(){return boardManager;}

    void processTapMovement(Context context, int position, boolean display){
        this.mContext = context;

        boardManager.touchMove(position);
        if (boardManager.cardAllMatched()) {
            mContext.deleteFile(MemoryMatchingGameActivity.SAVE_FILE_NAME);
            long duration = ((MemoryMatchingGameActivity)mContext).getElapsedTime();
            String min = getUsedTime(duration)[0];
            String sec = getUsedTime(duration)[1];
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("You Win!")
                    .setMessage("You have used " + min + " minutes " + sec + " seconds.")
                    .setPositiveButton("Take me to ScoreBoard",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((MemoryMatchingGameActivity)mContext).switchToScoreBoard(duration);
                                }
                            })
                    .show();
        }
    }

    /**
     * Get the time in milliseconds to readable format.
     *
     * @param duration time player used in the game
     * @return readable format of time
     */
    private String[] getUsedTime(long duration) {
        long minute = (duration / 1000) / 60;
        long second = (duration / 1000) % 60;
        return new String[]{String.valueOf(minute), String.valueOf(second)};
    }
}
