package csc207project.gamecentre.MainMenu.GameLibFragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import csc207project.gamecentre.R;

/**
 * ViewHolder for games that is needed for RecyclerViewAdapter.
 *
 * This was adapted from an article by Droid By Me at :
 * https://medium.com/@droidbyme/android-recyclerview-fca74609725e
 * and from Android Guides at :
 * https://developer.android.com/guide/topics/ui/layout/recyclerview#java
 */
public class GameViewHolder extends RecyclerView.ViewHolder {

    /**
     * TextView for showing the game's name and description.
     */
    private TextView gameName, gameDescription;

    /**
     * Set a new ViewHolder for a game.
     *
     * @param itemView CardView for every RecyclerView item
     */
    public GameViewHolder(View itemView) {
        super(itemView);
        this.gameName = itemView.findViewById(R.id.GameName);
        this.gameDescription = itemView.findViewById(R.id.GameDescription);
    }

    /**
     * Set the game's own CardView.
     *
     * @param game the game to set
     */
    public void setView(Game game) {
        this.gameName.setText(game.getGameName());
        this.gameDescription.setText(game.getGameDescription());
    }
}
