package csc207project.gamecentre.OASIS;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ScoreManagerTest {

    private ScoreManager scoreManager;

    @Before
    public void setUp() {
        this.scoreManager = new ScoreManager();
        this.scoreManager.addScore("test1", 7L);
        this.scoreManager.addScore("test2", 10L);
        this.scoreManager.addScore("test3", 3L);
        this.scoreManager.addScore("test4", 8L);
        this.scoreManager.addScore("test5", 11L);
    }

    @Test
    public void testAddScoreToNewUser() {
        Long expected = 10L;
        this.scoreManager.addScore("test", expected);
        Long actual = this.scoreManager.getScore("test");
        assertEquals("add score to new user fails", expected, actual);
    }

    @Test
    public void testAddScoreToExistingUser() {
        Long expected = 5L;
        this.scoreManager.addScore("test", 10L);
        this.scoreManager.addScore("test", expected);
        Long actual = this.scoreManager.getScore("test");
        assertEquals("add score to existing user fails", expected, actual);
    }

    @Test
    public void testGetScoreOnNewUser() {
        Long expected = Long.MAX_VALUE;
        Long actual = this.scoreManager.getScore("test");
        assertEquals("get score from new user fails", expected, actual);
    }

    @Test
    public void testGetScoreOnExistingUser() {
        Long expected = 10L;
        this.scoreManager.addScore("test", expected);
        Long actual = this.scoreManager.getScore("test");
        assertEquals("get score from existing user fails", expected, actual);
    }

    @Test
    public void testGetFirstHighestScore() {
        List<Map.Entry<String, Long>> scores = this.scoreManager.getHighestFiveScores();
        String expected = "test3";
        String actual = scores.get(0).getKey();
        assertSame(expected, actual);
    }

    @Test
    public void testGetSecondHighestScore() {
        List<Map.Entry<String, Long>> scores = this.scoreManager.getHighestFiveScores();
        String expected = "test1";
        String actual = scores.get(1).getKey();
        assertSame(expected, actual);
    }

    @Test
    public void testGetThirdHighestScore() {
        List<Map.Entry<String, Long>> scores = this.scoreManager.getHighestFiveScores();
        String expected = "test4";
        String actual = scores.get(2).getKey();
        assertSame(expected, actual);
    }

    @Test
    public void testGetForthHighestScore() {
        List<Map.Entry<String, Long>> scores = this.scoreManager.getHighestFiveScores();
        String expected = "test2";
        String actual = scores.get(3).getKey();
        assertSame(expected, actual);
    }

    @Test
    public void testGetFifthHighestScore() {
        List<Map.Entry<String, Long>> scores = this.scoreManager.getHighestFiveScores();
        String expected = "test5";
        String actual = scores.get(4).getKey();
        assertSame(expected, actual);
    }
}