package csc207project.gamecentre.MemoryMatching;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import csc207project.gamecentre.R;


public class Board extends Observable implements Serializable, Cloneable {


    /**
     * the width of the board, e.g.: if width is 4, then the size will be 4 x 4.
     */
    private int width;

    /**
     * the tiles on the board in row-major order.
     */
    private Card[][] cards;

    /**
     * the stack of cards.
     */
    private Stack<Card> cardStack = new Stack<>();

    /**
     * the number of pictures in pairs that has matched
     */
    private int matched = 0;

    /**
     * Initialize the board with given width and list of tiles.
     *
     * @param width the size of the board.
     * @param cards the tiles on the board to play with.
     */
    Board(int width, List<Card> cards) {
        this.width = width;
        this.cards = new Card[this.width][this.width];

        Iterator<Card> iter = cards.iterator();

        for (int row = 0; row != this.width; row++) {
            for (int col = 0; col != this.width; col++) {
                this.cards[row][col] = iter.next();
            }
        }
    }

    /**
     * flipping a card in the given position.
     *
     * If the card is the first one that has been flipped over, keep it on the board;
     * Other wise if the card is the second one that has been flipped over, then:
     *
     * 1. if the position of the second card we flip on the board is the same as the position of
     * the first card, which means we flip a card twice, then the card will be flipped back.
     *
     * 2. if the second card that has been flipped over has the same picture with the first one,
     * which means they are matched, then we add 2 to matched.
     *
     * 3. if the second card that has been flipped over has a different picture with the first one,
     * which means they are not matched, then after 0.5 second, both cards will be flipped back.
     *
     * @param row the row of the card on the board;
     * @param col the column of the card on the board;
     */
    void flipCard(int row, int col) {
        Card cardOne = cards[row][col];
        if (cardOne.getFlipable() == true) {
            if (cardStack.isEmpty()) {
                cardOne.setBackground(cardOne.getBackside());
                cardStack.push(cardOne);
                updateObsevers();
            } else {
                Card cardTwo = cardStack.pop();
                if (cardOne.getId() == cardTwo.getId()) {
                    cardOne.setBackground(R.drawable.card_0);
                    updateObsevers();
                } else {
                    if (cardOne.getnum() == cardTwo.getnum()) {
                        cardOne.setBackground(cardTwo.getBackground());
                        cardOne.setFlipable(false);
                        cardTwo.setFlipable(false);
                        this.matched += 2;
                        updateObsevers();
                    } else {
                        cardOne.setBackground(cardOne.getBackside());
                        updateObsevers();
                        Timer t = new Timer();
                        t.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                cardOne.setBackground(R.drawable.card_0);
                                cardTwo.setBackground(R.drawable.card_0);
                                updateObsevers();
                            }
                        },500);
                    }
                }
            }
        }
    }


    /**
     * notify the observers.
     */
    private void updateObsevers() {
        setChanged();
        notifyObservers();
    }

    @Override
    protected Board clone() {
        List<Card> cloneCards = new ArrayList<>();
        for (int row = 0; row != this.width; row++) {
            for (int col = 0; col != this.width; col++) {
                cloneCards.add(this.cards[row][col]);
            }
        }
        return new Board(this.width, cloneCards);
    }

    /**
     * get the width
     *
     * @return width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * get the list of cards
     *
     * @return list of cards
     */
    public Card[][] getCards() {
        return this.cards;
    }

    /**
     * return the number of matched in board.
     */
    public int getMatched() {
        return this.matched;
    }

    /**
     * return the number of cards on the board.
     */
    public int getNumCards() {
        return (int) Math.pow(this.width, 2);
    }

    /**
     * return the card stack.
     */
    public Stack<Card> getCardStack(){
        return this.cardStack;
    }
}
