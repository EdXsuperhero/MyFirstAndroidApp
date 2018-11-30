package csc207project.gamecentre.MainMenu.GameLibFragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import csc207project.gamecentre.MainMenu.MainMenuActivity;
import csc207project.gamecentre.R;

/**
 * ViewHolder for games that is needed for RecyclerViewAdapter.
 * # Exclude from code coverage because it's a view class.
 *
 * This was adapted from an article by Droid By Me at :
 * https://medium.com/@droidbyme/android-recyclerview-fca74609725e
 * and from Android Guides at :
 * https://developer.android.com/guide/topics/ui/layout/recyclerview#java
 */
public class GameViewHolder extends RecyclerView.ViewHolder {

    /**
     * Set Context.
     */
    private Context mContext;

    /**
     * ImageView for showing the game's screenshot.
     */
    private ImageView gameImage;

    /**
     * TextView for showing the game's name and description.
     */
    private TextView gameName, gameDescription, gameStarter, scoreboardStarter;

    /**
     * Set a new ViewHolder for a game.
     *
     * @param itemView CardView for every RecyclerView item
     */
    public GameViewHolder(View itemView) {
        super(itemView);
        this.mContext = itemView.getContext();
        this.gameImage = itemView.findViewById(R.id.GameImage);
        this.gameName = itemView.findViewById(R.id.GameName);
        this.gameDescription = itemView.findViewById(R.id.GameDescription);
        this.gameStarter = itemView.findViewById(R.id.GameStarter);
        this.scoreboardStarter = itemView.findViewById(R.id.ScoreBoard);
    }

    /**
     * Set the game's own CardView.
     *
     * @param game the game to set
     */
    public void setView(Game game) {
        this.gameImage.setImageResource(game.getGameImage());
        this.gameName.setText(game.getGameName());
        this.gameDescription.setText(game.getGameDescription());
        this.gameStarter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(mContext, game.getStartGameClass());
                gameIntent.putExtra("current_user",
                        ((MainMenuActivity)mContext).getUserManager().getCurrentUser());
                mContext.startActivity(gameIntent);
            }
        });
        this.scoreboardStarter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreboardIntent = new Intent(mContext, game.getScoreboardClass());
                scoreboardIntent.putExtra("current_user",
                        ((MainMenuActivity)mContext).getUserManager().getCurrentUser());
                mContext.startActivity(scoreboardIntent);
            }
        });
    }
}
