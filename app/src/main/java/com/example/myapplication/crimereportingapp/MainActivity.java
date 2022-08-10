package com.example.myapplication.crimereportingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
     DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://crime-reporting-app-6db86-default-rtdb.firebaseio.com/");
      private Button sign_up_button,login_button;
      private EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username =(EditText) findViewById(R.id.user_name);
        password =(EditText) findViewById(R.id.password);
        login_button =(Button) findViewById(R.id.login_button);
        sign_up_button=(Button) findViewById(R.id.sign_up_button);
        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final String phonenumberTxt =username.getText().toString();
               final String passwordTXt =password.getText().toString();

               if  (phonenumberTxt.isEmpty() || passwordTXt.isEmpty()){
                   Toast.makeText(MainActivity.this,"Username or Password is Empty",Toast.LENGTH_SHORT).show();

               }
               else{
                          databaseReference.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                              @Override
                              public void onDataChange(@NonNull DataSnapshot snapshot) {
                                  // here will check if the user's phone number is in the database
                                  if(snapshot.hasChild(phonenumberTxt)){

                                      //if the number is in the database then we check if the phone number and password match
                                      final String getpassword= snapshot.child(phonenumberTxt).child("password").getValue(String.class);

                                      if(getpassword.equals(passwordTXt)){
                                          Toast.makeText(MainActivity.this,"logged in Successfully",Toast.LENGTH_SHORT).show();

                                      }else{
                                          Toast.makeText(MainActivity.this,"Wrong password",Toast.LENGTH_SHORT).show();
                                      }
                                  }else{
                                      Toast.makeText(MainActivity.this,"Wrong password",Toast.LENGTH_SHORT).show();
                                  }
                              }

                              @Override
                              public void onCancelled(@NonNull DatabaseError error) {

                              }
                          });
               }
            }
        });
    }
}