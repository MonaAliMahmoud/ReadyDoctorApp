package com.example.shaza.readydoctorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shaza.readydoctorapp.Model.Doctor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequestActivity extends AppCompatActivity {

    public TextView DrName;
    public TextView Rate;
    public TextView Fees;
    public TextView Specialization;
    public TextView DrPhone;
    public TextView RequestStatus;

    public DatabaseReference table_doctor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        DrName = findViewById(R.id.drName);
        Rate = findViewById(R.id.rate);
        Fees = findViewById(R.id.fees);
        Specialization = findViewById(R.id.specialization);
        DrPhone = findViewById(R.id.drPhone);
        RequestStatus = findViewById(R.id.requestStatus);

        table_doctor = FirebaseDatabase.getInstance().getReference("/doctor");

       /* table_doctor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(RequestActivity.this, "Success", Toast.LENGTH_LONG).show();

                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    Doctor doctors = s.getValue(Doctor.class);
                    String Key = s.getKey();

                    Toast.makeText(RequestActivity.this, ""+Key, Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                 Log.e("The read failed: ", databaseError.getMessage());
            }
        });*/
    }

}
