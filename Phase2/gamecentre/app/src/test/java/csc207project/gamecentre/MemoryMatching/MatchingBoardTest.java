package csc207project.gamecentre.MemoryMatching;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import csc207project.gamecentre.R;

import static org.junit.Assert.*;

public class MatchingBoardTest {

    @Test
    public void flipTwoCardMatchedTest() {
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < 8; i++){
            cards.add(new Card(i, i));
        }
        for (int i = 0; i < 8; i++){
            cards.add(new Card(i + 8, i));
        }
        Board b = new Board(4, cards);
        b.flipCard(0,0);
        b.flipCard(2,0);
        int matched1 = b.getMatched();
        assertEquals(2,matched1);

    }

    @Test
    public void flipTwoCardUnmatchedTest() {
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < 8; i++){
            cards.add(new Card(i, i));
        }
        for (int i = 0; i < 8; i++){
            cards.add(new Card(i + 8, i));
        }
        Board b = new Board(4, cards);
        b.flipCard(0,0);
        b.flipCard(1,0);
        int matched = b.getMatched();
        assertEquals(0,matched);

    }

    @Test
    public void flipOneCardOnceTest() {
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < 8; i++){
            cards.add(new Card(i, i));
        }
        for (int i = 0; i < 8; i++){
            cards.add(new Card(i + 8, i));
        }
        Board b = new Board(4, cards);
        b.flipCard(0,0);
        assertEquals(R.drawable.card_1,b.getCards()[0][0].getBackground());
    }

    @Test
    public void flipOneCardTwiceTest() {
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < 8; i++){
            cards.add(new Card(i, i));
        }
        for (int i = 0; i < 8; i++){
            cards.add(new Card(i + 8, i));
        }
        Board b = new Board(4, cards);
        b.flipCard(0,0);
        b.flipCard(0,0);
        assertEquals(R.drawable.card_0,b.getCards()[0][0].getBackground());

    }

    @Test
    public void cloneTest() {
        int res = 0;
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < 8; i++){
            cards.add(new Card(i, i));
        }
        for (int i = 0; i < 8; i++){
            cards.add(new Card(i + 8, i));
        }

        Board b = new Board(4,cards);

        Board clone_b= b.clone();

        for (int row = 0; row != 4; row++){
            for (int col = 0; col != 4; col++){
                if (b.getCards()[row][col] == clone_b.getCards()[row][col]){
                    res++;
                }
            }
        }

        assertEquals(16, res);

    }

    @Test
    public void getWidthTest() {
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < 8; i++){
            cards.add(new Card(i, i));
        }
        for (int i = 0; i < 8; i++){
            cards.add(new Card(i + 8, i));
        }
        Board b = new Board(4, cards);
        assertEquals(4, b.getWidth());

    }

    @Test
    public void getCardsTest() {
        int res = 0;
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < 8; i++){
            cards.add(new Card(i, i));
        }
        for (int i = 0; i < 8; i++){
            cards.add(new Card(i + 8, i));
        }

        Board b = new Board(4,cards);

        Card[][] c = new Card[4][4];
        Iterator<Card> it = cards.iterator();
        for (int row = 0; row != 4; row++){
            for (int col = 0; col != 4; col++){
                if (b.getCards()[row][col] == it.next()){
                    res++;
                }
            }
        }

        assertEquals(16, res);
    }

    @Test
    public void getNumCardsTest() {
        int res = 0;
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < 8; i++){
            cards.add(new Card(i, i));
        }
        for (int i = 0; i < 8; i++){
            cards.add(new Card(i + 8, i));
        }

        Board b = new Board(4,cards);

        assertEquals(16, b.getNumCards());
    }

    @Test
    public void getCardStackTest(){
        int res = 0;
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < 8; i++){
            cards.add(new Card(i, i));
        }
        for (int i = 0; i < 8; i++){
            cards.add(new Card(i + 8, i));
        }

        Board b = new Board(4,cards);

        b.flipCard(0,0);
        assertEquals(R.drawable.card_1,b.getCardStack().pop().getBackground());
    }

}