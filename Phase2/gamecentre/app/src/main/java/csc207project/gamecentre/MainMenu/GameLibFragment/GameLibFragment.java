package csc207project.gamecentre.MainMenu.GameLibFragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import csc207project.gamecentre.MemoryMatching.MemoryMatchingGameActivity;
import csc207project.gamecentre.MemoryMatching.MemoryMatchingScoreBoardActivity;
import csc207project.gamecentre.SlidingTiles.SlidingTilesGameActivity;
import csc207project.gamecentre.SlidingTiles.SlidingTilesScoreBoardActivity;
import csc207project.gamecentre.TwentyFourGame.TwentyFourGameScoreBoardActivity;
import csc207project.gamecentre.TwentyFourGame.game24Activity;
import csc207project.gamecentre.R;

/**
 * A Fragment showing the game library.
 * Based on a CardView based RecyclerView.
 * # Exclude from code coverage because it's a view class.
 *
 * This was adapted from an article by Droid By Me at :
 * https://medium.com/@droidbyme/android-recyclerview-fca74609725e
 * and from Android Guides at :
 * https://developer.android.com/guide/topics/ui/layout/recyclerview#java
 */
public class GameLibFragment extends Fragment {

    /**
     * Set the context.
     */
    private Context mContext;

    /**
     * A list for storing the games.
     */
    private ArrayList<Game> games = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    /**
     * This was a problem and I modified it based on a solution at :
     * https://stackoverflow.com/questions/48462564/error-void-recyclerview-setlayoutmanagerrecyclerviewlayoutmanager-on-a
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_lib, container, false);
        RecyclerView gameRecycler = view.findViewById(R.id.GameRecyclerView);
        gameRecycler.setLayoutManager(new LinearLayoutManager(this.mContext,
                LinearLayoutManager.HORIZONTAL, false));
        gameRecycler.setAdapter(new GameRecyclerAdapter(this.games));
        createListData();
        return view;
    }

    /**
     * Add games to the list.
     */
    private void createListData() {
        this.games.add(
                new Game(R.drawable.sliding_tiles_screenshot,
                        R.string.sliding_tiles,
                        R.string.sliding_tiles_des,
                        SlidingTilesGameActivity.class,
                        SlidingTilesScoreBoardActivity.class)
        );
        this.games.add(
                new Game(R.drawable.twenty_four_game_screenshot,
                        R.string.twenty_four_game,
                        R.string.twenty_four_game_des,
                        game24Activity.class,
                        TwentyFourGameScoreBoardActivity.class)
        );
        this.games.add(
                new Game(R.drawable.matching_game_screenshot,
                        R.string.memory_matching,
                        R.string.memory_matching_des,
                        MemoryMatchingGameActivity.class,
                        MemoryMatchingScoreBoardActivity.class)
        );
    }

}
