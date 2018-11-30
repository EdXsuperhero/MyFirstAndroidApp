package csc207project.gamecentre.MemoryMatching;

import org.junit.Test;

import csc207project.gamecentre.R;

import static org.junit.Assert.*;

public class MatchingBoardManagerTest {

    @Test
    public void cardAllMatchedTest() {
        BoardManager bm = new BoardManager(2);
        bm.touchMove(0);
        bm.touchMove(1);
        if (bm.getBoard().getMatched() == 2){
            bm.touchMove(2);
            bm.touchMove(3);
        }else{
            bm.touchMove(0);
            bm.touchMove(2);
            if (bm.getBoard().getMatched() == 2){
                bm.touchMove(1);
                bm.touchMove(3);
            }else{
                bm.touchMove(0);
                bm.touchMove(3);
                bm.touchMove(1);
                bm.touchMove(2);
            }
        }
        assertTrue(bm.cardAllMatched());

    }

    @Test
    public void touchMoveTest() {
        BoardManager bm = new BoardManager(2);
        bm.touchMove(0);
        bm.touchMove(0);
        assertEquals(R.drawable.card_0, bm.getBoard().getCards()[0][0].getBackground());
    }

    @Test
    public void BoardStackTest() {
        int res = 0;
        BoardManager bm = new BoardManager(2);
        Board b1 = bm.getBoard();
        Board b2 = bm.getBoardStack().pop();
        for (int row=0; row != 2; row++){
            for (int col=0; col != 2; col++){
                if (b1.getCards()[row][col] ==
                        b2.getCards()[row][col]){
                    res++;
                }
            }
        }
        assertEquals(4, res);
    }

    @Test
    public void setAndGetDurationTest() {
        BoardManager bm = new BoardManager(2);
        bm.setDuration(20);
        assertEquals(20, bm.getDuration());
    }
}