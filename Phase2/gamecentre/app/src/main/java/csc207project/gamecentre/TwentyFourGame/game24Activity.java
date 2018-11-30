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


    ImageView imageView1 = null;
    ImageView imageView2 = null;
    ImageView imageView3 = null;
    ImageView imageView4 = null;

    ImageView btnLeft;
    ImageView btnRight;
    ImageView btnPlus;
    ImageView btnMinus;
    ImageView btnMultiply;
    ImageView btnDivide;

    EditText editText;
    Button btnConfirm;
    Button undo;
    Button StartButton;

    String inputString = "";

    int track,a1,a2,a3,a4;

    public static final String USER_SCORE = "user_score";

    private ScoreManager sm;

    public final static String SAVE_SCORE = "save_score.ser";

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
    void getValidNumber(){
        a1 = validList[0];
        a2 = validList[1];
        a3 = validList[2];
        a4 = validList[3];
    }
    private boolean win = false;

    HashMap<String, String> hm = new HashMap<String, String>();
    /**
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
    public final static String IMAGE = "image";


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

        getValidNumber();

        addUndoButtonListener();
        addStartButtonListener();
        setImageView1Listener();
        setImageView2Listener();
        setImageView3Listener();
        setImageView4Listener();

        addLeftBracketListener();
        addRightBracketListener();
        addPlusButtontListener();
        addMinusButtonListener();
        addMutiplyButtonListener();
        addDivideButtonListener();


        SharedPreferences settings = getSharedPreferences(USER_SCORE, 0);
        SharedPreferences.Editor editor = settings.edit();
        String user = "user";

        editor.putString("userName",user) ;
        SharedPreferences settings1 = getSharedPreferences("score", 0);
        SharedPreferences.Editor editor1 = settings1.edit();
        editor1.putLong("score", Long.valueOf(pauseOffset));
        editor.commit();

        /**
         * Activity the load button.
         */

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoad.setClickable(false);
                btnConfirm.setEnabled(true);

                //Set StartButton unclickable
                StartButton.setClickable(false);

                //Make 4 imageViews clickable after click start button
                imageView1.setClickable(true);
                imageView2.setClickable(true);
                imageView3.setClickable(true);
                imageView4.setClickable(true);



                //set random picture to 4 imageViews
                setImage(imageView1, a1);
                setImage(imageView2, a2);
                setImage(imageView3, a3);
                setImage(imageView4, a4);

                btnLeft.setClickable(true);
                btnRight.setClickable(true);
                btnPlus.setClickable(true);
                btnMinus.setClickable(true);
                btnMultiply.setClickable(true);
                btnDivide.setClickable(true);

                //enable editText after LoadButton is clicked
                editText.setEnabled(true);
                editText.setFocusable(true);

                loadFromFile(GAME24POINTS_FILE_NAME);
                if (hm != null){
                    editText.setText(hm.get("user1"));
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
//                System.out.println("onchange..");
                loadFromFile(GAME24POINTS_FILE_NAME);
                if (hm== null){
//                    System.out.println("mapfromFile = null");
                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("user1", editText.getText().toString());
                    saveToFile(GAME24POINTS_FILE_NAME);
                }else {
//                    System.out.println("mapfromFile = not null");
                    hm.put("user1", editText.getText().toString());
                    saveToFile(GAME24POINTS_FILE_NAME);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScore();
            }
        });
    }


    private void addStartButtonListener(){
        StartButton = findViewById(R.id.startBtn);
        StartButton.setOnClickListener(new View.OnClickListener() {
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


                //enable editText after StartButton is clicked
                editText.setEnabled(true);
                editText.setFocusable(true);

                //Set StartButton unclickable
                StartButton.setClickable(false);

                //Make 4 imageViews clickable after click start button
                imageView1.setClickable(true);
                imageView2.setClickable(true);
                imageView3.setClickable(true);
                imageView4.setClickable(true);


                //set random picture to 4 imageViews
                setImage(imageView1, a1);
                setImage(imageView2, a2);
                setImage(imageView3, a3);
                setImage(imageView4, a4);

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
                }
            }
        });
    }


    void setImageView1Listener(){
        imageView1 = findViewById(R.id.imageView1);
        numberImageViewListener(imageView1,a1);
    }

    void setImageView2Listener(){
        imageView2 = findViewById(R.id.imageView2);
        numberImageViewListener(imageView2,a2);
    }

    void setImageView3Listener(){
        imageView3 = findViewById(R.id.imageView3);
        numberImageViewListener(imageView3,a3);
    }

    void setImageView4Listener(){
        imageView4 = findViewById(R.id.imageView4);
        numberImageViewListener(imageView4,a4);
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
                        if (indicator == a1) {
                            imageView1.setClickable(true);
                        }
                        if (indicator == a2) {
                            imageView2.setClickable(true);
                        }
                        if (indicator == a3) {
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

    @Override
    protected void onPause(){
        super.onPause();
        pauseChronometer();
        HashMap<String, String> chm = new HashMap<>();
        chm.put("userName", String.valueOf(pauseOffset));
        saveTimeToFile(TIMER_OFFSET, chm);
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
//        if (win == false){
//            pauseOffset = null;
//        }
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


