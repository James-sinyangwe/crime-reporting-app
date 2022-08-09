package com.example.myapplication.crimereportingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://crime-reporting-app-6db86-default-rtdb.firebaseio.com/");
       private EditText firstname,lastname,NRCnumber,phonenumber,email,password1,password2;
       private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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
                final String phonenumberTxt = phonenumber.getText().toString();
                final String emailTxt = email.getText().toString();
                final String password1Txt = password1.getText().toString();
                final String password2Txt = password2.getText().toString();

                if(firstnameTxt.isEmpty() || lastnameTxt.isEmpty() || nrcTxt.isEmpty() || phonenumberTxt.isEmpty()
                || emailTxt.isEmpty() || password1Txt.isEmpty() || password2Txt.isEmpty()){
                    Toast.makeText(SignupActivity.this, "Please fill all fields",Toast.LENGTH_SHORT).show();

                }else if(!password1Txt.equals(password2Txt)){

                    Toast.makeText(SignupActivity.this,"passwords are not matching",Toast.LENGTH_SHORT).show();


                }else {
                    databaseReference.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // here we"re checking if the phone number is already registered
                            if(snapshot.hasChild(phonenumberTxt)){
                                Toast.makeText(SignupActivity.this,"phone number is already registered",Toast.LENGTH_SHORT).show();

                            } else {
                                //database referencing
                                databaseReference.child("users").child(phonenumberTxt).child("firstname").setValue(firstnameTxt);
                                databaseReference.child("users").child(phonenumberTxt).child("lastname").setValue(lastnameTxt);
                                databaseReference.child("users").child(phonenumberTxt).child("NRCNumber").setValue(nrcTxt);
                                databaseReference.child("users").child(phonenumberTxt).child("email").setValue(emailTxt);
                                databaseReference.child("users").child(phonenumberTxt).child("password1").setValue(password1Txt);
                                databaseReference.child("users").child(phonenumberTxt).child("password2").setValue(password2Txt);

                                Toast.makeText(SignupActivity.this,"User Registered Successfully",Toast.LENGTH_SHORT).show();
                                finish();
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