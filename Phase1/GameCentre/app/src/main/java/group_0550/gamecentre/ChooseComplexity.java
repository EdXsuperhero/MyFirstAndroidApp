package group_0550.gamecentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class ChooseComplexity extends StartingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_complexity);

        add_three();
        add_four();
        add_five();
    }

    public void add_three(){
        Button three_by_three = findViewById(R.id.button10);
        three_by_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Board.NUM_COLS = 3;
                Board.NUM_ROWS = 3;
                Board.NUM_TILES = 9;
                boardManager = new BoardManager();
                switchToGame();
            }

        });
    }

    public void add_four(){
        Button four_by_four = findViewById(R.id.button12);
        four_by_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Board.NUM_COLS = 4;
                Board.NUM_ROWS = 4;
                Board.NUM_TILES = 16;
                boardManager = new BoardManager();
                switchToGame();

            }

        });
    }

    public void add_five(){
        Button five_by_five = findViewById(R.id.button13);
        five_by_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Board.NUM_COLS = 5;
                Board.NUM_ROWS = 5;
                Board.NUM_TILES = 25;
                boardManager = new BoardManager();
                switchToGame();

            }

        });
    }
}