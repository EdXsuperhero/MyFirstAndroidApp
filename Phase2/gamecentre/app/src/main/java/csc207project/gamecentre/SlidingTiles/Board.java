package csc207project.gamecentre.SlidingTiles;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Iterable<Tile>, Serializable, Cloneable {

    /**
     * The width of this board.
     */
    private int width;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param width the width for the board
     * @param tiles the tiles for the board
     */
    Board(int width, List<Tile> tiles) {
        this.width = width;
        this.tiles = new Tile[this.width][this.width];

        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != this.width; row++) {
            for (int col = 0; col != this.width; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        Tile tmpTile = this.tiles[row1][col1];
        this.tiles[row1][col1] = this.tiles[row2][col2];
        this.tiles[row2][col2] = tmpTile;

        updateObservers();
    }

    /**
     * Notify Observers to update the board.
     */
    void updateObservers() {
        setChanged();
        notifyObservers();
    }

    /**
     * Return the width of this board.
     *
     * @return the width of this board
     */
    int getWidth() {
        return width;
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return (int) Math.pow(this.width, 2);
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return this.tiles[row][col];
    }

    @Override
    protected Board clone() {
        List<Tile> cloneTiles = new ArrayList<>();
        for (int row = 0; row != this.width; row++) {
            for (int col = 0; col != this.width; col++) {
                cloneTiles.add(this.tiles[row][col]);
            }
        }
        return new Board(this.width, cloneTiles);
    }


    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(this.tiles) +
                '}';
    }

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator();
    }

    /**
     * The iterator for the board.
     */
    private class BoardIterator implements Iterator<Tile> {

        /**
         * The position we are at during iteration.
         */
        private int position = 0;

        @Override
        public boolean hasNext() {
            return this.position < numTiles();
        }

        @Override
        public Tile next() {

            int currentRow = this.position / width;
            int currentCol = this.position % width;
            this.position++;

            return tiles[currentRow][currentCol];
        }
    }
}
