package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PersonalInfoActivity extends AppCompatActivity {

    Button pSave;
    String FName,LName,phonenum;
    EditText FirstName,LastName,PhoneNo;
    ImageView profileimg;
    Bitmap b;
    HashMap<String,String> userMap = new HashMap<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        pSave=findViewById(R.id.SavePersonalInfoNew);
        LastName=findViewById(R.id.LastNameNew);
        FirstName=findViewById(R.id.FirstNameNew);
        PhoneNo=findViewById(R.id.PhoneNoNew);
        profileimg=findViewById(R.id.profile_imageNew);


        //database:
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference  = firebaseDatabase.getReference("userData");




        pSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInfoActivity.this , MainActivity12.class);
                FName= FirstName.getText().toString();
                LName =LastName.getText().toString();
                phonenum= PhoneNo.getText().toString();
                profileimg.setDrawingCacheEnabled(true);
                b=profileimg.getDrawingCache();

                if (phonenum.length() != 10){
                    Toast.makeText(getApplicationContext(), " Write a valid number please" , Toast.LENGTH_LONG).show();

                }else if(phonenum.charAt(0) == '0'){
                    phonenum  = "+966" +  phonenum.substring(1);




                    firebaseDatabase.getReference("userData/anas/plastname").setValue(LName);
                    firebaseDatabase.getReference("userData/anas/pphonenum").setValue(phonenum);
                    firebaseDatabase.getReference("userData/anas/pfirstname").setValue(FName);





                    startActivity(intent);
                }


            }
        });



    }
}