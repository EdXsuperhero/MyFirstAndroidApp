package csc207project.gamecentre.filemanagement;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class FileManagerSingleton {
//    private static FileManagerSingleton instance;
    public static FileManagerSingleton instance;

//    private FileManagerSingleton(){}
    public  FileManagerSingleton(){}
    public static FileManagerSingleton getInstance(){
        if(instance == null){
            instance = new FileManagerSingleton();
        }
        return instance;
    }

//    public void writeToFile(String fileName, HashMap<String,String> userInputStrMap){
//        try{
//            File file = new File(fileName);
//            FileOutputStream fos = new FileOutputStream(file);
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//
//            oos.writeObject(userInputStrMap);
//            oos.flush();
//            oos.close();
//            fos.close();
//        }catch(Exception e){}
//    }

    public void writeToFile(String fileName, String userInputStrMap){
        try{
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(userInputStrMap);
            oos.flush();
            oos.close();
            fos.close();
        }catch(Exception e){}
    }
    public String loadFromFile(String fileName){
        try{
            File toRead = new File(fileName);
            FileInputStream fis = new FileInputStream(toRead);
            ObjectInputStream ois = new ObjectInputStream(fis);
//
//            @SuppressWarnings("unchecked")
//            String mapInFile = (HashMap<String,String>)ois.readObject();
//            @SuppressWarnings("Unchecked")


            ois.close();
            fis.close();
            //print All data in MAP
	        /*
	        for(Map.Entry<String,String> m :mapInFile.entrySet()){
	            System.out.println(m.getKey()+" : "+m.getValue());
	        }
	        */
//            return mapInFile;
        }catch(Exception e){}
        return null;
//    public HashMap<String,String> loadFromFile(String fileName){
//        try{
//            File toRead = new File(fileName);
//            FileInputStream fis = new FileInputStream(toRead);
//            ObjectInputStream ois = new ObjectInputStream(fis);
//
//            @SuppressWarnings("unchecked")
//            HashMap<String,String> mapInFile = (HashMap<String,String>)ois.readObject();
//
//            ois.close();
//            fis.close();
//            //print All data in MAP
//	        /*
//	        for(Map.Entry<String,String> m :mapInFile.entrySet()){
//	            System.out.println(m.getKey()+" : "+m.getValue());
//	        }
//	        */
//            return mapInFile;
//        }catch(Exception e){}
//        return null;

    }

}
