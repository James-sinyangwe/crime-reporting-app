package com.example.myapplication.crimereportingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
      private Button sign_up_button,login_button;
      private EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sign_up_button=(Button) findViewById(R.id.sign_up_button);
        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}