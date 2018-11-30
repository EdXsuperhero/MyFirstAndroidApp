package csc207project.gamecentre.SlidingTiles;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void swapTiles() {
        List<Tile> tile_t = new ArrayList<>();

        for (int i = 0; i < 9; i++){
            tile_t.add(new Tile(i, i));
        }

        Board board = new Board(3, tile_t);
        Tile t1 = board.getTile(1, 1);
        board.swapTiles(1, 1, 2, 2);
        Tile t2 = board.getTile(2, 2);
        boolean result = t1.equals(t2);
        assertTrue(result);
    }


    @Test
    public void getWidth() {
        List<Tile> tile_t = new ArrayList<>();

        for (int i = 0; i < 9; i++){
            tile_t.add(new Tile(i, i));
        }

        Board board = new Board(3, tile_t);
        assertEquals(3, board.getWidth());
    }


    @Test
    public void numTiles() {
        List<Tile> tile_t = new ArrayList<>();

        for (int i = 0; i < 9; i++){
            tile_t.add(new Tile(i, i));
        }

        Board board = new Board(3, tile_t);
        assertEquals(9, board.numTiles());

    }

    @Test
    public void getTile() {
        int res = 0;
        List<Tile> tile_t = new ArrayList<>();

        for (int i = 0; i < 16; i++){
            tile_t.add(new Tile(i, i));
        }
        Board b = new Board(4,tile_t);

        Iterator<Tile> it = tile_t.iterator();
        for (int row = 0; row != 4; row++){
            for (int col = 0; col != 4; col++){
                if (b.getTile(row, col) == it.next()){
                    res++;
                }
            }
        }

        assertEquals(16, res);
    }


    @Test
    public void cloneTest() {
        int res = 0;
        List<Tile> tile_t = new ArrayList<>();

        for (int i = 0; i < 16; i++){
            tile_t.add(new Tile(i, i));
        }

        Board b = new Board(4,tile_t);

        Board clone_b= b.clone();

        for (int row = 0; row != 4; row++){
            for (int col = 0; col != 4; col++){
                if (b.getTile(row, col)== clone_b.getTile(row, col)){
                    res++;
                }
            }
        }

        assertEquals(16, res);
    }

}

