package com.example.shaza.readydoctorapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class SignUpActivity extends AppCompatActivity {

    private AutoCompleteTextView PatientEmail, UserName, PhoneNumber, Password, ConfirmPassword;
    public Button SignUp;

    //Database
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference table_patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        UserName = (AutoCompleteTextView) findViewById(R.id.username);
        PatientEmail = (AutoCompleteTextView) findViewById(R.id.Patient_Email);
        PhoneNumber = (AutoCompleteTextView) findViewById(R.id.phonenumber);
        Password = (AutoCompleteTextView) findViewById(R.id.Patient_Password);
        ConfirmPassword = (AutoCompleteTextView) findViewById(R.id.confirmpassword);
        SignUp = (Button) findViewById(R.id.Signup);

        mAuth = FirebaseAuth.getInstance();
        table_patient = FirebaseDatabase.getInstance().getReference().child("patient");

        final String getEmailId = PatientEmail.getText().toString();
        final String getPassword = Password.getText().toString();

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get all edit text texts
                String getUserName = UserName.getText().toString();
                final String getEmailId = PatientEmail.getText().toString();
                String getMobileNumber = PhoneNumber.getText().toString();
                final String getPassword = Password.getText().toString();
                String getConfirmPassword = ConfirmPassword.getText().toString();

                // Check if all strings are null or not
                if (!TextUtils.isEmpty(getUserName)
                        && !TextUtils.isEmpty(getEmailId)
                        && !TextUtils.isEmpty(getMobileNumber)
                        && !TextUtils.isEmpty(getPassword)
                        && !TextUtils.isEmpty(getConfirmPassword)) {

                    if (getPassword.equals(getConfirmPassword) && isEmailValid(getEmailId) && isPasswordValid(getPassword)) {
                        //Store object to the database
                        // string for the key & string for the value
                        HashMap<String, String> dataMap = new HashMap<>();
                        dataMap.put("Patient name", getUserName);
                        dataMap.put("Email", getEmailId);
                        dataMap.put("Password", getPassword);
                        dataMap.put("Phone", getMobileNumber);

                        mAuth.createUserWithEmailAndPassword(getEmailId, getPassword).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "", Toast.LENGTH_SHORT).show();
                                    /*Intent intent = new Intent(SignUpActivity.this, AnatomyActivity.class);
                                    startActivity(intent);
                                    finish();*/
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Authentication failed" + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        table_patient.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Sign up successfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpActivity.this, AnatomyActivity.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(SignUpActivity.this, "Error...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else if (!getPassword.equals(getConfirmPassword)) {
                        Toast.makeText(SignUpActivity.this, "Confirm password & password field doesn't match", Toast.LENGTH_LONG).show();
                    } else if (!isEmailValid(getEmailId)) {
                        Toast.makeText(SignUpActivity.this, "This email address is invalid", Toast.LENGTH_LONG).show();
                    } else if (!isPasswordValid(getPassword)) {
                        Toast.makeText(SignUpActivity.this, "Password is too short", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "All fields are required", Toast.LENGTH_LONG).show();
                }

                /*mAuth.createUserWithEmailAndPassword(getEmailId, getPassword).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(SignUpActivity.this, AnatomyActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Authentication failed" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private boolean isEmailValid(String getEmailId) { return (getEmailId.contains("@") && getEmailId.contains("."));}

    private boolean isPasswordValid(String password) { return password.length() > 4; }

    /*private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }*/
}
