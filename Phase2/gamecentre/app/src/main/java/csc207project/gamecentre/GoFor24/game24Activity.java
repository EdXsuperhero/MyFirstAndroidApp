package csc207project.gamecentre.GoFor24;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

import csc207project.gamecentre.R;

public class game24Activity extends AppCompatActivity {

    Random random = new Random();
    int a1 = random.nextInt(9) + 1;
    int a2 = random.nextInt(9) + 1;
    int a3 = random.nextInt(9) + 1;
    int a4 = random.nextInt(9) + 1;

    String inputString = "";

    ArrayList<String> undo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game24);

        final Button StartButton = findViewById(R.id.startBtn);
        final EditText editText = findViewById(R.id.inputText);
        final Button Comfirm = findViewById(R.id.btnComfirm);

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

        editText.setShowSoftInputOnFocus(false);
        editText.setInputType(InputType.TYPE_NULL);
        editText.setFocusable(false);


        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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

        Comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StartButton.setClickable(false);

                btnLeft.setClickable(false);
                btnRight.setClickable(false);
                btnPlus.setClickable(false);
                btnMinus.setClickable(false);
                btnMultiply.setClickable(false);
                btnDivide.setClickable(false);

                String finalResult = getFinalResult(inputString);
                editText.setText(finalResult);

            }
        });






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


}
