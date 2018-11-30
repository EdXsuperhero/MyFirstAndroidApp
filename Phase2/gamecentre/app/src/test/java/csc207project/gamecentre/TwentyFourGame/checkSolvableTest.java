package csc207project.gamecentre.TwentyFourGame;

import org.junit.Test;

import static org.junit.Assert.*;

public class checkSolvableTest {

    @Test
    public void judgePoint24() {

        checkSolvable checkSolvable = new checkSolvable();

        int [] testTrue = new int[4];
        testTrue[0] = 2;
        testTrue[1] = 3;
        testTrue[2] = 4;
        testTrue[3] = 5;
        assertTrue(checkSolvable.judgePoint24(testTrue));

        int [] testFalse = new int[4];
        for(int i = 0; i < 4; i++){
            testFalse[i] = 1;
        }
        assertFalse(checkSolvable.judgePoint24(testFalse));
    }
}