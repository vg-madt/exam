package com.example.exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    EditText confirmPassword;
    Button registerButton;
    TextView register;
    DatabaseInitClass db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        db = new DatabaseInitClass(this);
        username = findViewById(R.id.rusrName);
        password = findViewById(R.id.rpwdText);
        confirmPassword = findViewById(R.id.rePwdText);
        registerButton = findViewById(R.id.signUp);
        register = findViewById(R.id.txtLogin);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr = username.getText().toString().trim();
                String pwd = password.getText().toString().trim();
                String rePwd = confirmPassword.getText().toString().trim();

                if(pwd.equals(rePwd)){
                    long value = db.addUser(usr,pwd);
                    if(value > 0) {
                        Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
                        Intent loginScreen = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(loginScreen);
                    }else{
                        Toast.makeText(RegisterActivity.this, "Registration error", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

