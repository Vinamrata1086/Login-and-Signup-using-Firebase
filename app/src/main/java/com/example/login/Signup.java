package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.ValueEventListener;


public class Signup extends AppCompatActivity {
    Button login, signup;
    
    EditText email, pass, con_pass,name;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-e2402-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        login=findViewById(R.id.login);
        name=findViewById(R.id.name);
        signup=findViewById(R.id.signup);
        //acc=findViewById(R.id.acc);
        email=findViewById(R.id.email);
        pass = findViewById(R.id.pas);
        con_pass=findViewById(R.id.con_pas);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eml = email.getText().toString();
                String pas = pass.getText().toString();
                String nam = name.getText().toString();
                String confm = con_pass.getText().toString();
                if (eml.isEmpty()) {
                    email.setError("Enter the email address.");
                    email.requestFocus();
                } else if (pas.isEmpty()) {
                    pass.setError("Enter the password.");
                    pass.requestFocus();
                }
                else if(nam.isEmpty()){
                    name.setError("Enter the full name.");
                    name.requestFocus();
                }
                else if(confm.isEmpty()){
                    con_pass.setError("Enter the same password.");
                    con_pass.requestFocus();
                }
                else if(!pas.equals(confm)){
                    con_pass.setError("Password does not match.");
                    con_pass.requestFocus();
                }
                else if (eml.isEmpty() && pas.isEmpty() && confm.isEmpty() && nam.isEmpty()) {
                    Toast.makeText(Signup.this, "Fill the empty fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (Patterns.EMAIL_ADDRESS.matcher(eml).matches()) {
                        reference.child("user").child(eml).child("name").setValue(nam);
                        reference.child("user").child(eml).child("password").setValue(pas);
                        Toast.makeText(Signup.this, " Sign up complete!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Signup.this, MainActivity.class));
                        finish();

                    }
                    else{
                        email.setError("Pleas Enter Correct Email");
                    }
                }

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this,Login.class));
                finish();
            }
        });
    }
}