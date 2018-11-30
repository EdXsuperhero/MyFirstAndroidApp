package csc207project.gamecentre.SlidingTiles;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import csc207project.gamecentre.R;

import static org.junit.Assert.*;

public class BoardManagerTest {

    @Test
    public void isValidTap() {
        boolean result = true;
        BoardManager bm = new BoardManager(2);
        if(bm.getBoard().getTile(1, 1).getId() == 0){
            result = false;
        } else if(bm.getBoard().getTile(0, 0).getId() == 0){
            result = false;
        }
        boolean valid = bm.isValidTap(3);
        boolean equal = valid == result;
        assertTrue(equal);
    }

    @Test
    public void pushandpopToStack() {
        int res = 0;
        BoardManager bm = new BoardManager(2);
        bm.pushToStack();

        boolean result = bm.popFromStack();
        assertTrue(result);
    }

    @Test
    public void setandgetDuration() {
        BoardManager bm = new BoardManager(2);
        bm.setDuration(20);
        assertEquals(20, bm.getDuration());
    }

    @Test
    public void getBoard() {
        BoardManager bm = new BoardManager(4);
        int res = 0;
        List<Tile> tile_t = new ArrayList<>();
        for (int i = 0; i < 16; i++){
            tile_t.add(new Tile(i, i));
        }
        Board our_board = new Board(4, tile_t);
        assertNotEquals(our_board, bm.getBoard());
    }
}