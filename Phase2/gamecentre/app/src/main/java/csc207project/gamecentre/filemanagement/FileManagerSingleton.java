package csc207project.gamecentre.filemanagement;


import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class FileManagerSingleton  implements Serializable {
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

    public void writeToFile(String fileName, HashMap<String,String> userInputStrMap){
        try{
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(userInputStrMap);
            oos.flush();
            oos.close();
            fos.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public HashMap<String,String> loadFromFile(String fileName){
        try{
            File toRead = new File(fileName);
            FileInputStream fis = new FileInputStream(toRead);
            ObjectInputStream ois = new ObjectInputStream(fis);

            @SuppressWarnings("unchecked")
            HashMap<String,String> mapInFile = (HashMap<String,String>)ois.readObject();

            ois.close();
            fis.close();
            //print All data in MAP

	        for(Map.Entry<String,String> m :mapInFile.entrySet()){
	            System.out.println(m.getKey()+" : "+m.getValue());
	        }

            return mapInFile;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;

    }
//    /**
//     * Load the board manager from fileName.
//     *
//     * @param fileName the name of the file
//     */
//    private void loadFromFile(String fileName) {
//
//        try {
//            InputStream inputStream = this.openFileInput(fileName);
//            if (inputStream != null) {
//                ObjectInputStream input = new ObjectInputStream(inputStream);
//                boardManager = (BoardManager) input.readObject();
//                inputStream.close();
//            }
//        } catch (FileNotFoundException e) {
//            Log.e("login activity", "File not found: " + e.toString());
//        } catch (IOException e) {
//            Log.e("login activity", "Can not read file: " + e.toString());
//        } catch (ClassNotFoundException e) {
//            Log.e("login activity", "File contained unexpected data type: " + e.toString());
//        }
//    }
//
//    /**
//     * Save the board manager to fileName.
//     *
//     * @param fileName the name of the file
//     */
//    public void saveToFile(String fileName) {
//        try {
//            ObjectOutputStream outputStream = new ObjectOutputStream(
//                    this.openFileOutput(fileName, MODE_PRIVATE));
//            outputStream.writeObject(boardManager);
//            outputStream.close();
//        } catch (IOException e) {
//            Log.e("Exception", "File write failed: " + e.toString());
//        }
//    }


}
