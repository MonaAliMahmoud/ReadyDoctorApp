package com.example.shaza.readydoctorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    public Button btnPatient;
    public Button btnDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnPatient = (Button) findViewById(R.id.patient);
        btnDoctor = (Button) findViewById(R.id.doctor);

        btnPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(WelcomeActivity.this , PatientLoginActivity.class);
                startActivity(intent);
            }
        });

        btnDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(WelcomeActivity.this , DrLoginActivity.class);
                startActivity(intent);
            }
        });
    }
    /*public void GoToDrLogin(View view)
    {
        Intent intent= new Intent(WelcomeActivity.this , DrLoginActivity.class);
        startActivity(intent);
    }
    public void GoToPatientLogin(View view)
    {
        Intent intent= new Intent(WelcomeActivity.this , PatientLoginActivity.class);
        startActivity(intent);
    }*/
}
