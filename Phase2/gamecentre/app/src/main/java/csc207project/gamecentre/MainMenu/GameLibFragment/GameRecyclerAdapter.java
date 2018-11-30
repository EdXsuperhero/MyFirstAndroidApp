package csc207project.gamecentre.MainMenu.GameLibFragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import csc207project.gamecentre.R;

/**
 * A RecyclerAdapter based on games that is controlled by RecyclerView.
 * # Exclude from code coverage because it's a view class.
 *
 * This was adapted from an article by Droid By Me at :
 * https://medium.com/@droidbyme/android-recyclerview-fca74609725e
 * and from Android Guides at :
 * https://developer.android.com/guide/topics/ui/layout/recyclerview#java
 */
public class GameRecyclerAdapter extends RecyclerView.Adapter<GameViewHolder> {

    /**
     * A list for storing games.
     */
    private ArrayList<Game> games;

    /**
     * Set a new adapter for controlling the games.
     *
     * @param games a list of games to be controlled
     */
    public GameRecyclerAdapter(ArrayList<Game> games) {
        this.games = games;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_games, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = this.games.get(position);
        holder.setView(game);
    }

    @Override
    public int getItemCount() {
        return this.games.size();
    }
}
