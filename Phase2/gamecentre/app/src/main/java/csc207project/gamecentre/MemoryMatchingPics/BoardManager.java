package csc207project.gamecentre.MemoryMatchingPics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import csc207project.gamecentre.R;

/**
 * manage a board, including flip the tile, check for match, check for win, and managing taps.
 */
public class BoardManager implements Serializable {

    /**
     * the board being managed.
     */
    private Board board;

    /**
     * The duration of current game.
     */
    private long duration = 0;

    /**
     * the stack of board for storing the boards.
     */
    private Stack<Board> boardStack= new Stack<>();


    /**
     * initialize the boardManager
     * @param width the width for the board.
     */
    BoardManager(int width){

        List<Card> cards = new ArrayList<>();
        final int numTiles = (int) Math.pow(width,2);
        for (int tileNum = 0; tileNum < numTiles/2; tileNum++){
            cards.add(new Card(tileNum, tileNum));
        }
        for (int tileNum = 0; tileNum < numTiles/2; tileNum++){
            cards.add(new Card(tileNum + 8, tileNum));
        }
        Collections.shuffle(cards);
        this.board = new Board(width, cards);
        pushToStack();
    }


    /**
     * detecting the matching.
     * @return whether all cards on the boards are matched.
     */
    boolean cardAllMatched(){
        return (this.board.getMatched() == this.board.getNumCards());
    }

    /**
     * return whether the user is able to flip the card.
     * @param position the tile to check
     * @return whether the card has been matched or not.
     */
    boolean isValidTap(int position){
        int width = this.board.getWidth();
        int row = position / width;
        int col = position % width;

        Card c = this.board.getCards()[row][col];
        if (c.getBackground() == R.drawable.tile_24){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Process a touch at position in the board, flip over the card.
     * @param position the position for card
     */
    void touchMove(int position){
        int width = this.board.getWidth();
        int row = position / width;
        int col = position % width;

        if (isValidTap(position)) {
            this.board.flipCard(row, col);
        }
    }


    /**
     * push current board to stack.
     */
    public void pushToStack() {
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
