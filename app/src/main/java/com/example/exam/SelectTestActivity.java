package com.example.exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SelectTestActivity extends AppCompatActivity {
    Spinner sp;
    ArrayList<String> allTests;
    String testName;
    Button start;
    String user;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_test);
        final DatabaseInitClass db = new DatabaseInitClass(this);
        allTests = new ArrayList<>();
        start = findViewById(R.id.startTest);
        sp = findViewById(R.id.spinner);
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        user = bundle.getString("user");

        allTests = db.getAllTests();

        ArrayAdapter aa=new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,allTests);
        aa.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp.setAdapter(aa);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                testName = sp.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usr = user.trim();
                String test = testName.trim();

                boolean s = db.checkIfWritten(test, usr);
                if (s == true) {
                    Toast.makeText(SelectTestActivity.this, "Test taken already", Toast.LENGTH_SHORT).show();

                }else{
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    Bundle b = new Bundle();
                    b.putString("testName", testName);
                    b.putString("user", user);
                    intent.putExtras(b);
                    startActivity(intent);

                }
            }
        });

    }
}
