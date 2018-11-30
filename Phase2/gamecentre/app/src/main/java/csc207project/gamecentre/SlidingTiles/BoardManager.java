package csc207project.gamecentre.SlidingTiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import static java.lang.Math.abs;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;

    /**
     * A stack for storing the board.
     */
    private Stack<Board> boardStack = new Stack<>();

    /**
     * The duration of current game.
     */
    private long duration = 0;

    /**
     * Manage a new shuffled board.
     *
     * @param width the width of board to be initialized
     */
    BoardManager(int width) {

        List<Tile> tiles = new ArrayList<>();
        final int numTiles = (int) Math.pow(width, 2);
        for (int tileNum = 1; tileNum < numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        tiles.add(new Tile(0));

        this.board = new Board(width, tiles);
        shuffle(this.board);
        pushToStack();
    }

    /**
     * Shuffle the board.
     * 
     * @param board the board to be shuffle
     */
    private void shuffle(Board board) {
        Random rnd = new Random();
        long shuffleTimes = abs(rnd.nextInt()) / 12345;
        System.out.println(shuffleTimes);
        int blankTileRow = board.getWidth() - 1;
        int blankTileCol = board.getWidth() - 1;
        while (shuffleTimes > 0) {
            int shuffleCondition = rnd.nextInt() % 4;
            switch (shuffleCondition) {
                case SlidingTilesGameActivity.ABOVE:
                    if (blankTileRow - 1 >= 0) {
                        board.swapTiles(blankTileRow, blankTileCol, blankTileRow - 1, blankTileCol);
                        blankTileRow -= 1;
                    }
                    break;
                case SlidingTilesGameActivity.BELOW:
                    if (blankTileRow + 1 < board.getWidth()) {
                        board.swapTiles(blankTileRow, blankTileCol, blankTileRow + 1, blankTileCol);
                        blankTileRow += 1;
                    }
                    break;
                case SlidingTilesGameActivity.LEFT:
                    if (blankTileCol - 1 >= 0) {
                        board.swapTiles(blankTileRow, blankTileCol, blankTileRow, blankTileCol - 1);
                        blankTileCol -= 1;
                    }
                    break;
                case SlidingTilesGameActivity.RIGHT:
                    if (blankTileCol + 1 < board.getWidth()) {
                        board.swapTiles(blankTileRow, blankTileCol, blankTileRow, blankTileCol + 1);
                        blankTileCol += 1;
                    }
                    break;
            }
            shuffleTimes--;
        }

    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {

        boolean solved = true;
        Iterator<Tile> iter = this.board.iterator();
        int position = 1;

        while (iter.hasNext() && solved) {
            Tile tile = iter.next();
            if (!iter.hasNext()) {
                if (tile.getId() != 0) {
                    solved = false;
                }
            } else {
                if (tile.getId() != position) {
                    solved = false;
                }
            }
            position++;
        }

        return solved;
    }

    /**
     * Return the position of the blank tile.
     * The return is based on constants in SlidingTilesGameActivity.java.
     *
     * @param row the row of the tile to check
     * @param col the col of the tile to check
     * @return the position of the blank tile to the given position
     */
    private int findBlankTile(int row, int col) {

        int result = SlidingTilesGameActivity.CANNOTFIND;
        int width = this.board.getWidth();
        int blankId = 0;

        Tile above = row == 0 ? null : this.board.getTile(row - 1, col);
        if (above != null && above.getId() == blankId) {
            result = SlidingTilesGameActivity.ABOVE;
        }

        Tile below = row == width - 1 ? null : board.getTile(row + 1, col);
        if (below != null && below.getId() == blankId) {
            result = SlidingTilesGameActivity.BELOW;
        }

        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        if (left != null && left.getId() == blankId) {
            result = SlidingTilesGameActivity.LEFT;
        }

        Tile right = col == width - 1 ? null : board.getTile(row, col + 1);
        if (right != null && right.getId() == blankId) {
            result = SlidingTilesGameActivity.RIGHT;
        }

        return result;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {

        int width = this.board.getWidth();
        int row = position / width;
        int col = position % width;

        return findBlankTile(row, col) != SlidingTilesGameActivity.CANNOTFIND;
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {

        int width = this.board.getWidth();
        int row = position / width;
        int col = position % width;
        int positionBlankTiles = findBlankTile(row, col);

        switch (positionBlankTiles) {
            case SlidingTilesGameActivity.ABOVE:
                this.board.swapTiles(row, col, row - 1, col);
                break;
            case SlidingTilesGameActivity.BELOW:
                this.board.swapTiles(row, col, row + 1, col);
                break;
            case SlidingTilesGameActivity.LEFT:
                this.board.swapTiles(row, col, row, col - 1);
                break;
            case SlidingTilesGameActivity.RIGHT:
                this.board.swapTiles(row, col, row, col + 1);
                break;
        }
    }

    /**
     * Push current board to stack.
     */
    void pushToStack() {
        Board cloneBoard = this.board.clone();
        this.boardStack.push(cloneBoard);
    }

    /**
     * Pop last board from stack to this.board.
     *
     * @return whether the pop is successful
     */
    boolean popFromStack() {
        boolean result = false;
        if (this.boardStack.size() > 1) {
            this.boardStack.pop();
            this.board = this.boardStack.pop();
            result = true;
        }
        return result;
    }

    /**
     * Update the duration of this game.
     *
     * @param duration the duration of time spent on this game
     */
    void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * @return the duration of this game
     */
    long getDuration() {
        return this.duration;
    }

    /**
     * @return the current board
     */
    Board getBoard() {
        return this.board;
    }
}
