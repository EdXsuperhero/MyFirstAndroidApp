package group_0550.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;

    /**
     * The ScoreBoard to store the score.
     */
    private ScoreBoard Score;

    /**
     * Manage a board that has been pre-populated.
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
        this.Score = new ScoreBoard(board);
    }

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }
    //int getScore() {return Score.getTotalScore();}

    /**
     * Manage a new shuffled board.
     */
    BoardManager() {
        List<Tile> tiles = new ArrayList<>();
        int numTiles = Board.NUM_TILES;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }

        Collections.shuffle(tiles);
        this.board = new Board(tiles);
    }


    /*BoardManager(int size) {
        List<Tile> tiles = new ArrayList<>();
        Board.NUM_ROWS = size;
        Board.NUM_COLS = size;
        final int numTiles = Board.NUM_TILES;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }

        Collections.shuffle(tiles);
        this.board = new Board(tiles);
    }/*

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        boolean solved = true;
        Iterator<Tile> iterTile = this.board.iterator();
        int tileNum = 1;
        //Tile next_tile = iterTile.next();
        while (iterTile.hasNext() && solved) {
            if (iterTile.next().getId() != tileNum) {
                solved = false;
                }
            tileNum++;
            if (tileNum == Board.NUM_TILES){
                tileNum = 0;
            }
            }
            //next_tile = iterTile.next();
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {
        int row = position / Board.NUM_COLS;
        int col = position % Board.NUM_COLS;
        int blankId = 0;
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {
        int row = position / Board.NUM_ROWS;
        int col = position % Board.NUM_COLS;
        int blankId = 0;

        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
        if (above != null && above.getId() == blankId) {
            board.swapTiles(row, col, row - 1, col);
        } else if (below != null && below.getId() == blankId) {
            board.swapTiles(row, col, row + 1, col);
        } else if (left != null && left.getId() == blankId) {
            board.swapTiles(row, col, row, col - 1);
        } else if (right != null && right.getId() == blankId) {
            board.swapTiles(row, col, row, col + 1);
        }

    }

}
