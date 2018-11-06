package group_0550.gamecentre;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;


public class MovementController {

    private BoardManager boardManager = null;

    private int steps;

    public MovementController() {
        this.steps = 1;
    }

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    public void processTapMovement(final Context context, int position, boolean display) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            this.steps++;
            if (boardManager.puzzleSolved()) {
                int score = 10000 / steps;
                try {
                    ObjectOutputStream outputStream = new ObjectOutputStream(
                            context.openFileOutput("tem_save_score.ser", Context.MODE_PRIVATE));
                        outputStream.writeObject(score);
                        outputStream.close();
                    } catch (IOException e) {
                        Log.e("Exception", "File write failed: " + e.toString());
                    }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("You Win!")
                        .setMessage("Your Score is: " + score)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                context.startActivity(new Intent(context.getApplicationContext(), ScoreBoardActivity.class));
                            }
                        })
                        .show();
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
