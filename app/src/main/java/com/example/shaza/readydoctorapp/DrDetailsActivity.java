package com.example.shaza.readydoctorapp;

import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.shaza.readydoctorapp.Model.Doctor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DrDetailsActivity extends AppCompatActivity {

    private static final String TAG ="DrDetailsActivity";

    public TextView DrName;
    public TextView Rate;
    public TextView Specialization;
    public TextView DrPhone;

    String Doctor_Email;
    String Doctor_Password;

    //private ExpandableListView mExpandableListView;

    //Firebase
    //private FirebaseDatabase mFirebaseDatabase;
    //private DatabaseReference table_doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_details);

        DrName = findViewById(R.id.drName);
        Rate = findViewById(R.id.rate);
        Specialization = findViewById(R.id.specialization);
        DrPhone = findViewById(R.id.drPhone);

        String drName = DrName.getText().toString();
        String specialization = Specialization.getText().toString();
        String drPhone = DrPhone.getText().toString();
        float rate = Float.parseFloat(Rate.getText().toString());

        Bundle email = getIntent().getExtras();
        Doctor_Email = email.getString("name");

        Bundle pass = getIntent().getExtras();
        Doctor_Password = pass.getString("password");

        showInfo();

        //mFirebaseDatabase = FirebaseDatabase.getInstance();
        //table_doctor = FirebaseDatabase.getInstance().getReference().child("doctor");

        //showInfo(dataSnapshot);

       /* table_doctor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showInfo();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

    }

    private void showInfo() {


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/doctor");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Doctor Doctors = ds.getValue(Doctor.class);
                    assert Doctors != null;
                    if (Doctors.getName().equals(Doctor_Email) && Doctors.getPassword().equals(Doctor_Password)){
                        Log.v(TAG, "Show data: Name: " + Doctors.getName());
                        Log.v(TAG, "Show data: Specialization: " + Doctors.getSpecialization());
                        Log.v(TAG, "Show data: Rate: " + Doctors.getPhone());
                    }

                    //   DoctorLong = (double) dataSnapshot.child("longitude").getValue();

                  //  Log.v(TAG, "Show data: Fees: " + Doctors.getFees());
                  //  Log.v(TAG, "Show data: Latitude: " + Doctors.getLatitude());

                   }
                }
                @Override
                public void onCancelled (@NonNull DatabaseError databaseError){

            }
        });
    }
}
