package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


public class MainActivity12 extends AppCompatActivity {


    private String medic_FileId, medic_nationalId, medic_fullName, medic_bloodType;
    private String P_firstName, P_LastName, P_phoneNumber;
    private String H_phoneNumber, H_massage1, H_massage2, H_massage3;
    private double Longitude = 0.0;
    private double Latitude  = 0.0;
    private LocationManager locationManager;

    private String locationLink = "https://www.google.com/maps/dir/?api=1&origin=";


    private Button Send;
    private TextView welcomeMsg;


    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;


    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // for all initializations
        welcomeMsg=findViewById(R.id.userName);
        Ins();


        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);


        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                if (count == 1) {
                    sendSMSMessage(count, H_massage1);
                } else if (count == 2) {
                    sendSMSMessage(count, H_massage2);

                } else if (count == 3) {
                    sendSMSMessage(count, H_massage3);
                }


            }
        });

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage(H_phoneNumber, null, H_massage1, null, null);
//                    Toast.makeText(getApplicationContext(), "SMS hjghgjhghgjgjhghgjhghgjhjhsent.",
//                            Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getApplicationContext(),
//                            "SMS faild, please try againkjbkjhhkjhkjhkjkjhkjhkjkjhjkhkjhkjh.", Toast.LENGTH_LONG).show();
//                }
//            }
//        }
//    }

    protected void sendSMSMessage(int c, String massage) {

        SmsManager smsManager = SmsManager.getDefault();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation

            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(MainActivity12.this , new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION,Manifest.permission.SEND_SMS,Manifest.permission.WAKE_LOCK},MY_PERMISSIONS_REQUEST_SEND_SMS);

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Longitude = location.getLongitude();
                Latitude = location.getLatitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });

        try {
            smsManager.sendTextMessage(H_phoneNumber, null,  massage + "\n  My Location is : \n"+ locationLink  + Longitude + "," + Latitude    + "     \n emergency level : " + c, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        }catch (Exception e){

            Log.d("SMS Failed" , Objects.requireNonNull(e.getMessage()));
        }

    }

    private void Ins(){

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert mSensorManager != null;
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // getting Permissions
        if (ContextCompat.checkSelfPermission(MainActivity12.this , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity12.this , new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION,Manifest.permission.SEND_SMS,Manifest.permission.WAKE_LOCK},MY_PERMISSIONS_REQUEST_SEND_SMS);

        }




        try {

            H_phoneNumber = getIntent().getStringExtra("aPhonenum");

            H_massage1 = getIntent().getStringExtra("aMsg1");
            H_massage2 = getIntent().getStringExtra("aMsg2");
            H_massage3 = getIntent().getStringExtra("aMsg3");

            medic_fullName = getIntent().getStringExtra("hfullname");
            medic_FileId = getIntent().getStringExtra("hmfile");
            medic_nationalId = getIntent().getStringExtra("hnid");
            medic_bloodType = getIntent().getStringExtra("hbloodtype");

            P_firstName = getIntent().getStringExtra("pfirstname");
            P_LastName = getIntent().getStringExtra("plastname");
            P_phoneNumber = getIntent().getStringExtra("pphonenum");


            welcomeMsg.setText("Welcome "+P_firstName);

            Log.d("UserInfo" ,  "H_phoneNumber : " + H_phoneNumber);

            Log.d("UserInfo" ,  "medic_fullName : " + medic_fullName);

            Log.d("UserInfo" ,  "P_firstName : " + P_firstName);
        }catch (Exception ex){


            Log.d("Exception : " , Objects.requireNonNull(ex.getMessage()));
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume

    }
    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

}
