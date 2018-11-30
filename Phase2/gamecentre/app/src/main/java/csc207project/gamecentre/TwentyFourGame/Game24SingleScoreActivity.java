package csc207project.gamecentre.GoFor24;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import csc207project.gamecentre.R;

public class Game24SingleScoreActivity extends AppCompatActivity {

    TextView tvYourScore;
    TextView tvYourName;
    TextView tvYourNewScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game24_single_score);

        tvYourScore = findViewById(R.id.txtView);
        tvYourName = findViewById(R.id.txtUsername);
        tvYourNewScore = findViewById(R.id.txtViewScore);

        SharedPreferences sp1 =this.getSharedPreferences("user_score",0);

        String userName = sp1.getString("userName", null);


        if(userName == null) userName ="guest";

        tvYourName.setText(userName);
        SharedPreferences sp2 =this.getSharedPreferences("score",0);

        Long score= sp2.getLong("score", 0);
        tvYourNewScore.setText(String.valueOf(score));


    }
}
