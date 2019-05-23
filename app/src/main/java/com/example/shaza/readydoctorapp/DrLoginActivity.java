package com.example.shaza.readydoctorapp;

//import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//import android.util.Log;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DrLoginActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView DoctorEmail;
    private EditText DoctorPassword;
    Button mSignInButton;

    //Database
    private FirebaseAuth mAuth;
    private DatabaseReference table_doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_login);

        // Set up the login form.
        DoctorEmail = (AutoCompleteTextView) findViewById(R.id.drEmail);
        DoctorPassword = (EditText) findViewById(R.id.drPassword);
        mSignInButton = (Button) findViewById(R.id.sign_in_button);

       mAuth = FirebaseAuth.getInstance();

       table_doctor = FirebaseDatabase.getInstance().getReference().child("/doctor");

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String Doctor_Email = DoctorEmail.getText().toString();
                final String Doctor_Password = DoctorPassword.getText().toString();

                if (TextUtils.isEmpty(Doctor_Email) && TextUtils.isEmpty(Doctor_Password)) {
                    Toast.makeText(getApplicationContext(), "Enter email address & Password!", Toast.LENGTH_SHORT).show();
                }

               else if (TextUtils.isEmpty(Doctor_Email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                }

                else if (TextUtils.isEmpty(Doctor_Password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.signInWithEmailAndPassword(Doctor_Email, Doctor_Password)
                            .addOnCompleteListener(DrLoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.

                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        if (!isPasswordValid(Doctor_Password)) {
                                            Toast.makeText(DrLoginActivity.this, "Password is too short", Toast.LENGTH_LONG).show();
                                        } else if (!isEmailValid(Doctor_Email)) {
                                            Toast.makeText(DrLoginActivity.this, "This email address is invalid", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(DrLoginActivity.this, "Authentication failed" + task.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Intent intent = new Intent(DrLoginActivity.this, DrDetailsActivity.class);
                                        intent.putExtra("Dr name",Doctor_Email);
                                        intent.putExtra("Dr password",Doctor_Password);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                    }

                    /*HashMap<String, String> dataMap = new HashMap<>();
                    dataMap.put("Doctor Name",Doctor_Email);
                    dataMap.put("password", Doctor_Password);

                    table_doctor.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(DrLoginActivity.this, "Sign up successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DrLoginActivity.this, DrDetailsActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(DrLoginActivity.this, "Error...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });*/

                    Intent intent = new Intent(DrLoginActivity.this, DrDetailsActivity.class);
                    startActivity(intent);
                    finish();

            }
        });
    }

   /* public void GoDrDetails(View view)
    {
        Intent intent= new Intent(DrLoginActivity.this , DrDetailsActivity.class);
        startActivity(intent);
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }*/
   private boolean isEmailValid(String getEmailId) { return (getEmailId.contains("@") && getEmailId.contains("."));}

    private boolean isPasswordValid(String password) { return password.length() > 4; }


}