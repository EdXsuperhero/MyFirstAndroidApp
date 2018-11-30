package csc207project.gamecentre.TwentyFourGame;
/*
Adapted from:
https://blog.csdn.net/yhhazr/article/details/7947962
 */
import java.util.ArrayList;
import java.util.Stack;
/**
 *  A changString class that transform valid input string to an expression that is computable,
 *  give the result if that is the not valid input.
 */
public class ChangeString {
    /**
     * Transform input string to an ArrayList of String.
     * @param str input string
     * @return an ArrayList
     */
    public ArrayList<String> getStringList(String str){
        ArrayList<String> result = new ArrayList<String>();
        String num = "";
        for (int i = 0; i < str.length(); i++) {
            if(Character.isDigit(str.charAt(i))){
                num = num + str.charAt(i);
            }else{
                if(num != ""){
                    result.add(num);
                }
                result.add(str.charAt(i) + "");
                num = "";
            }
        }
        if(num != ""){
            result.add(num);
        }
        return result;
    }

    /**
     * get the post oder of input arrayList.
     * @param inOrderList input arrayList
     * @return an ArrayList of String
     */
    public ArrayList<String> getPostOrder(ArrayList<String> inOrderList){

        ArrayList<String> result = new ArrayList<String>();
        Stack<String> stack = new Stack<String>();
        for (int i = 0; i < inOrderList.size(); i++) {
            if(Character.isDigit(inOrderList.get(i).charAt(0))){
                result.add(inOrderList.get(i));
            }else{
                switch (inOrderList.get(i).charAt(0)) {
                    case '(':
                        stack.push(inOrderList.get(i));
                        break;
                    case ')':
                        while (!stack.peek().equals("(")) {
                            result.add(stack.pop());
                        }
                        stack.pop();
                        break;
                    default:
                        while (!stack.isEmpty() && compare(stack.peek(), inOrderList.get(i))){
                            result.add(stack.pop());
                        }
                        stack.push(inOrderList.get(i));
                        break;
                }
            }
        }
        while(!stack.isEmpty()){
            result.add(stack.pop());
        }
        return result;
    }

    /**
     * Calculate the final of integer with the input ArrayList postOrder
     * @param postOrder an input arrayList
     * @return an integer which is the result
     */
    public Integer calculate(ArrayList<String> postOrder){
        Stack stack = new Stack();
        for (int i = 0; i < postOrder.size(); i++) {
            if(Character.isDigit(postOrder.get(i).charAt(0))){
                stack.push(Integer.parseInt(postOrder.get(i)));
            }else{
                Integer back = (Integer)stack.pop();
                Integer front = (Integer)stack.pop();
                Integer res = 0;
                switch (postOrder.get(i).charAt(0)) {
                    case '+':
                        res = front + back;
                        break;
                    case '-':
                        res = front - back;
                        break;
                    case '*':
                        res = front * back;
                        break;
                    case '/':
                        res = front / back;
                        break;
                }
                stack.push(res);
            }
        }
        return (Integer)stack.pop();
    }

    /**
     * Compare the current and peek operator with string peek and string cur.
     * @param peek string peek
     * @param cur string curr
     * @return true if peek and cur are the same, otherwise return false
     */
    public static boolean compare(String peek, String cur){
        if("*".equals(peek) && ("/".equals(cur) || "*".equals(cur) ||"+".equals(cur) ||"-".equals(cur))){
            return true;
        }else if("/".equals(peek) && ("/".equals(cur) || "*".equals(cur) ||"+".equals(cur) ||"-".equals(cur))){
            return true;
        }else if("+".equals(peek) && ("+".equals(cur) || "-".equals(cur))){
            return true;
        }else if("-".equals(peek) && ("+".equals(cur) || "-".equals(cur))){
            return true;
        }
        return false;
    }
}






