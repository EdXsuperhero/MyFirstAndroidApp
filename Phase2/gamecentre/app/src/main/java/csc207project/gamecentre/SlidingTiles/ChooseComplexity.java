package csc207project.gamecentre.SlidingTiles;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import csc207project.gamecentre.R;

/**
 * The activity for selecting complexity.
 */
public class ChooseComplexity extends StartingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_complexity);
        addThreeComplexityListener();
        addFourComplexityListener();
        addFiveComplexityListener();
    }

    /**
     * Activate the three by three complexity.
     */
    private void addThreeComplexityListener(){
        Button three_by_three = findViewById(R.id.threebythree);
        three_by_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(3);
                switchToGame();
            }
        });
    }

    /**
     * Activate the four by four complexity.
     */
    private void addFourComplexityListener(){
        Button four_by_four = findViewById(R.id.fourbyfour);
        four_by_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(4);
                switchToGame();
            }
        });
    }

    /**
     * Activate the five by five complexity.
     */
    private void addFiveComplexityListener(){
        Button five_by_five = findViewById(R.id.fivebyfive);
        five_by_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(5);
                switchToGame();
            }
        });
    }
}