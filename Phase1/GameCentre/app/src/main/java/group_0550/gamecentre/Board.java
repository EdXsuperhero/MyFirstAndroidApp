package group_0550.gamecentre;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The number of rows.
     */
    private int num_rows;

    /**
     * The number of rows.
     */
    private int num_cols;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board(int num_rows, int num_cols, List<Tile> tiles) {
        this.num_rows = num_rows;
        this.num_cols = num_cols;
        Iterator<Tile> iter = tiles.iterator();
        this.tiles = new Tile[this.num_rows][this.num_cols];

        for (int row = 0; row != this.num_rows; row++) {
            for (int col = 0; col != this.num_cols; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Return the number of tiles on the board.
     * @return the number of tiles on the board
     */
    int numTiles() {
        return this.num_rows * this.num_cols;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    public int getNumRows() {
        return num_rows;
    }

    public int getNumCols() {
        return num_cols;
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
        Tile tempTile = this.tiles[row1][col1];
        this.tiles[row1][col1] = this.tiles[row2][col2];
        this.tiles[row2][col2] = tempTile;

        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator(this.num_rows, this.num_cols);
    }

    /**
     * Iterator for Tiles iteration on board.
     */
    private class BoardIterator implements Iterator<Tile> {

        /**
         * The position of next Tile on board.
         */
        int nextPosition = 0;

        private int num_rows;
        private int num_cols;

        public BoardIterator(int num_rows, int num_cols) {
            this.num_rows = num_rows;
            this.num_cols = num_cols;
        }

        @Override
        public boolean hasNext() {
            return nextPosition != this.num_rows * this.num_cols;
        }

        @Override
        public Tile next() {
            Tile nextTile = tiles[nextPosition / this.num_cols][nextPosition % this.num_cols];
            nextPosition++;
            return nextTile;
        }
    }
}
