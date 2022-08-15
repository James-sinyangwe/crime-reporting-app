package com.example.myapplication.crimereportingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://crime-reporting-app-6db86-default-rtdb.firebaseio.com/");
       private EditText firstname,lastname,NRCnumber,phonenumber,email,password1,password2;
       private Button submit;
             ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        firstname =(EditText) findViewById(R.id.firstname);
        lastname =(EditText) findViewById(R.id.lastname);
        NRCnumber =(EditText) findViewById(R.id.nrcnumber);
        phonenumber =(EditText) findViewById(R.id.phonenumber);
        email =(EditText) findViewById(R.id.email);
        password1 =(EditText) findViewById(R.id.password1);
        password2 =(EditText) findViewById(R.id.password2);
        submit =(Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String firstnameTxt = firstname.getText().toString();
                final String lastnameTxt = lastname.getText().toString();
                final String nrcTxt = NRCnumber.getText().toString();
                final String phoneTxt = phonenumber.getText().toString();
                final String emailTxt = email.getText().toString();
                final String password1Txt = password1.getText().toString();
                final String password2Txt = password2.getText().toString();

                if (firstnameTxt.isEmpty()) {
                    firstname.setError("FirstName is required");
                    firstname.requestFocus();
                    return;
                }
                if (lastnameTxt.isEmpty()) {
                    lastname.setError("Lastname is required");
                    lastname.requestFocus();
                    return;
                }
                if (nrcTxt.isEmpty()) {
                    NRCnumber.setError("NRC is required");
                    NRCnumber.requestFocus();
                    return;
                }
                if (phoneTxt.isEmpty()) {
                    phonenumber.setError("Phone number is required");
                    phonenumber.requestFocus();
                    return;
                }
                if (emailTxt.isEmpty()) {
                    email.setError("Email is required");
                    email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()) {
                    email.setError("Please Enter a Valid Email");
                    email.requestFocus();
                    return;
                }
                if (password1Txt.isEmpty()) {
                    password1.setError("password is required");
                    password1.requestFocus();
                    return;
                }
                if (password1Txt.length() < 6) {
                    password1.setError("your password length should be great than 6");
                }
                if (password2Txt.isEmpty()) {
                    password2.setError("confirm your password");
                    password2.requestFocus();
                    return;
                }
                if (!password1Txt.equals(password2Txt)) {

                    Toast.makeText(SignupActivity.this, "passwords are not matching", Toast.LENGTH_SHORT).show();


                }
                                 progressBar.setVisibility(View.VISIBLE);
                                mAuth.createUserWithEmailAndPassword(emailTxt,password1Txt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            user user = new user( firstnameTxt, lastnameTxt,nrcTxt, phoneTxt,emailTxt, password1Txt);
                                            FirebaseDatabase.getInstance().getReference("users")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(SignupActivity.this,"user has been registered successfully", Toast.LENGTH_LONG).show();
                                                    progressBar.setVisibility(View.GONE);
                                                    }else {
                                                        Toast.makeText(SignupActivity.this,"user registration failed", Toast.LENGTH_LONG).show();
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                }
                                            });
                                        }else{
                                            Toast.makeText(SignupActivity.this,"user registration failed", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });/*

                else {
                    databaseReference.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // here we"re checking if the phone number is already registered

                            if (snapshot.hasChild(phoneTxt)) {
                                Toast.makeText(SignupActivity.this, "phone number is already registered", Toast.LENGTH_SHORT).show();

                            } else {
                                //database referencing
                                databaseReference.child("users").child(phoneTxt).child("firstname").setValue(firstnameTxt);
                                databaseReference.child("users").child(phoneTxt).child("lastname").setValue(lastnameTxt);
                                databaseReference.child("users").child(phoneTxt).child("NRCNumber").setValue(nrcTxt);
                                databaseReference.child("users").child(phoneTxt).child("email").setValue(emailTxt);
                                databaseReference.child("users").child(phoneTxt).child("password1").setValue(password1Txt);
                                databaseReference.child("users").child(phoneTxt).child("password2").setValue(password2Txt);


                                Toast.makeText(SignupActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }


            }
        });*/

    }
});
    }
}