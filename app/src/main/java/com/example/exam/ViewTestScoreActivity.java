package com.example.exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewTestScoreActivity extends AppCompatActivity {
    Spinner sp;
    ArrayList<String> allTests;
    String testName;
    Button start;
    Bundle b = new Bundle();
    ArrayList<Integer> scores = new ArrayList<>();
    ArrayList<String> users = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_test);
        final DatabaseInitClass db = new DatabaseInitClass(this);
        allTests = new ArrayList<>();
        start = findViewById(R.id.startTest);
        sp = findViewById(R.id.spinner);

        allTests = db.getAllTests();

        ArrayAdapter aa=new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,allTests);
        aa.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp.setAdapter(aa);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                testName = sp.getSelectedItem().toString();
                b.putString("testname", testName);
                scores = db.getAllScore(testName);
                users = db.getAllUsers(testName);
                b.putIntegerArrayList("scores",scores);
                b.putStringArrayList("users",users);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(getBaseContext(), ScorelistActivity.class);

                    intent.putExtras(b);
                    startActivity(intent);

                }
        });

    }
}
