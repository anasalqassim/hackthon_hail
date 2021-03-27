package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class MainActivity12 extends AppCompatActivity {


    private String medic_FileId, medic_nationalId, medic_fullName, medic_bloodType;
    private String P_firstName, P_LastName, P_phoneNumber;
    private String H_phoneNumber, H_massage1, H_massage2, H_massage3;
    private double Longitude;
    private double Latitude;
    private LocationManager locationManager;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private String locationLink = "https://www.google.com/maps/dir/?api=1&origin=";


    private Button Send;
    private ImageView profileImage;
    private TextView welcome ,statemsg1,statemsg2;
    private ConstraintLayout state;
    private LocationCallback locationCallback;

    private Bitmap data;
    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private FusedLocationProviderClient fusedLocationClient;





    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // for all initializations
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference  = firebaseDatabase.getReference("userData");

        databaseReference.child("anas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                H_phoneNumber = (String) snapshot.child("aPhonenum").getValue();

                H_massage1 =  snapshot.child("aMsg1").getValue().toString();
                H_massage2 =  snapshot.child("aMsg2").getValue().toString();
                H_massage3 =  snapshot.child("aMsg3").getValue().toString();

                medic_fullName =  snapshot.child("hfullname").getValue().toString();
                medic_FileId =  snapshot.child("hmfile").getValue().toString();
                medic_nationalId =  snapshot.child("hnid").getValue().toString();
                medic_bloodType =  snapshot.child("hbloodtype").getValue().toString();

                P_firstName = snapshot.child("pfirstname").getValue().toString();
                P_LastName =snapshot.child("plastname").getValue().toString();
                P_phoneNumber = snapshot.child("pphonenum").getValue().toString();

             //   data = (String) snapshot.child("pprofileimg").getValue();
                Toast.makeText(getApplicationContext(),P_firstName , Toast.LENGTH_LONG).show();


                state=findViewById(R.id.stateLayout);
                statemsg1=findViewById(R.id.state);
                statemsg2=findViewById(R.id.state2);
                welcome=findViewById(R.id.userName);

                if(H_phoneNumber.isEmpty() && medic_fullName.isEmpty() && medic_FileId.isEmpty() && medic_nationalId.isEmpty() && medic_bloodType.isEmpty() &&
                   P_firstName.isEmpty() && P_LastName.isEmpty() && P_phoneNumber.isEmpty()){

                    state.setBackgroundResource(R.drawable.missing_info);
                    statemsg1.setText("You have a missing information!");
                    statemsg2.setText("");//empty msg


                }else{
                    state.setBackgroundResource(R.drawable.good_state);
                    statemsg1.setText("Every thing is okay");
                    statemsg2.setText("your helper phone number: "+H_phoneNumber);//empty msg
                    welcome.setText("Welcome "+P_firstName);

                }
            }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(MainActivity12.this , new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.SEND_SMS,Manifest.permission.WAKE_LOCK},MY_PERMISSIONS_REQUEST_SEND_SMS);

        }
        fusedLocationClient.getLastLocation().addOnCompleteListener(this
                , new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        location = task.getResult();

                        Longitude = location.getLongitude();
                        Latitude = location.getLatitude();


                    }
                });


        Ins();


        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                if (count == 1) {
                    sendSMSMessage(count, H_massage1);
                    Toast.makeText(getApplicationContext(),"shake!!" ,Toast.LENGTH_LONG).show();
                } else if (count == 2) {
                    sendSMSMessage(count, H_massage2);

                } else if (count == 3) {
                    sendSMSMessage(count, H_massage3);
                }


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {



        }
    }

    protected void sendSMSMessage(int c, String massage) {

        SmsManager smsManager = SmsManager.getDefault();
        ActivityCompat.requestPermissions(MainActivity12.this , new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.SEND_SMS,Manifest.permission.WAKE_LOCK},MY_PERMISSIONS_REQUEST_SEND_SMS);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(MainActivity12.this , new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.SEND_SMS,Manifest.permission.WAKE_LOCK},MY_PERMISSIONS_REQUEST_SEND_SMS);


        }


        try {
            if (Longitude != 0.0) {
                smsManager.sendTextMessage(H_phoneNumber, null, massage + "\n  My Location is : \n" + locationLink + Latitude + "," + Longitude + "     \n emergency level : " + c, null, null);
                Toast.makeText(getApplicationContext(), "SMS sent.",
                        Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "^_^ hhhhhhhh", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){

            Log.d("SMS Failed" , Objects.requireNonNull(e.getMessage()));
        }

    }

    private void Ins(){

        profileImage = findViewById(R.id.edit_profile_image_border);
       // Welcome = findViewById(R.id.us)

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert mSensorManager != null;
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // getting Permissions

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity12.this , new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.SEND_SMS,Manifest.permission.WAKE_LOCK},MY_PERMISSIONS_REQUEST_SEND_SMS);

        }




        try {



            data = getIntent().getParcelableExtra("pprofileimg");
            if(data != null){
                profileImage.setImageBitmap(data);
            }

//            Log.d("UserInfo" ,  "H_phoneNumber : " + H_phoneNumber);
//
//            Log.d("UserInfo" ,  "medic_fullName : " + medic_fullName);
//
//            Log.d("UserInfo" ,  "P_firstName : " + P_firstName);
        }catch (Exception ex){


            Log.d("Exception : " , Objects.requireNonNull(ex.getMessage()));
        }



    }



}
