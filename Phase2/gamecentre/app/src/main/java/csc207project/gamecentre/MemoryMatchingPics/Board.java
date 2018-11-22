package csc207project.gamecentre.MemoryMatchingPics;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import csc207project.gamecentre.R;


public class Board extends Observable implements Serializable, Cloneable{



    /**
     * the width of the board, e.g.: if width is 4, then the size will be 4 x 4.
     */
    private int width;

    /**
     * the tiles on the board in row-major order.
     */
    private Card[][] cards;

//    /**
//     * the first card to flip.
//     */
//    private Card cardOne;
//
//    /**
//     * the second card to flip.
//     */
//    private Card cardTwo;

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
     * @param width the size of the board.
     * @param cards the tiles on the board to play with.
     */
    Board(int width, List<Card> cards){
        this.width = width;
        this.cards = new Card[this.width][this.width];

        Iterator<Card> iter= cards.iterator();

        for (int row = 0; row != this.width; row++){
            for (int col = 0; col != this.width; col++){
                this.cards[row][col] = iter.next();
            }
        }
    }

    void flipCard(int row, int col) {
        Card cardOne = cards[row][col];
        if (cardStack.size() == 2){
            cardStack.pop().setBackground(R.drawable.tile_0);
            cardStack.pop().setBackground(R.drawable.tile_0);
            updateObsevers();
        }

        else if (cardOne.getFlipable() == true) {
                if (cardStack.isEmpty()) {
                    cardOne.setBackground(cardOne.getBackside());
                    cardStack.push(cardOne);
                    updateObsevers();

                } else {
                    Card cardTwo = cardStack.pop();
                    if(cardOne.getId() == cardTwo.getId()) {
                        cardOne.setBackground(R.drawable.tile_0);
                        updateObsevers();
                    } else {
                        if (cardOne.getnum() == cardTwo.getnum()) {
                            cardOne.setBackground(cardTwo.getBackground());
                            cardOne.setFlipable(false);
                            cardTwo.setFlipable(false);
                            this.matched += 2;
                            updateObsevers();
                        }
                        else {
                            cardOne.setBackground(cardOne.getBackside());
                            updateObsevers();
                            cardStack.push(cardOne);
                            cardStack.push(cardTwo);

                            // if you want to use timer
                            //TODO:Something magical happens(timer), execute the following two lines after 1.5 seconds
                            //cardOne.setBackground(R.drawable.tile_0);
                            //cardTwo.setBackground(R.drawable.tile_0);

                        }
                    }//else {
                    //cardOne.setBackground(cardOne.getBackside());
                    //updateObsevers();
                    //Timer timer = new Timer();
                    //if (cardOne.getId() == cardTwo.getId()) {

                    //this.matched++;
                    //cardOne.setBackground(R.drawable.tile_3);
                    //cardTwo.setBackground(R.drawable.tile_3);
                    //updateObsevers();
//                    TimerTask timerTask = new FlipFinish(this);
//                    timer.schedule(timerTask, 1500);//delay 1.5 seconds.
                    //} else {
                    //cardOne.setBackground(R.drawable.tile_0);
                    //cardTwo.setBackground(R.drawable.tile_0);
                    //updateObsevers();
//                    TimerTask timerTask = new FlipBack(this);
//                    timer.schedule(timerTask, 1500);//delay 1.5 seconds.
                    //}

                    //}
                }

            }
        }



/**
* flip back the cards.
  */
//void flipCardBack(){
    //cardOne.setBackground(R.drawable.tile_0);
//}
//        cardTwo.setBackground(R.drawable.tile_0);
//    }
//
//    /**
//     * flip the cards to a matching state.
//     */
//    void flipCardFinish(){
//        cardOne.setBackground(R.drawable.tile_24);
//        cardTwo.setBackground(R.drawable.tile_24);
//    }

    /**
     * notify the observers.
     */
    private void updateObsevers(){
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
     * @return width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * get the list of cards
     * @return list of cards
     */
    public Card[][] getCards() {
        return this.cards;
    }

//    /**
//     * get the first card we touch
//     * @return cardOne
//     */
//    public Card getCardOne() {
//        return this.cardOne;
//    }
//
//    /**
//     * get the second card we touch
//     * @return cardTwo
//     */
//    public Card getCardTwo() {
//        return this.cardTwo;
//    }

    /**
     * get the stack of cards
     * @return cardStack
     */
    public Stack<Card> getCardStack() {
        return this.cardStack;
    }

    /**
     * return the number of matched in board.
     */
    public int getMatched(){
        return this.matched;
    }

    /**
     * return the number of cards on the board.
     */
    public int getNumCards(){
        return (int) Math.pow(this.width, 2);
    }

//    /**
//     * return whether the given position is matched.
//     * @param position on the board.
//     * @return
//     */
//   TODO:Complete match method.
//    public boolean match(int row, int col) {
//        if (this.cards[row][col])
//
//    }
}
//
//
///**
// * A TimerTask to flip back the card to white state.
// */
//class FlipBack extends TimerTask {

    //private Board b;

  //public FlipBack(Board b){
      // this.b = b;
   //}

  //@Override
    //public void run() {
       //this.b.flipCardBack();
   //}
//}
//
///**
// * A TimerTask to flip back the card to white state.
// */
//class FlipFinish extends TimerTask {
//
//    private Board b;
//
//    public FlipFinish(Board b){
//        this.b = b;
//    }
//
//    @Override
//    public void run() {
//        this.b.flipCardFinish();
//    }
//}