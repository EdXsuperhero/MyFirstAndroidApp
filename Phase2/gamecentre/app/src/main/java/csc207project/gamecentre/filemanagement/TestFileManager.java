/*package csc207project.gamecentre.filemanagement;

import java.util.HashMap;
import java.util.Map;

public class TestFileManager {
    public final static String GAME24POINT_FILE_NAME = "game24point.ser";
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        //get an single instance of file manager
        FileManagerSingleton fm = FileManagerSingleton.getInstance();
        System.out.println("------------before -------------");
        HashMap<String,String> mapfromFile = fm.loadFromFile(GAME24POINT_FILE_NAME);
        for(Map.Entry<String,String> m :mapfromFile.entrySet()){
            System.out.println(m.getKey()+" : "+m.getValue());
        }


        HashMap<String,String> hm = new  HashMap<String,String>();
        hm.put("eleanor","2*3/2 + 3 - 4");
        hm.put("mike","2-3 *(6/3) + 3 - 9");

        fm.writeToFile(GAME24POINT_FILE_NAME, hm);
        System.out.println("------------after -------------");
        HashMap<String,String> mapInFile = fm.loadFromFile(GAME24POINT_FILE_NAME);
        for(Map.Entry<String,String> m :mapInFile.entrySet()){
            System.out.println(m.getKey()+" : "+m.getValue());
        }

    }

}
*/
//package csc207project.gamecentre.filemanagement;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class TestFileManager {
//    public final static String GAME24POINT_FILE_NAME = "game24point.ser";
//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//
//        //get an single instance of file manager
//        FileManagerSingleton fm = FileManagerSingleton.getInstance();
//        System.out.println("------------before -------------");
//        HashMap<String,String> mapfromFile = fm.loadFromFile(GAME24POINT_FILE_NAME);
//        for(Map.Entry<String,String> m :mapfromFile.entrySet()){
//            System.out.println(m.getKey()+" : "+m.getValue());
//        }
//
//
//        HashMap<String,String> hm = new  HashMap<String,String>();
//        hm.put("eleanor","2*3/2 + 3 - 4");
//        hm.put("mike","2-3 *(6/3) + 3 - 9");
//
//        fm.writeToFile(GAME24POINT_FILE_NAME, hm);
//        System.out.println("------------after -------------");
//        HashMap<String,String> mapInFile = fm.loadFromFile(GAME24POINT_FILE_NAME);
//        for(Map.Entry<String,String> m :mapInFile.entrySet()){
//            System.out.println(m.getKey()+" : "+m.getValue());
//        }
//
//    }
//
//}
