package com.example.exam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class QuestionsViewActivity extends AppCompatActivity {

    ListView lv;
    Spinner sp;
    DatabaseInitClass db;
    ArrayList<String> allTests;
    ArrayList<Question> allQuestions;
    ArrayList<String> allQ = new ArrayList<>();
    String curQuestion,curOpt1,curOpt2,curOpt3,curTest;
    int curAns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        lv = findViewById(R.id.quesList);
        sp = findViewById(R.id.spinner);
        db = new DatabaseInitClass(this);
        allTests = new ArrayList<>();
        allQuestions = new ArrayList<Question>();


        allTests = db.getAllTests();
        MyAdapter adapter = new MyAdapter(getBaseContext(),allQ);
        lv.setAdapter(adapter);



        ArrayAdapter aa=new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,allTests);
        aa.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp.setAdapter(aa);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTest = sp.getSelectedItem().toString();
                allQuestions = db.getAllTheQuestions(selectedTest);
                getQ(allQuestions);

                MyAdapter adapter = new MyAdapter(getBaseContext(),allQ);
                lv.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ques = allQ.get(position);
                getDetails(ques);
                Toast.makeText(QuestionsViewActivity.this, "Selected", Toast.LENGTH_LONG).show();

                //intent to the nextActivity page. takes values to the next page through bundle and intent
                Intent intent = new Intent(getBaseContext(), EditQuestionActivity.class);
                Bundle display = new Bundle();
                display.putString("ques",curQuestion);
                display.putString("opt1", curOpt1);
                display.putString("opt2", curOpt2);
                display.putString("opt3", curOpt3);
                display.putString("ans", String.valueOf(curAns));
                display.putString("test",curTest);
                intent.putExtras(display);
                startActivity(intent);

            }
        });

    }
//gets all questions
    public void getQ(ArrayList<Question> questions){
        allQ.removeAll(allQ);
        for(int i=0;i<questions.size();i++){
            String q = questions.get(i).getQuestion();
            allQ.add(q);
        }
    }
//custom listview
    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        ArrayList<String> questions;

        MyAdapter(Context c, ArrayList questions){
            super(c,R.layout.row,R.id.username,questions);
            this.context = c;
            this.questions = questions;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.row_of_ques,parent,false);
            TextView quest = row.findViewById(R.id.ques);

            quest.setText(questions.get(position));
            return row;
        }
    }
//sets the values of current question
    public void getDetails(String ques){
        for(int i =0; i<allQuestions.size();i++){
            if(ques.equals(allQuestions.get(i).getQuestion())){
                curQuestion = ques;
                curOpt1 = allQuestions.get(i).getOption1();
                curOpt2 = allQuestions.get(i).getOption2();
                curOpt3 = allQuestions.get(i).getOption3();
                curAns = allQuestions.get(i).getAnswerNumber();
                curTest = allQuestions.get(i).getTestname();
            }
        }
    }
}
