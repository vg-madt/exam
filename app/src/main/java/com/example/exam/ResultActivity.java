package com.example.exam;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    TextView resultString;
    Button exit;
    DatabaseInitClass db;
    String user;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        db = new DatabaseInitClass(this);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        String score = bundle.getString("score");
        String user = bundle.getString("user");
        String testName = bundle.getString("testName");
        String total = bundle.getString("total");

        resultString = findViewById(R.id.resultText);
        exit = findViewById(R.id.exitBtn);
        int scoreInt = Integer.parseInt(score);
        checkScore(scoreInt,score,user,testName,total);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ResultActivity.this, "Good Bye!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(),FirstPageActivity.class);
                startActivity(intent);
            }
        });

    }

    //checks the score and displays pass or fail
    public void checkScore(int scoreInt,String score,String user,String testName,String total){
         int totalQ = Integer.parseInt(total);
        long value = db.addScore(testName,user,scoreInt);
        int percent = (scoreInt * 100)/totalQ;
        if(value > 0){
        if(percent > 50){

            resultString.setText("Congratulations! " + user + " You have passed with score: " +score);
        }else{
            resultString.setText("You have not passed. Score is:  " +score);
        }
    }
    }
}
