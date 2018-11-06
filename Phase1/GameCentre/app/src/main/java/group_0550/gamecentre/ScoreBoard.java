package group_0550.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ScoreBoard implements Serializable {
    private HashMap<String, Integer> userScoreMap;

    public ScoreBoard() {
        this.userScoreMap = new HashMap<>();
    }

    public void addScore(String username, Integer newScore) {
        this.userScoreMap.putIfAbsent(username, 0);
        Integer currentScore = this.userScoreMap.get(username);
        if (currentScore < newScore) {
            this.userScoreMap.replace(username, newScore);
        }
    }

    public String getHighestScore(String username) {
        return this.userScoreMap.get(username).toString();
    }

    public ArrayList<String> getHighest5Scores() {
        Set keys = this.userScoreMap.keySet();
        Iterator iterkeys = keys.iterator();
        ArrayList<String> scores = new ArrayList<>();
        while (iterkeys.hasNext()) {
            String key = (String) iterkeys.next();
            String score = this.userScoreMap.get(key).toString() +
                    ',' + key;
            scores.add(score);
            System.out.println(scores);
        }
        Collections.sort(scores, Collections.<String>reverseOrder());
        for (int i = 0; i <= 5; i++) {
            scores.add("0,Nobody" + new Integer(i).toString(i));
        }
        return new ArrayList<>(scores.subList(0, 5));
    }

}
