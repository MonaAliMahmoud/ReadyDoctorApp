package com.example.shaza.readydoctorapp;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.ConnectionResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AnatomyActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "AnatomyActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private DatabaseReference table_doctor;

    public Button mSignOut;
    public String specialization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anatomy);

        Button btnLung = (Button) findViewById(R.id.Lung);
        Button btnKidney = (Button) findViewById(R.id.Kidney);
        Button btnEar = (Button) findViewById(R.id.Ear);
        Button btnLiver = (Button) findViewById(R.id.Liver);
        Button btnStomach = (Button) findViewById(R.id.Stomach);

        mSignOut = (Button) findViewById(R.id.SignOut);

        table_doctor = FirebaseDatabase.getInstance().getReference().child("doctor");


        if (isServicesOk()){

            btnLung.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AnatomyActivity.this, MapActivity.class);
                    specialization="Lung";
                    intent.putExtra("Lung",specialization);
                    startActivity(intent);
                }
            });

            btnKidney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AnatomyActivity.this, MapActivity.class);
                    specialization="Kidney";
                    intent.putExtra("Kidney",specialization);
                    startActivity(intent);
                }
            });

            btnEar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AnatomyActivity.this, MapActivity.class);
                    specialization="Ear";
                    intent.putExtra("Ear",specialization);
                    startActivity(intent);
                }
            });

            btnLiver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AnatomyActivity.this, MapActivity.class);
                    specialization="Liver";
                    intent.putExtra("Liver",specialization);
                    startActivity(intent);
                }
            });

            btnStomach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AnatomyActivity.this, MapActivity.class);
                    specialization="Stomach";
                    intent.putExtra("Stomach",specialization);
                    startActivity(intent);
                }
            });
        }

        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnatomyActivity.this, RequestActivity.class);
                startActivity(intent);
            }
        });
    }

    private void doctorInfo(){

    }

    public boolean isServicesOk(){
        Log.d(TAG, "isServicesOk : checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(AnatomyActivity.this);

        if (available == ConnectionResult.SUCCESS)
        {
            //Every thing is fine and the user can make map requests
            Log.d(TAG, "isServicesOk : checking google services version is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            //An error occurred but we can fix it
            Log.d(TAG, "isServicesOk : an error occurred but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(AnatomyActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Toast.makeText(this, "you can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    /*public void GoToSignIn(View view)
    {
        Intent intent = new Intent(AnatomyActivity.this, PatientLoginActivity.class);
        startActivity(intent);
    }*/
}

