package csc207project.gamecentre.SlidingTiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import csc207project.gamecentre.R;

/**
 * The activity for selecting complexity.
 */
public class ChooseComplexity extends AppCompatActivity {

    /**
     * Intent for sending back result.
     */
    private Intent returnIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_complexity);

        addThreeComplexityListener();
        addFourComplexityListener();
        addFiveComplexityListener();
    }

    @Override
    public void finish() {
        setResult(0, returnIntent);
        super.finish();
    }

    /**
     * Activate the three by three complexity.
     */
    private void addThreeComplexityListener(){
        Button three_by_three = findViewById(R.id.easy);
        three_by_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnIntent.putExtra("complexity", 3);
                finish();
            }
        });
    }

    /**
     * Activate the four by four complexity.
     */
    private void addFourComplexityListener(){
        Button four_by_four = findViewById(R.id.normal);
        four_by_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnIntent.putExtra("complexity", 4);
                finish();
            }
        });
    }

    /**
     * Activate the five by five complexity.
     */
    private void addFiveComplexityListener(){
        Button five_by_five = findViewById(R.id.hard);
        five_by_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnIntent.putExtra("complexity", 5);
                finish();
            }
        });
    }
}