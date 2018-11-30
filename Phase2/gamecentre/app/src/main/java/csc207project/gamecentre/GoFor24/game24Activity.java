package csc207project.gamecentre.GoFor24;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import csc207project.gamecentre.R;

/**
 * The idea of chronometer is cited from https://codinginflow.com/tutorials/android/chronometer
 * Disabling the phone keyboard when the game st arts citation:
 * https://stackoverflow.com/questions/46024100/how-to-completely-disable-keyboard-when-using-edittext-in-android
 */
public class game24Activity extends AppCompatActivity implements Serializable{

    Random random = new Random();
    int a1 = random.nextInt(9) + 1;
    int a2 = random.nextInt(9) + 1;
    int a3 = random.nextInt(9) + 1;
    int a4 = random.nextInt(9) + 1;

    ImageView imageView1 = null;
    ImageView imageView2 = null;
    ImageView imageView3 = null;
    ImageView imageView4 = null;

    String inputString;

    public static final String USER_SCORE = "user_score";

    private boolean win = false;

    private ScoreManager sm;

    public final static String SAVE_SCORE = "save_score.ser";

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game24);

        final Button StartButton = findViewById(R.id.startBtn);
        EditText editText = findViewById(R.id.inputText);
        editText.setEnabled(false);
        editText.setFocusable(false);
//        editText.setInputType(0);
        Button btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setEnabled(false);
        final Button undo = findViewById(R.id.undoBtn);
        final Button btnLoad = findViewById(R.id.btnLoad);
        final Button btnResult = findViewById(R.id.btnResult);
        btnResult.setEnabled(false);
        btnResult.setFocusable(false);

        final ImageView imageView1 = findViewById(R.id.imageView1);
        final ImageView imageView2 = findViewById(R.id.imageView2);
        final ImageView imageView3 = findViewById(R.id.imageView3);
        final ImageView imageView4 = findViewById(R.id.imageView4);

        final ImageView btnLeft = findViewById(R.id.btnLeft);
        final ImageView btnRight = findViewById(R.id.btnRight);
        final ImageView btnPlus = findViewById(R.id.btnPlus);
        final ImageView btnMinus = findViewById(R.id.btnMinus);
        final ImageView btnMultiply = findViewById(R.id.btnMultiply);
        final ImageView btnDivide = findViewById(R.id.btnDivide);

        this.chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        /**
         * Activity the start button.
         */
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //enable chronometer
                startChronometer();

                //enable confirm
                btnConfirm.setEnabled(true);
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

                btnLeft.setClickable(true);
                btnRight.setClickable(true);
                btnPlus.setClickable(true);
                btnMinus.setClickable(true);
                btnMultiply.setClickable(true);
                btnDivide.setClickable(true);

                inputString = "";


            }
        });

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
                System.out.println("onchange..");
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

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageView1.setClickable(false);
                inputString += a1;
                editText.setText(inputString);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView2.setClickable(false);
                inputString += a2;
                editText.setText(inputString);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView3.setClickable(false);
                inputString += a3;
                editText.setText(inputString);
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView4.setClickable(false);
                inputString += a4;
                editText.setText(inputString);
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputString += "(";
                editText.setText(inputString);
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputString += ")";
                editText.setText(inputString);
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputString += "+";
                editText.setText(inputString);
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputString += "-";
                editText.setText(inputString);
            }
        });

        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputString += "*";
                editText.setText(inputString);
            }
        });

        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputString += "/";
                editText.setText(inputString);
            }
        });


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undo.setClickable(false);
                undo.setEnabled(false);
                // disable chronometer
                pauseChronometer();
                btnResult.setEnabled(true);
                btnResult.setFocusable(true);
                btnResult.setClickable(true);
                StartButton.setClickable(false);
                btnLoad.setClickable(false);

                btnLeft.setClickable(false);
                btnRight.setClickable(false);
                btnPlus.setClickable(false);
                btnMinus.setClickable(false);
                btnMultiply.setClickable(false);
                btnDivide.setClickable(false);

                String finalResult = getFinalResult(inputString);
                editText.setText(finalResult);
//                ScoreManager sm;
//                sm = new ScoreManager();
//                if(win){
//                    System.out.println(pauseOffset);
//                    sm.addScore("userName", Long.valueOf(pauseOffset));
//                }else{
//                    sm.addScore("userName", Long.valueOf(0));
//                }
//                saveScoreToFile(SAVE_SCORE);

                SharedPreferences settings = getSharedPreferences(USER_SCORE, 0);
                SharedPreferences.Editor editor = settings.edit();
                String user = "user";
//                String userName = Integer.parseInt(user);// here userName = EditText.getText().toString()

                editor.putString("userName",user) ;
                SharedPreferences settings1 = getSharedPreferences("score", 0);
                SharedPreferences.Editor editor1 = settings1.edit();
                editor1.putLong("score", Long.valueOf(pauseOffset));
                editor.commit();

            }

        });

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScore();
            }
        });

        /**
         * Activity the undo button.
         */
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastStr = inputString.substring(inputString.length()-1);
                int indicator = checkIfNumber(lastStr);
                if (inputString.length() != 0) {
                    if(indicator == a1)
                    if(indicator == a1){
                        imageView1.setClickable(true);
                    } if(indicator == a2){
                        imageView2.setClickable(true);
                    } if(indicator == a3){
                        imageView3.setClickable(true);
                    }else {
                        imageView4.setClickable(true);
                    }
                    inputString = inputString.substring(0, inputString.length() - 1);
                    editText.setText(inputString);

                } else {
                    editText.setText("No Step to Undo!");
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

    /**
     * A method that start
     * s the chronometer.
     */
    private void startChronometer() {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
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

    public String getFinalResult(String str) {
        int re = judgeTransferable(str);
        if (re == 0) {
            return "Ooop! Computer cannot do this math!";
        } else {
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
        Intent scoreboard = new Intent(this, ScoreBoard24GameActivity.class);
        scoreboard.putExtra("score", pauseOffset);
        scoreboard.putExtra("current_user", getIntent().getStringExtra("current_user"));
        startActivity(scoreboard);
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
