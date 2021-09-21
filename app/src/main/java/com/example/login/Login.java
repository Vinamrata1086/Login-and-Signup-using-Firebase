package com.example.login;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    Button login, signup;
    TextView acc;
    EditText email, pass;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-e2402-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup);
        acc=findViewById(R.id.acc);
        email=findViewById(R.id.email);
        pass = findViewById(R.id.pas);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eml = email.getText().toString();
                String pas = pass.getText().toString();
                if (eml.isEmpty()) {
                    email.setError("Enter the email address.");
                    email.requestFocus();
                } else if (pas.isEmpty()) {
                    pass.setError("Enter the password.");
                    pass.requestFocus();
                } else if (eml.isEmpty() && pas.isEmpty()) {
                    Toast.makeText(Login.this, "Email and Password fields are empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (Patterns.EMAIL_ADDRESS.matcher(eml).matches()) {
                        reference.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(eml)) {
                                    String password = snapshot.child(eml).child("password").getValue(String.class);
                                    if (password.equals(pas)) {
                                        Toast.makeText(Login.this, "Login successful!!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(Login.this, "Bad Credentials", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Login.this, "Bad Credentials", Toast.LENGTH_SHORT).show();
                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    else{
                        email.setError("Pleas Enter Correct Email");
                        email.requestFocus();



                    }
                }
            }
        });



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Signup.class);
                startActivity(intent);
            }
        });




    }
}