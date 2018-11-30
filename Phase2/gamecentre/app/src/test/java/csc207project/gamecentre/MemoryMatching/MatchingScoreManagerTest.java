package csc207project.gamecentre.MemoryMatching;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class MatchingScoreManagerTest {

    @Test
    public void addScoreTest() {
        MatchingScoreManager testmanager = new MatchingScoreManager();
        testmanager.addScore("even", 2L);
        long evenscore = testmanager.getScore("even");
        assertEquals(2L, evenscore);
    }

    @Test
    public void getScoreTest() {
        MatchingScoreManager testmanager = new MatchingScoreManager();
        testmanager.addScore("ed", 5L);
        testmanager.addScore("ed", 4L);
        testmanager.addScore("ed", 6L);
        testmanager.addScore("ed", 8L);
        testmanager.addScore("ed", 1L);
        testmanager.addScore("ed", 5L);
        long edscore = testmanager.getScore("ed");
        assertEquals(1L, edscore);
    }

    @Test
    public void getHighestFiveScores() {
        MatchingScoreManager testmanager = new MatchingScoreManager();
        testmanager.addScore("tester1", 12L);
        testmanager.addScore("tester2", 17L);
        testmanager.addScore("tester3", 10L);
        testmanager.addScore("tester4", 8L);
        testmanager.addScore("tester5", 9L);
        testmanager.addScore("tester6", 18L);
        List<Map.Entry<String, Long>> getscore = testmanager.getHighestFiveScores();
        boolean nam1 = getscore.get(0).getKey().equals("tester4");
        boolean val1 = getscore.get(0).getValue().equals(8L);
        boolean nam2 = getscore.get(1).getKey().equals("tester5");
        boolean val2 = getscore.get(1).getValue().equals(9L);
        boolean nam3 = getscore.get(2).getKey().equals("tester3");
        boolean val3 = getscore.get(2).getValue().equals(10L);
        boolean nam4 = getscore.get(3).getKey().equals("tester1");
        boolean val4 = getscore.get(3).getValue().equals(12L);
        boolean nam5 = getscore.get(4).getKey().equals("tester2");
        boolean val5 = getscore.get(4).getValue().equals(17L);
        boolean result = nam1 && val1 && nam2 && val2 && nam3 && val3 && nam4 && val4 && nam5 && val5;
        assertTrue(result);
    }
}