package csc207project.gamecentre.TwentyFourGame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import csc207project.gamecentre.OASIS.ScoreManager;
import csc207project.gamecentre.R;

/**
 * The idea of chronometer is cited from https://codinginflow.com/tutorials/android/chronometer
 * Disabling the phone keyboard when the game st arts citation:
 * https://stackoverflow.com/questions/46024100/how-to-completely-disable-keyboard-when-using-edittext-in-android
 */

public class game24Activity extends AppCompatActivity implements Serializable{

    /**
     * create imageView1,imageView2,imageView3,imageView4
     */
    ImageView imageView1 = null;
    ImageView imageView2 = null;
    ImageView imageView3 = null;
    ImageView imageView4 = null;


    /**
     * create imageView btnLeft,btnRight,btnPlus,btnMinus,btnMultiply,btnDivide
     */
    ImageView btnLeft;
    ImageView btnRight;
    ImageView btnPlus;
    ImageView btnMinus;
    ImageView btnMultiply;
    ImageView btnDivide;

    /**
     * create   EditText editText;Button btnConfirm;Button undo;Button startButton;
     */
    EditText editText;
    Button btnConfirm;
    Button undo;
    Button startButton;

    String inputString = "";

    int track,a1,a2,a3,a4;

    public int[] generateNumber(){
        int[] numberList = new int[4];
        Random random = new Random();
        for(int i = 0; i < 4; i++)
            numberList[i] = random.nextInt(9)+1;
        return numberList;
    }

    int[] getSolvableDigits(){
        checkSolvable checkSolvable = new checkSolvable();
        int[] resultList;
        boolean result;
        do{
            resultList = generateNumber();
            result = checkSolvable.judgePoint24(resultList);
        }while(!result);
        return resultList;
    }

    int[] validList = getSolvableDigits();

    /**
     * Creating a file that stores the user's score.
     */
    public static final String USER_SCORE = "user_score.ser";
    /**
     * The ScoreManager.
     */
    private ScoreManager sm;
    /**
     * To check if the game is win or lose.
     */
    private boolean win = false;

    /**
     * The HashMap which the key and value are Strings.
     */
    HashMap<String, String> hm = new HashMap<String, String>();
    /**
     *
     * A Chronometer to record how many time is taken for the game.
     */
    private Chronometer chronometer;

    /**
     * The duration of the timer
     */
    private long pauseOffset;

    /**
     * The attribute to check if is running or not
     */
    private boolean running;

    /**
     * The file GAME24POINTS_FILE_NAME that store the strings.
     */
    public final static String GAME24POINTS_FILE_NAME = "game24.ser";
    /**
     * The file TIMER_OFFSET that store the time as string.
     */
    public final static String TIMER_OFFSET = "chronometer.ser";
    /**
     * The file IMAGE_NUMBER that stores the array validList.
     */
    public final static String IMAGE_NUMBER = "image_number.ser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game24);

        editText = findViewById(R.id.inputText);

        editText.setEnabled(false);
        editText.setInputType(0);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setEnabled(false);

        Button btnLoad = findViewById(R.id.btnLoad);

        Button btnResult = findViewById(R.id.btnResult);
        btnResult.setEnabled(false);
        btnResult.setFocusable(false);

        editText.setShowSoftInputOnFocus(false);
        editText.setInputType(InputType.TYPE_NULL);
        editText.setFocusable(false);

        this.chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        addUndoButtonListener();
        addStartButtonListener();
        setImageView1Listener();
        imageView1.setClickable(false);
        setImageView2Listener();
        imageView2.setClickable(false);
        setImageView3Listener();
        imageView3.setClickable(false);
        setImageView4Listener();
        imageView4.setClickable(false);

        addLeftBracketListener();
        btnLeft.setClickable(false);
        addRightBracketListener();
        btnRight.setClickable(false);
        addPlusButtontListener();
        btnPlus.setClickable(false);
        addMinusButtonListener();
        btnMinus.setClickable(false);
        addMutiplyButtonListener();
        btnMultiply.setClickable(false);
        addDivideButtonListener();
        btnDivide.setClickable(false);


        SharedPreferences settings = getSharedPreferences(USER_SCORE, 0);
        SharedPreferences.Editor editor = settings.edit();
        String user = "user";

        editor.putString("userName",user) ;
        SharedPreferences settings1 = getSharedPreferences("score", 0);
        SharedPreferences.Editor editor1 = settings1.edit();
        editor1.putLong("score", Long.valueOf(pauseOffset));
        editor.commit();

        /**
         * Active the load button.
         */

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoad.setClickable(false);
                btnConfirm.setEnabled(true);

                startButton.setClickable(false);

                imageView1.setClickable(true);
                imageView2.setClickable(true);
                imageView3.setClickable(true);
                imageView4.setClickable(true);

                loadImageFromFile(IMAGE_NUMBER);
                //set random picture to 4 imageViews
                setImage(imageView1, validList[0]);
                setImage(imageView2, validList[1]);
                setImage(imageView3, validList[2]);
                setImage(imageView4, validList[3]);

                setImageView1Listener();
                setImageView2Listener();
                setImageView3Listener();
                setImageView4Listener();
                

                btnLeft.setClickable(true);
                btnRight.setClickable(true);
                btnPlus.setClickable(true);
                btnMinus.setClickable(true);
                btnMultiply.setClickable(true);
                btnDivide.setClickable(true);

                editText.setEnabled(true);
                editText.setFocusable(true);

                loadFromFile(GAME24POINTS_FILE_NAME);
                if (hm != null){
                    inputString = hm.get("user1");
                    editText.setText(inputString);
                } else {
                    editText.setText(inputString);
                    editText.setEnabled(true);
                    editText.setFocusable(true);
                }

                HashMap<String, String> chm = loadTimeFromFile(TIMER_OFFSET);
                if (chm!= null){
                    System.out.println(chm);
                    if (chm.get("userName") != null)
                             {
                        pauseOffset = Long.parseLong(chm.get("userName"));
                    }
                }
                startChronometer();

            }
        });

        /**
         * To save and load strings from editText
         */
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadFromFile(GAME24POINTS_FILE_NAME);
                if (hm== null){
                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("user1", inputString);
                    saveToFile(GAME24POINTS_FILE_NAME);
                }else {
                    hm.put("user1", inputString);
                    saveToFile(GAME24POINTS_FILE_NAME);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /**
         * Active confirm button.
         */
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoad.setEnabled(false);
                // disable chronometer
                pauseChronometer();

                undo.setEnabled(false);

                setOperatorClickable(false);

                String finalResult = getFinalResult(inputString);
                editText.setText(finalResult);
                if (win){
                    btnResult.setEnabled(true);
                    String msg = "You Win :)";
                    Toast.makeText(game24Activity.this,msg, Toast.LENGTH_LONG).show();
                }else{
                    String msg = "You Lose :(";
                    Toast.makeText(game24Activity.this,msg, Toast.LENGTH_LONG).show();
                }
            }

            /**
             * A method that pause the chronometer.
             */
            private void pauseChronometer() {
                if (running) {
                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                }
            }
        });
        /**
         * Active the see result button.
         */
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScore();
            }
        });
    }


    private void addStartButtonListener(){
        startButton = findViewById(R.id.startBtn);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //enable chronometer
                startChronometer();

                //enable confirm
                btnConfirm.setEnabled(true);
                editText.setHint("GoFor24");
                editText.setEnabled(true);
                editText.setFocusable(true);
                editText.setText("");

                editText.setEnabled(true);
                editText.setFocusable(true);

                startButton.setClickable(false);



                imageView1.setClickable(true);
                imageView2.setClickable(true);
                imageView3.setClickable(true);
                imageView4.setClickable(true);
                saveImageToFile(IMAGE_NUMBER);

                setImage(imageView1, validList[0]);
                setImage(imageView2, validList[1]);
                setImage(imageView3, validList[2]);
                setImage(imageView4, validList[3]);
                System.out.println(validList);

                setOperatorClickable(true);
                }
        });
    }

     void numberImageViewListener(ImageView numImaView, int a){
        numImaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(track == 0) {
                    track = a;
                    numImaView.setClickable(false);
                    inputString += a;
                    editText.setText(inputString);
                }else{
                    String msg = "can not put two number together!";
                    Toast.makeText(game24Activity.this,msg, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void setImageView1Listener(){
        imageView1 = findViewById(R.id.imageView1);
        numberImageViewListener(imageView1,validList[0]);
    }

    private void setImageView2Listener(){
        imageView2 = findViewById(R.id.imageView2);
        numberImageViewListener(imageView2,validList[1]);
    }

    private void setImageView3Listener(){
        imageView3 = findViewById(R.id.imageView3);
        numberImageViewListener(imageView3,validList[2]);
    }

    private void setImageView4Listener(){
        imageView4 = findViewById(R.id.imageView4);
        numberImageViewListener(imageView4,validList[3]);
    }

    void operatorImageListener(ImageView opeImaView,String operator){
        opeImaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                track = 0;
                inputString += operator;
                editText.setText(inputString);
            }
        });
    }

    private void addLeftBracketListener(){
        btnLeft = findViewById(R.id.btnLeft);
        operatorImageListener(btnLeft,"(");
    }

    private void addRightBracketListener(){
        btnRight = findViewById(R.id.btnRight);
        operatorImageListener(btnRight,")");
    }


    private void addPlusButtontListener(){
        btnPlus = findViewById(R.id.btnPlus);
        operatorImageListener(btnPlus,"+");
    }

    private void addMinusButtonListener(){
        btnMinus = findViewById(R.id.btnMinus);
        operatorImageListener(btnMinus,"-");
    }

    private void addMutiplyButtonListener(){
        btnMultiply = findViewById(R.id.btnMultiply);
        operatorImageListener(btnMultiply,"*");
    }

    private void addDivideButtonListener(){
        btnDivide = findViewById(R.id.btnDivide);
        operatorImageListener(btnDivide,"/");
    }

    private  void addUndoButtonListener(){
        undo = findViewById(R.id.undoBtn);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputString.length() > 0) {
                    String lastStr = inputString.substring(inputString.length() - 1);
                    int indicator = checkIfNumber(lastStr);
                    if (indicator > 0) {
                        track = 0;
                        if (indicator == validList[0]) {
                            imageView1.setClickable(true);
                        }
                        if (indicator == validList[1]) {
                            imageView2.setClickable(true);
                        }
                        if (indicator == validList[2]) {
                            imageView3.setClickable(true);
                        } else {
                            imageView4.setClickable(true);
                        }
                    }
                    inputString = inputString.substring(0, inputString.length() - 1);
                    editText.setText(inputString);
                }else{
                    editText.setText("No Step to Undo");
                }
            }
        });
    }

    /**
     * To store the states of the game when the activity is switched.
     */
    @Override
    protected void onPause(){
        super.onPause();
        pauseChronometer();
        HashMap<String, String> chm = new HashMap<>();
        chm.put("userName", String.valueOf(pauseOffset));
        saveTimeToFile(TIMER_OFFSET, chm);
        saveImageToFile(IMAGE_NUMBER);

    }

    /**
     * A method that pause the chronometer.
     */
    private void pauseChronometer() {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    private void setOperatorClickable(boolean bol){
        btnLeft.setClickable(bol);
        btnRight.setClickable(bol);
        btnPlus.setClickable(bol);
        btnMinus.setClickable(bol);
        btnMultiply.setClickable(bol);
        btnDivide.setClickable(bol);
    }

    public int checkIfNumber(String lastC) {
        int lastInt = 0;
        try {
            lastInt = Integer.valueOf(lastC).intValue();
        } catch (Exception e) {
            System.out.println("It is not integer");
        }
        return lastInt;
    }

    public String getFinalResult(String str){
        int re = judgeTransferable(str);
        if(re == 0){
            return "Ooops! Invalid Input!";
        }else{
            String result = String.valueOf(re);
            return result;
        }
    }

    public int judgeTransferable(String s) {
        int i = 0;
        try {
            ChangeString changeString = new ChangeString();
            ArrayList result = changeString.getStringList(s);
            result = changeString.getPostOrder(result);
            i = changeString.calculate(result);
            if (i == 24){
                win = true;
            }
        } catch (Exception e) {
            System.out.println("invalid message");
        }
        return i;
    }

    private void setImage(ImageView imageView, int num) {
        switch (num) {
            case 1:
                imageView.setImageResource(R.drawable.one);
                break;
            case 2:
                imageView.setImageResource(R.drawable.two);
                break;
            case 3:
                imageView.setImageResource(R.drawable.three);
                break;
            case 4:
                imageView.setImageResource(R.drawable.four);
                break;
            case 5:
                imageView.setImageResource(R.drawable.five);
                break;
            case 6:
                imageView.setImageResource(R.drawable.six);
                break;
            case 7:
                imageView.setImageResource(R.drawable.seven);
                break;
            case 8:
                imageView.setImageResource(R.drawable.eight);
                break;
            case 9:
                imageView.setImageResource(R.drawable.nine);
                break;
            default:
                imageView.setImageResource(R.drawable.initial);
                break;
        }
    }

    /**
     * A method that enables the chronometer.
     */
    private void startChronometer() {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    /**
     * Load the string from fileName to ediText.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                hm = (HashMap<String, String>) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("game24 activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("game24 activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("game24 activity", "File contained unexpected data type: " + e.toString());
        }
    }
    /**
     * Load the array validList from fileName imageView.
     *
     * @param fileName the name of the file
     */
    private void loadImageFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
//                oldvalidList = (int[]) input.readObject();
                validList = (int[]) input.readObject();
                input.close();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("game24 activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("game24 activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("game24 activity", "File contained unexpected data type: " + e.toString());
        }
    }
    /**
     * Load the time taken from the fileName to the HashMap.
     *
     * @param fileName the name of the file,
     */
    private HashMap<String, String> loadTimeFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                HashMap<String, String> chm = (HashMap<String, String>) input.readObject();
                inputStream.close();
                return chm;
            }
        } catch (FileNotFoundException e) {
            Log.e("game24 activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("game24 activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("game24 activity", "File contained unexpected data type: " + e.toString());
        }
        return null;
    }

    /**
     * Switch to Score Board when the game is ended.
     */
    private void switchToScore(){
        Intent scoreboard = new Intent(getApplicationContext(), TwentyFourGameScoreBoardActivity.class);
        scoreboard.putExtra("score", pauseOffset);
        scoreboard.putExtra("current_user", getIntent().getStringExtra("current_user"));
        startActivity(scoreboard);
        finish();
    }

    /**
     * Save the time taken to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(hm);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Save the array validList to the file.
     *
     * @param fileName the name of the file
     */
    public void saveImageToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(validList);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Save the time taken from HashMap to fileName.
     *
     * @param fileName the name of the file,
     * @param chm the name of the HashMap.
     */
    public void saveTimeToFile(String fileName, HashMap<String,String> chm) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(chm);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Save the score manager to fileName.
     *
     * @param fileName the name of the file
     */
    private void saveScoreToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(this.sm);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}


