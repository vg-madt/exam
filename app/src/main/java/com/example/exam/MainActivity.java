package com.example.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView questionText;
    RadioGroup rg;
    RadioButton opt1, opt2, opt3;
    Button next;
    TextView questionFlag, time;
    ImageView img;
    private ArrayList<Question> listOfQuestions;
    private int quescounter;
    private int totalQues;
    private Question curQuestion;
    private ColorStateList defaultColor;

    public int score;
    private boolean answered;
    String user;
    DatabaseInitClass db;
    String testName;

    private static final long COUNTDOWN = 60000;

    private CountDownTimer countDownTimer;
    private long timeLeft;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseInitClass(this);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        user = bundle.getString("user");
        testName = bundle.getString("testName");

        questionText = findViewById(R.id.quesText);
        opt1 = findViewById(R.id.radAns1);
        opt2 = findViewById(R.id.radAns2);
        opt3 = findViewById(R.id.radAns3);
        rg = findViewById(R.id.radioGroup2);
        next = findViewById(R.id.nextbtn);
        questionFlag = findViewById(R.id.quesNo);
        time = findViewById(R.id.timer);
        img = findViewById(R.id.imageView);

        defaultColor = opt1.getTextColors();

        DatabaseInitClass db = new DatabaseInitClass((this));


        listOfQuestions = db.getAllTheQuestions(testName);
        totalQues = listOfQuestions.size();
        Collections.shuffle(listOfQuestions);

        nextQuestionDisplay();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answered){
                    if(opt1.isChecked() || opt2.isChecked() || opt3.isChecked()){
                        checkAnswer();
                    }else{
                        Toast.makeText(MainActivity.this, "Please select an answer", Toast.LENGTH_LONG).show();
                    }
                } else{
                    nextQuestionDisplay();
                }
            }
        });

    }

    //displays questions from the db
    private void nextQuestionDisplay(){
        opt1.setTextColor(defaultColor);
        opt2.setTextColor(defaultColor);
        opt3.setTextColor(defaultColor);

        rg.clearCheck();

        if(quescounter < totalQues){
            curQuestion = listOfQuestions.get(quescounter);

            questionText.setText(curQuestion.getQuestion());
            opt1.setText(curQuestion.getOption1());
            opt2.setText(curQuestion.getOption2());
            opt3.setText(curQuestion.getOption3());

            quescounter++;
            questionFlag.setText(quescounter +"/" + totalQues);
            answered = false;
            next.setText("Confirm");

            //countdown timer
            timeLeft = COUNTDOWN;
            startCountDown();
        }else{
            finishQuiz();
        }
    }

    //countdown timer
    private void startCountDown(){
        countDownTimer = new CountDownTimer(timeLeft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateCountDown();
            }

            @Override
            public void onFinish() {
                timeLeft = 0;
                updateCountDown();
                checkAnswer();
            }
        }.start();


    }

    //updates countdown
    private void updateCountDown(){
        int min = (int) (timeLeft/1000)/60;
        int sec = (int) (timeLeft/1000)%60;

        String timeFormat = String.format(Locale.getDefault(),"%02d:%02d",min,sec);
        time.setText(timeFormat);

        if(timeLeft <10000){
            time.setTextColor(Color.RED);
        }else{
            time.setTextColor(getResources().getColor((R.color.half_white)));
        }
    }

    //checks if answer is correct and displays an image
    private void checkAnswer(){
        answered = true;
        countDownTimer.cancel();
        RadioButton rb = findViewById(rg.getCheckedRadioButtonId());

        int ansnr = rg.indexOfChild(rb) + 1;
        if(ansnr == curQuestion.getAnswerNumber()){
            score++;
            img.setVisibility(View.VISIBLE);
            img.setImageDrawable(getResources().getDrawable(R.drawable.clap));

        }else if(ansnr != curQuestion.getAnswerNumber()){
            img.setVisibility(View.VISIBLE);
            img.setImageDrawable(getResources().getDrawable(R.drawable.sad));
        }
        showSelectedAnswer();
    }

    private void showSelectedAnswer(){
        switch(curQuestion.getAnswerNumber()){
            case 1:
                opt1.setTextColor(Color.parseColor("#00223E"));
                break;
            case 2:
                opt2.setTextColor(Color.parseColor("#00223E"));
                break;
            case 3:
                opt3.setTextColor(Color.parseColor("#00223E"));
                break;
        }
        if(quescounter < totalQues){
            next.setText("NEXT");
        }else{
            next.setText("FINISH");
        }
    }

    private void finishQuiz(){
        Toast.makeText(MainActivity.this, "End of Test", Toast.LENGTH_LONG).show();


        Intent afterTest = new Intent(MainActivity.this, ResultActivity.class);
        //String user = username.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("testName",testName);
        bundle.putString("user",user);
        bundle.putString("score", score +"");
        bundle.putString("total",totalQues+"");
        afterTest.putExtras(bundle);
        startActivity(afterTest);
        finish();
    }

    //resets countdown timer
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }
}
