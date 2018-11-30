package csc207project.gamecentre.TwentyFourGame;



import android.os.Build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.os.Build.VERSION_CODES.N;

/**
 * Manage a scores list with username and score.
 */
class ScoreManager implements Serializable {

    /**
     * A HashMap storing usernames and scores.
     */
    private HashMap<String, Long> scores = new HashMap<>();

    ScoreManager() {
        for (int i = 0; i < 5; i++) {
            scores.put("Nobody_" + i, Long.MAX_VALUE);
        }
    }

    /**
     * Add a new score to a user.
     *
     * @param username the username to add new score
     * @param score the new score
     */
    public void addScore(String username, Long score) {
        if (isStoredUser(username)) {
            Long prevScore = this.scores.get(username);
            if (score < prevScore){
                if (Build.VERSION.SDK_INT >= N) {
                    this.scores.replace(username, score);
                }
            }
        } else {
            this.scores.put(username, score);
        }
    }

    /**
     * Get the highest score of a user.
     *
     * @param username the username to get score
     * @return the highest score of username
     */
    Long getScore(String username) {
        if (isStoredUser(username)) {
            return this.scores.get(username);
        }
        return null;
    }

    /**
     * Get the highest 5 scores of all users.
     *
     * @return the highest 5 scores
     */
    List<Map.Entry<String, Long>> getHighestFiveScores() {
        List<Map.Entry<String, Long>> scoresList = new ArrayList<>(scores.entrySet());
        if (Build.VERSION.SDK_INT >= N) {
            scoresList.sort(Map.Entry.comparingByValue());
        }

        return scoresList.subList(0, 5);
    }

    /**
     * Return whether the username is in the scores.
     *
     * @param username the username to check
     * @return whether the username is in the scores
     */
    private boolean isStoredUser(String username) {
        return this.scores.containsKey(username);
    }
}
