package com.example.exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class QuestionsActivity extends AppCompatActivity {

    EditText ques,opt1,opt2,opt3,correct,test;
    Button add;
    Question question = new Question();
    DatabaseInitClass db;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        test = findViewById(R.id.testnameText);
        ques = findViewById(R.id.questionText);
        opt1 = findViewById(R.id.opt1Text);
        opt2 = findViewById(R.id.opt2Text);
        opt3 = findViewById(R.id.opt3Text);
        correct = findViewById(R.id.correctText);
        add = findViewById(R.id.addbtn);
        db = new DatabaseInitClass(this);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    getAllQuestion();
            }
        });
    }

    //creates questions
    public void setQuestions(){
        question.setTestname(test.getText().toString());
        question.setQuestion(ques.getText().toString());
        question.setOption1(opt1.getText().toString());
        question.setOption2(opt2.getText().toString());
        question.setOption3(opt3.getText().toString());
        int num = Integer.parseInt(correct.getText().toString());
        question.setAnswerNumber(num);
        long check = db.addToDb(question);
        if(check == -1){
            Toast.makeText(getBaseContext(), "Question already exists", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getBaseContext(), "Question added", Toast.LENGTH_LONG).show();
        }
        }
//sets question to database
    private void getAllQuestion(){

        if(ques.getText().toString().equals("")||opt1.getText().toString().equals("")||opt2.getText().toString().equals("")||
                opt3.getText().toString().equals("")||correct.getText().toString().equals("")){
            Toast.makeText(getBaseContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
        }
        else{

            int c = Integer.parseInt((correct.getText().toString()));
            if (c > 3) {
                Toast.makeText(getBaseContext(), "Invalid Correct answer", Toast.LENGTH_LONG).show();
            } else {

                setQuestions();
            }
                finishQuiz();
            }
        }

    public void finishQuiz(){

        Intent intent = new Intent(getBaseContext(),FacultyChoiceActivity.class);

        startActivity(intent);
    }

}
