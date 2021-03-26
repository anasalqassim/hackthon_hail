package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.SensorManager;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        //3 Relative Layout (GONE)
        RelativeLayout PersonalInformation =findViewById(R.id.PersonalInfo);
        RelativeLayout HealthProfile =findViewById(R.id.HealthProfile);
        RelativeLayout AssistantInfo =findViewById(R.id.assistantInfo);

        //Personal Information Layout variables declaration :
        final EditText FirstName=findViewById(R.id.FirstName);
        final EditText LastName=findViewById(R.id.LastName);
        final EditText PhoneNo=findViewById(R.id.PhoneNo);
        final ImageView profileimg=findViewById(R.id.profile_image);
        Button pSave=findViewById(R.id.SavePersonalInfo);


        //Health Profile Layout variables declaration :
        final EditText NationalID=findViewById(R.id.NID);
        final EditText FullName=findViewById(R.id.FullName);
        final EditText MFile=findViewById(R.id.MedicalFile);
        final RadioGroup BloodType =findViewById(R.id.radio);
        Button hSave=findViewById(R.id.SaveHealthProfile);


        //Assistant Info Layout variables declaration :
        final EditText PhoneNoA=findViewById(R.id.NID);
        final EditText message1=findViewById(R.id.M1);
        final EditText message2=findViewById(R.id.M2);
        final EditText message3=findViewById(R.id.M3);
        Button aSave=findViewById(R.id.SaveAssistantInfo);




        //data passed from main activity to decide which relative layout to show:
        try{
        String intent =  getIntent().getStringExtra("key");
        switch (intent){
            case "InfoC" :PersonalInformation.setVisibility(View.VISIBLE);break;
            case "MedcK" :HealthProfile.setVisibility(View.VISIBLE);break;
            case "HelpK" :AssistantInfo.setVisibility(View.VISIBLE);break;
        }}
        catch (Exception e){e.printStackTrace();}




        //
        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,10000);
            }
        });


        //
        pSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MenuActivity.this,MainActivity12.class);
                intent.putExtra("pfirstname",FirstName.getText());
                intent.putExtra("plastname",LastName.getText());
                intent.putExtra("pphonenum",PhoneNo.getText());
                profileimg.setDrawingCacheEnabled(true);
                Bitmap b=profileimg.getDrawingCache();
                intent.putExtra("pprofileimg", b);

                startActivity(intent);
            }
        });

        //
        hSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MenuActivity.this,MainActivity12.class);
                intent.putExtra("hnid",NationalID.getText());
                intent.putExtra("hfullname",FullName.getText());
                intent.putExtra("hmfile",MFile.getText());
                int radioID=BloodType.getCheckedRadioButtonId();
                RadioButton radioButton=findViewById(radioID);
                intent.putExtra("hbloodtype",radioButton.getText());

                startActivity(intent);
            }
        });


        //
        aSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MenuActivity.this,MainActivity12.class);
                intent.putExtra("aphonenum",PhoneNoA.getText());
                intent.putExtra("amsg1",message1.getText());
                intent.putExtra("amsg2",message2.getText());
                intent.putExtra("amsg3",message3.getText());

                startActivity(intent);
            }
        });

    }
}