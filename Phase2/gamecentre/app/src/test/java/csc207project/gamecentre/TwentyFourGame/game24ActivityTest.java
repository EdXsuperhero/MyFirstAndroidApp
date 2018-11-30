package csc207project.gamecentre.TwentyFourGame;

import android.widget.ImageView;

import org.junit.Test;

import static org.junit.Assert.*;

public class game24ActivityTest {

    game24Activity game24Activity = new game24Activity();
    checkSolvable checkSolvable = new checkSolvable();
    ImageView imageView1 = null;

    @Test
    public void generateNumber() {

        int[] testList = game24Activity.generateNumber();

        int actualLength = testList.length;
        int expectLength = 4;
        assertTrue(actualLength == expectLength);

        boolean expect = true;
        boolean actual = true;
        for (int i = 0; i > 4; i++) {
            if (testList[i] == 0) {
                actual = false;
            }
        }
        assertEquals(expect, actual);

    }

    @Test
    public void getSolvableDigits() {

        int[] testList = game24Activity.getSolvableDigits();
        boolean actual = checkSolvable.judgePoint24(testList);
        boolean expect = true;
        assertEquals(expect, actual);
    }

    @Test
    public void getValidNumber() {

        game24Activity.getValidNumber();

        assertFalse(game24Activity.a1 == 0);
        assertFalse(game24Activity.a2 == 0);
        assertFalse(game24Activity.a3 == 0);
        assertFalse(game24Activity.a4 == 0);
    }


    @Test
    public void checkIfNumber() {

        int actual1 = game24Activity.checkIfNumber("3");
        assertFalse(actual1 == 0);

    }

    @Test
    public void getFinalResult() {

        String test1 = ")))";
        String result1 = game24Activity.getFinalResult(test1);
        assertFalse(result1.equals("123"));

        String test2 = "1+2";
        String result2 = game24Activity.getFinalResult(test2);
        assertTrue(result2.equals("3"));
    }

    @Test
    public void judgeTransferable() {

        int test1 = game24Activity.judgeTransferable("1+2");
        int expect1 = 3;
        assertEquals(test1, expect1);

        int test2 = game24Activity.judgeTransferable("))");
        int expect2 = 0;
        assertEquals(test2, expect2);

    }

}
