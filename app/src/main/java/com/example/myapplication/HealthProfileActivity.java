package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class HealthProfileActivity extends AppCompatActivity {

    EditText NationalID,FullName,MFile;
    RadioGroup BloodType;
    Button hSave;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private String medic_FileId, medic_nationalId, medic_fullName, medic_bloodType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_profile);
        NationalID=findViewById(R.id.NIDNew);
        FullName=findViewById(R.id.FullNameNew);
        MFile=findViewById(R.id.MedicalFileNew);
        BloodType =findViewById(R.id.radioNew);
        hSave=findViewById(R.id.SaveHealthProfileNew);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference  = firebaseDatabase.getReference("userData");
        hSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthProfileActivity.this ,MainActivity12.class);
                medic_nationalId= NationalID.getText().toString();
                medic_fullName=FullName.getText().toString();
                medic_FileId=MFile.getText().toString();

                int radioID=BloodType.getCheckedRadioButtonId();
                RadioButton radioButton=findViewById(radioID);
                medic_bloodType = radioButton.getText().toString();




                firebaseDatabase.getReference("userData/anas/hnid").setValue(medic_nationalId);
                firebaseDatabase.getReference("userData/anas/hfullname").setValue(medic_fullName);
                firebaseDatabase.getReference("userData/anas/hmfile").setValue(medic_FileId);
                firebaseDatabase.getReference("userData/anas/hbloodtype").setValue(medic_bloodType);


                startActivity(intent);
            }
        });

    }
}