package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.time.Instant;

public class MainActivity12 extends AppCompatActivity  {


    EditText Ph,Nm;
    LinearLayout A1,A2;
    private  String phoneNo,nm;

    private Button Send;


    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

   private static   String message1 = "I'm not in in hurry but can you please if you are free come to help me with something ";

   private static String message2 = " I'm in wired situation Im not sure but if you are free come to me please  ";

   private static String message3 = "!!!  Im in very Dangers situation come and help me please ";


    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // for all initializations
        Ins();

        String intent =  getIntent().getStringExtra(key);




        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);

                A1.setVisibility(View.GONE);
                A2.setVisibility(View.VISIBLE);
            }
        });

        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                if (count == 1) {
                    sendSMSMessage(count,message1);
                }else if(count == 2){
                    sendSMSMessage(count,message2);

                }else if (count == 3){
                    sendSMSMessage(count,message3);
                }


            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message1, null, null);
                    Toast.makeText(getApplicationContext(), "SMS hjghgjhghgjgjhghgjhghgjhjhsent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try againkjbkjhhkjhkjhkjkjhkjhkjkjhjkhkjhkjh.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }

    protected void sendSMSMessage(int c,String massge) {

        Nm = findViewById(R.id.name);

        nm = Nm.getText().toString();


        Ph = (EditText) findViewById(R.id.name2);

        phoneNo = Ph.getText().toString();
        SmsManager smsManager = SmsManager.getDefault();

        try {
            smsManager.sendTextMessage(phoneNo, null, "Hello : "+  nm + "\n"+ massge + " \n emergency level : " + c, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "SMS Failed " +e,
                    Toast.LENGTH_LONG).show();
        }

    }

    private void Ins(){


        A1 = findViewById(R.id.A1);
        A2 = findViewById(R.id.A2);

        Send = findViewById(R.id.AssisstnitInfo);


        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert mSensorManager != null;
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();



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
