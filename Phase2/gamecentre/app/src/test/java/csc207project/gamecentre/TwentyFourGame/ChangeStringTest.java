package csc207project.gamecentre.TwentyFourGame;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ChangeStringTest {

    ChangeString changeString = new ChangeString();

    @Test
    public void getStringList() {

        String test1 = "abc";
        ArrayList<String> result1 = changeString.getStringList(test1);
        ArrayList<String> expect1 = new ArrayList<String>();
        expect1.add("a");
        expect1.add("b");
        expect1.add("c");
        assertEquals(expect1,result1);

        String test2 = "";
        ArrayList<String> result2 = changeString.getStringList(test2);
        ArrayList<String> expect2 = new ArrayList<String>();
        assertEquals(expect2,result2);

        String test3 = "1+2-3*";
        ArrayList<String> result3 = changeString.getStringList(test3);
        ArrayList<String> expect3 = new ArrayList<String>();
        expect3.add("1");
        expect3.add("+");
        expect3.add("2");
        expect3.add("-");
        expect3.add("3");
        expect3.add("*");
        assertEquals(expect3,result3);
    }



    @Test
    public void getPostOrder() {

        String test1 = "abc";
        ArrayList<String> array1 = changeString.getStringList(test1);
        ArrayList<String> result1 = changeString.getPostOrder(array1);
        ArrayList<String> expect1 = new ArrayList<String>();
        expect1.add("c");
        expect1.add("b");
        expect1.add("a");
        assertEquals(expect1,result1);

        String test2 = "";
        ArrayList<String> array2 = changeString.getStringList(test2);
        ArrayList<String> result2 = changeString.getPostOrder(array2);
        ArrayList<String> expect2 = new ArrayList<String>();
        assertEquals(expect2,result2);

        String test3 = "1+2-3*";
        ArrayList<String> array3 = changeString.getStringList(test3);
        ArrayList<String> result3 = changeString.getPostOrder(array3);
        ArrayList<String> expect3 = new ArrayList<String>();
        expect3.add("1");
        expect3.add("2");
        expect3.add("+");
        expect3.add("3");
        expect3.add("*");
        expect3.add("-");
        assertEquals(expect3,result3);
    }

    @Test
    public void calculate() {

        String test1 = "1+2+3";
        ArrayList<String> stringList1 = changeString.getStringList(test1);
        ArrayList<String> postOder1 = changeString.getPostOrder(stringList1);
        Integer actual1 = changeString.calculate(postOder1);
        Integer expect1 = 6;
        assertEquals(expect1,actual1);


        String test2 = "6/2";
        ArrayList<String> stringList2 = changeString.getStringList(test2);
        ArrayList<String> postOder2 = changeString.getPostOrder(stringList2);
        Integer actual2 = changeString.calculate(postOder2);
        Integer expect2 = 3;
        assertEquals(expect2,actual2);
    }

    @Test
    public void compare() {

        String peek1 = "3+5";
        String cur1 = "3+5";
        boolean actual1 = changeString.compare(peek1,cur1);
        boolean expect1 = false;
        assertEquals(expect1,actual1);

        String peek2 = "+";
        String cur2 = "+";
        boolean actual2 = changeString.compare(peek2,cur2);
        boolean expect2 = true;
        assertEquals(expect2,actual2);



    }
}