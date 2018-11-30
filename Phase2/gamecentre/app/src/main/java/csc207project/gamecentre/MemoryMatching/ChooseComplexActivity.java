
package csc207project.gamecentre.MemoryMatching;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import csc207project.gamecentre.R;

/**
 * The activity for selecting complexity.
 */
public class ChooseComplexActivity extends AppCompatActivity {

    /**
     * Intent for sending back result.
     */
    private Intent returnIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_matching_complex);
        addTwoComplexityListener();
        addFourComplexityListener();
        addSixComplexityListener();
    }

    @Override
    public void finish() {
        setResult(0, returnIntent);
        super.finish();
    }

    /**
     * Activate the three by three complexity.
     */
    private void addTwoComplexityListener(){
        Button easy = findViewById(R.id.easy1);
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnIntent.putExtra("complexity", 2);
                finish();
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
                returnIntent.putExtra("complexity", 4);
                finish();
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
                returnIntent.putExtra("complexity", 6);
                finish();
            }
        });
    }
}