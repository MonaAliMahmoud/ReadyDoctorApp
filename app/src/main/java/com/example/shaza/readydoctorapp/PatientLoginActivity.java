package com.example.shaza.readydoctorapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PatientLoginActivity extends AppCompatActivity {

    //Database
   private FirebaseAuth mAuth;
   private FirebaseAuth.AuthStateListener mAuthListener;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    public View mLoginFormView;
    public Button mSignIn;
    public Button mSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView)findViewById(R.id.Patient_Email);
        mPasswordView = (EditText) findViewById(R.id.Patient_Password);
        mLoginFormView =(View) findViewById(R.id.email_login_form);
        mSignIn = (Button)findViewById(R.id.sign_in_button);
        mSignUp = (Button)findViewById(R.id.sign_up_button);

        mAuth = FirebaseAuth.getInstance();
        /*FirebaseUser User = mAuth.getCurrentUser();

        if (User != null) {
            finish();
            startActivity(new Intent(PatientLoginActivity.this, AnatomyActivity.class));
        }*/

        mSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                final String PatientEmail = mEmailView.getText().toString();
                final String PatientPassword = mPasswordView.getText().toString();

                if (TextUtils.isEmpty(PatientEmail) && TextUtils.isEmpty(PatientPassword)) {
                    Toast.makeText(getApplicationContext(), "Enter email address & Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (TextUtils.isEmpty(PatientEmail)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (TextUtils.isEmpty(PatientPassword)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(PatientEmail, PatientPassword)
                        .addOnCompleteListener(PatientLoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (!isPasswordValid(PatientPassword)) {
                                        Toast.makeText(PatientLoginActivity.this, "Password is too short", Toast.LENGTH_LONG).show();
                                    } else if (!isEmailValid(PatientEmail)) {
                                        Toast.makeText(PatientLoginActivity.this, "This email address is invalid", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(PatientLoginActivity.this, "Authentication failed" + task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Intent intent = new Intent(PatientLoginActivity.this, AnatomyActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(PatientLoginActivity.this , SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /*@Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }*/

    /*private void Validate (String )*/

    /*public void GoToAnatomy(View view)
    {
        Intent intent= new Intent(PatientLoginActivity.this , SignUpActivity.class);
        startActivity(intent);
    }*/

    /*public void GoToSignUp(View view)
    {
        Intent intent= new Intent(PatientLoginActivity.this , SignUpActivity.class);
        startActivity(intent);
    }*/

    private boolean isEmailValid(String getEmailId) { return (getEmailId.contains("@") && getEmailId.contains("."));}

    private boolean isPasswordValid(String password) { return password.length() > 4; }

    /*private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }*/
}
