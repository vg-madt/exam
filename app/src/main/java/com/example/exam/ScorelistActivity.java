package com.example.exam;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ScorelistActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<String> username = new ArrayList<>();
    ArrayList<Integer> score = new ArrayList<>();
    String testname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorelist);
        DatabaseInitClass db = new DatabaseInitClass(this);


        Bundle b = getIntent().getExtras();
        testname = b.getString("testname");
        username = b.getStringArrayList("users");
        score = b.getIntegerArrayList("scores");


        lv = findViewById(R.id.scoreListView);

        MyAdapter adapter = new MyAdapter(getBaseContext(),username,score);
        lv.setAdapter(adapter);
    }

//custom adapter to display the list of scores and users along with test name
    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> nusers;
        ArrayList<Integer> nscores;

        MyAdapter(Context c, ArrayList users,ArrayList scores) {
            super(c, R.layout.row, R.id.username, users);
            this.context = c;
            this.nusers = users;
            this.nscores = scores;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.row, parent, false);
            TextView username = row.findViewById(R.id.username);
            TextView scoreText = row.findViewById(R.id.score);
            TextView testName = row.findViewById(R.id.testname);

            testName.setText(testname);
            username.setText(nusers.get(position));
            scoreText.setText(nscores.get(position).toString());

            return row;
        }
    }
}

