package csc207project.gamecentre.MemoryMatchingPics;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class BoardUnitTest {

    @Test
    public void flipCardTest() {
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

        b.flipCard(0,1);
        b.flipCard(0,1);
        int matched2 = b.getMatched();
        assertEquals(2,matched2);

        b.flipCard(0,1);
        b.flipCard(0,2);
        int matched3 = b.getMatched();
        assertEquals(2,matched3);
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
    public void getMatchedTest() {

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

        b.flipCard(0,1);
        b.flipCard(0,1);
        int matched2 = b.getMatched();
        assertEquals(2,matched2);

        b.flipCard(0,1);
        b.flipCard(0,2);
        int matched3 = b.getMatched();
        assertEquals(2,matched3);

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
}