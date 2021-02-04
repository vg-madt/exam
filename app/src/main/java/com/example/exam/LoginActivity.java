package com.example.exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    TextView register;
    DatabaseInitClass db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        db = new DatabaseInitClass(this);
        username = (EditText)findViewById(R.id.usrName);
        password = findViewById(R.id.pwdText);
        loginButton = findViewById(R.id.login);
        register = findViewById(R.id.txtRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr = username.getText().toString().trim();
                String pwd = password.getText().toString().trim();
                Boolean result = db.validate(usr, pwd);

                if(result == true){
                    Toast.makeText(LoginActivity.this, "Successfully Logged in", Toast.LENGTH_LONG).show();
                    Intent afterLogin = new Intent(LoginActivity.this, SelectTestActivity.class);
                    String user = username.getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("user",user);
                    afterLogin.putExtras(bundle);
                    startActivity(afterLogin);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Invalid user", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

