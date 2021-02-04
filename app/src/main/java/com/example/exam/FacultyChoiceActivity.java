package com.example.exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FacultyChoiceActivity extends AppCompatActivity {

    Button viewScore,addQuestion,viewQues;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_choice);

        viewScore = findViewById(R.id.viewresultBtn);
        addQuestion = findViewById(R.id.addquestionbtn);
        viewQues = findViewById(R.id.viewQues);

        //View Questions button
        viewQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),QuestionsViewActivity.class);
                startActivity(intent);
            }
        });

        //Add questions button
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),QuestionsActivity.class);
                startActivity(intent);
            }
        });

        //View score button
        viewScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),ViewTestScoreActivity.class);
                startActivity(intent);
            }
        });

    }
}

