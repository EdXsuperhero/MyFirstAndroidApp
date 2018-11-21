
package csc207project.gamecentre.MemoryMatchingPics;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import csc207project.gamecentre.R;


/**
 * The activity for selecting complexity.
 */
public class ChooseComplex extends MatchingStartingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_matching_complex);
        addTwoComplexityListener();
        addFourComplexityListener();
        addSixComplexityListener();
    }
    /**
     * Activate the three by three complexity.
     */
    private void addTwoComplexityListener(){
        Button easy = findViewById(R.id.easy1);
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(2);
                switchToGame();
            }
        });
    }

    /**
     * Activate the four by four complexity.
     */
    private void addFourComplexityListener(){
        Button normal = findViewById(R.id.normal1);
        normal.setOnClickListener(new View.OnClickListener() {
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
    private void addSixComplexityListener(){
        Button hard = findViewById(R.id.hard1);
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(6);
                switchToGame();
            }
        });
    }
}