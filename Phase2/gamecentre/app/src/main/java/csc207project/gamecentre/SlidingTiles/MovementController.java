package csc207project.gamecentre.SlidingTiles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;


class MovementController {

    private BoardManager boardManager;

    private Context mContext;

    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    void processTapMovement(Context context, int position, boolean display) {
        this.mContext = context;
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                mContext.deleteFile(SlidingTilesGameActivity.SAVE_FILE_NAME);
                long duration = ((SlidingTilesGameActivity)mContext).getElapsedTime();
                String minutes = getUsedTime(duration)[0];
                String seconds = getUsedTime(duration)[1];
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("You Win!")
                        .setMessage("You have used " + minutes + " minutes " + seconds + " seconds.")
                        .setPositiveButton("Take me to ScoreBoard",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ((SlidingTilesGameActivity)mContext).switchToScoreBoard(duration);
                                    }
                                })
                        .show();
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
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
