package csc207project.gamecentre.TwentyFourGame;

import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;

import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import java.util.Random;

import csc207project.gamecentre.R;
import csc207project.gamecentre.filemanagement.FileManagerSingleton;

/**
 * The idea of chronometer is cited from https://codinginflow.com/tutorials/android/chronometer
 * Disabling the phone keyboard when the game st arts citation:
 * https://stackoverflow.com/questions/46024100/how-to-completely-disable-keyboard-when-using-edittext-in-android
 */
public class game24Activity extends AppCompatActivity {

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
    void getValidNumber(){
        a1 = validList[0];
        a2 = validList[1];
        a3 = validList[2];
        a4 = validList[3];
    }

    /**
     * A Chronometer to calculate time.
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game24);

        editText = findViewById(R.id.inputText);

        editText.setEnabled(false);
        editText.setFocusable(false);
        editText.setInputType(0);
        btnConfirm = findViewById(R.id.btnComfirm);
        btnConfirm.setEnabled(false);

        Button btnLoad = findViewById(R.id.btnLoad);

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
        addMultiplyButtontListener();
        addDivideButtonListener();

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileManagerSingleton fileManagerSingleton = new FileManagerSingleton();
                fileManagerSingleton.loadFromFile(GAME24POINTS_FILE_NAME);
                editText.setText(inputString);
            }
        });

        final SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count)
            {
                prefs.edit().putString("autoSave", s.toString()).commit();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // disable chronometer
                pauseChronometer();

                undo.setClickable(false);


                setOperatorClickable(false);


                String finalResult = getFinalResult(inputString);
                editText.setText(finalResult);
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
        }

    private void addStartButtonListener(){
        Button StartButton = findViewById(R.id.startBtn);
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

    private void setImageView1Listener(){
        imageView1 = findViewById(R.id.imageView1);
        numberImageViewListener(imageView1,a1);
    }

    private void setImageView2Listener(){
        imageView2 = findViewById(R.id.imageView2);
        numberImageViewListener(imageView2,a2);
    }

    private void setImageView3Listener(){
        imageView3 = findViewById(R.id.imageView3);
        numberImageViewListener(imageView3,a3);
    }

    private void setImageView4Listener(){
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

    private void addMultiplyButtontListener(){
        btnMultiply = findViewById(R.id.btnMultiply);
        operatorImageListener(btnMultiply,"*");
    }

    private void addDivideButtonListener(){
        btnDivide = findViewById(R.id.btnDivide);
        operatorImageListener(btnDivide,"/");
    }

    private void addUndoButtonListener(){
        undo = findViewById(R.id.undoBtn);
        undo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(inputString.length() > 0){
                    String lastStr = inputString.substring(inputString.length()-1);
                    int indicator =checkIfNumber(lastStr);
                    if(indicator > 0){
                        if(indicator == a1){
                            imageView1.setClickable(true);
                        }if(indicator == a2){
                            imageView2.setClickable(true);
                        }if(indicator == a3){
                            imageView3.setClickable(true);
                        }else{
                            imageView4.setClickable(true);
                        }
                    }
                    inputString = inputString.substring(0,inputString.length()-1);
                    editText.setText(inputString);
                }else{
                    editText.setText("No Step to Undo!");
                }
            }

        });
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
            return "Ooop! Computer cannot do this math!";
        }else{
            String result = String.valueOf(re);
            return result;
        }
    }

    public int judgeTransferable(String s){
        int i = 0;
        try{
            ChangeString changeString = new ChangeString();
            ArrayList result = changeString.getStringList(s);
            result = changeString.getPostOrder(result);
            i = changeString.calculate(result);
        }catch (Exception e){
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
}


