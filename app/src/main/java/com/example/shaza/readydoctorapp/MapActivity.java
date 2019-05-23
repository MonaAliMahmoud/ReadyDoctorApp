package com.example.shaza.readydoctorapp;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.shaza.readydoctorapp.Model.Doctor;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by shaza on 18/05/2018.
 */

public class MapActivity extends AppCompatActivity implements LocationListener,
        GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    //variables
    private Boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    //Firebase
    //private FirebaseDatabase tracker;

    private DatabaseReference table_doctor;

    //  FirebaseDatabase databaseLocation = FirebaseDatabase.getInstance();
    // DatabaseReference table_doctor = databaseLocation.getReference("https://readydoctorapp-204411.firebaseio.com/doctor");

    //String of Specialization
    //String Specialize = getIntent().getStringExtra("Lung");
    String Specialize;
    //position of Patient
    public double PatientLong;
    public double PatientLat;

    //position of doctor
    public double DoctorLong;
    public double DoctorLat;

    //output of Euclidean
    public double Euclidean;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);

        getLocationPermission();
        Bundle bd = getIntent().getExtras();
        Specialize = bd.getString("Lung");
        //Specialize=intent.getStringExtra("Lung");
        Toast.makeText(MapActivity.this, "" + Specialize, Toast.LENGTH_LONG).show();

       /* tracker = FirebaseDatabase.getInstance();
        table_doctor = tracker.getReference("doctor");*/
    }

    private void init() {
        Log.d(TAG, "init: initializing");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;
        googleApiClient = new GoogleApiClient.Builder(MapActivity.this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();
        googleApiClient.connect();
        // mMap.setOnInfoWindowClickListener(this);

        if (mLocationPermissionGranted) {
//            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.setBuildingsEnabled(true);
            init();
            initializeFireBase();
            //addMarkers();
        }
    }

    private void initializeFireBase() {
       // table_doctor = FirebaseDatabase.getInstance().getReference().child("doctor");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/doctor");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(MapActivity.this, "Success", Toast.LENGTH_LONG).show();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Doctor Doctors = child.getValue(Doctor.class);
                    assert Doctors != null;
                    //   DoctorLat = (double) dataSnapshot.child("latitude").getValue();
                    //   DoctorLong = (double) dataSnapshot.child("longitude").getValue();
                    System.out.print(Doctors.getSpecialization());
                    if (Doctors.getSpecialization().equals(Specialize)) {
                        // Euclidean = getEuclidean(PatientLat,PatientLong,DoctorLat,DoctorLong);
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Doctors.getLatitude(), Doctors.getLongitude()))
                                .title(Doctors.getName())
                                .snippet("Fees: ,Rate: " + Doctors.getFees() + Doctors.getRate())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                // Log.e("The read failed: ", databaseError.getMessage());
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM);
        mMap.moveCamera(update);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest locationRequest = LocationRequest.create();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    //Get My current Location
  /*private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM, "My Location");

                            //for eucledian
                            PatientLong = currentLocation.getLongitude();
                            PatientLat = currentLocation.getLatitude();

                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
  }*/

    //Enable to move and zoom in/out into the map
    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet("Here you are");
        mMap.addMarker(options);
        //hideSoftKeyboard();
    }

    //create Map

    private void initMap() {
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting Location Permissions");
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permission, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permission, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called");
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }
   /* private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }*/

    //Get Marker data

    /*private void addMarkers(){

        mMap.addMarker(new MarkerOptions().position(new LatLng(31.24123188,29.99163933))
                .title("Dr. Mahmoud Ali")
                .snippet("Fees:100 LE, Rate:4")
                //.snippet("Tap to send request to the doctor")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        mMap.addMarker(new MarkerOptions().position(new LatLng(31.23993847,29.99486871))
                .title("Dr. Ahmed Galal")
                .snippet("Fees:150 LE, Rate:3.5")
                //.snippet("Tap to send request to the doctor")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        mMap.addMarker(new MarkerOptions().position(new LatLng(31.23819555,29.99131747))
                .title("Dr. Moaz Mohammed")
                .snippet("Fees:250 LE, Rate:4.5")
                //.snippet("Tap to send request to the doctor")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        mMap.addMarker(new MarkerOptions().position(new LatLng(31.23806712,29.99214359))
                .title("Dr. Omar Ali")
                .snippet("Fees:75 LE, Rate:3")
                //.snippet("Tap to send request to the doctor")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        mMap.addMarker(new MarkerOptions().position(new LatLng(31.23904089,29.95509333))
                .title("Dr. Moaz Mohammed")
                .snippet("Fees:250 LE, Rate:4.5")
                //.snippet("Tap to send request to the doctor")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        mMap.addMarker(new MarkerOptions().position(new LatLng(31.23779332,29.95406336))
                .title("Dr. Omar Ali")
                .snippet("Fees:75 LE, Rate:3")
                //.snippet("Tap to send request to the doctor")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
    }*/

    @Override
    public void onInfoWindowClick(Marker marker) {

        Intent intent = new Intent(MapActivity.this, RequestActivity.class);
        startActivity(intent);
        finish();
    }

    public double getEuclidean(double a, double b, double c, double d) {
        double lat = a - c;
        double lon = b - d;
        double euclidean = Math.sqrt(Math.pow(lat, 2) + Math.pow(lon, 2));
        return euclidean;
    }
}
