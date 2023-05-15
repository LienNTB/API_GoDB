package com.example.ltdd_finalproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ltdd_finalproject.R;

public class LoginActivity extends AppCompatActivity {

    //Init Component
    EditText passwordEdit,usernameEdit;
    Button loginButton;
    TextView regisTextview,forgotTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        setEvent();
    }
    protected  void setEvent(){
        //Event for login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        //
        //forgot
        forgotTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something when the TextView is clicked
                Toast.makeText(getApplicationContext(), "Forgot under development", Toast.LENGTH_SHORT).show();
            }
        });

        //Register
        regisTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something when the TextView is clicked
                Intent intent = new Intent(LoginActivity.this, RegisActivity.class);
                startActivity(intent);
            }
        });
    }
    protected void anhXa(){

        loginButton = (Button) findViewById(R.id.loginButton);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        usernameEdit = (EditText) findViewById(R.id.usernameEdit);
        regisTextview = (TextView) findViewById(R.id.regisTextview);
        forgotTextview = (TextView) findViewById(R.id.forgotTextview);

    }
}