package com.example.myapplication.crimereportingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

     DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://crime-reporting-app-6db86-default-rtdb.firebaseio.com/");
      private Button sign_up_button,login_button;
      private EditText email_Edittext,password_Edittext;
      private FirebaseAuth mAuth;
      ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email_Edittext=(EditText) findViewById(R.id.user_name);
        password_Edittext =(EditText) findViewById(R.id.password);
        login_button =(Button) findViewById(R.id.login_button);
        progressBar =(ProgressBar)findViewById(R.id.progressBar2);
        mAuth=FirebaseAuth.getInstance();
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
               final String EmailTxt =email_Edittext.getText().toString();
               final String passwordTXt =password_Edittext.getText().toString();

               if  (EmailTxt.isEmpty()){
                         email_Edittext.setError("Please Enter Email");
                         email_Edittext.requestFocus();
                         return;
               }
                if(!Patterns.EMAIL_ADDRESS.matcher(EmailTxt).matches()){

                    email_Edittext.setError("Please Enter a Valid Email");
                    email_Edittext.requestFocus();
                    return;
                }
               if (passwordTXt.isEmpty()){
                   password_Edittext.setError("Please Enter your password");
                   password_Edittext.requestFocus();
                           return;
               }
               if(passwordTXt.length()<6){
                   password_Edittext.setError("Minimum password length is 6 characters");
               }
               progressBar.setVisibility(View.VISIBLE);
               mAuth.signInWithEmailAndPassword(EmailTxt,passwordTXt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           Intent intent = new Intent(MainActivity.this,DashboardActivity.class);
                           startActivity(intent);
                   }else{
                       Toast.makeText(MainActivity.this,"Faild to login! Please Check your Credentials!",Toast.LENGTH_LONG).show();
                       }
                   }
               });

            }
        });
    }
}