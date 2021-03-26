package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;

public class MenuActivity extends AppCompatActivity {
     String FName,LName,phonenum,nationalid,fullname,mfile,bloodtype;
     Bitmap b;
     private boolean i=false;
    private ImageView profileimg;
    Uri selectPhotoUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        //3 Relative Layout (GONE)
        final ConstraintLayout PersonalInformation =findViewById(R.id.PersonalInfo);
        final ConstraintLayout HealthProfile =findViewById(R.id.HealthProfile);
        final ConstraintLayout AssistantInfo =findViewById(R.id.assistantInfo);

        //Personal Information Layout variables declaration :
        final EditText FirstName=findViewById(R.id.FirstName);
        final EditText LastName=findViewById(R.id.LastName);
        final EditText PhoneNo=findViewById(R.id.PhoneNo);
        profileimg  =findViewById(R.id.profile_image);
        Button pSave=findViewById(R.id.SavePersonalInfo);


        //Health Profile Layout variables declaration :
        final EditText NationalID=findViewById(R.id.NID);
        final EditText FullName=findViewById(R.id.FullName);
        final EditText MFile=findViewById(R.id.MedicalFile);
        final RadioGroup BloodType =findViewById(R.id.radio);
        Button hSave=findViewById(R.id.SaveHealthProfile);


        //Assistant Info Layout variables declaration :
        final EditText PhoneNoA=findViewById(R.id.phoneNoo);
        final EditText message1=findViewById(R.id.M1);
        final EditText message2=findViewById(R.id.M2);
        final EditText message3=findViewById(R.id.M3);
        Button aSave=findViewById(R.id.SaveAssistantInfo);




        //data passed from main activity to decide which relative layout to show:
        try{
        String intent =  getIntent().getStringExtra("key");
        switch (intent){
            case "InfoC" :PersonalInformation.setVisibility(View.VISIBLE); ; break;
            case "MedcK" :HealthProfile.setVisibility(View.VISIBLE);break;
            case "HelpK" :AssistantInfo.setVisibility(View.VISIBLE);break;
        }}
        catch (Exception e){e.printStackTrace();}




        //
        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1);
            }
        });


        //
        pSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FName= FirstName.getText().toString();
                LName =LastName.getText().toString();
                phonenum= PhoneNo.getText().toString();
                profileimg.setDrawingCacheEnabled(true);
                b=profileimg.getDrawingCache();


               PersonalInformation.setVisibility(View.GONE);
               HealthProfile.setVisibility(View.VISIBLE);
               AssistantInfo.setVisibility(View.GONE);}


        });

        //
        hSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nationalid= NationalID.getText().toString();
                fullname=FullName.getText().toString();
                mfile=MFile.getText().toString();
                int radioID=BloodType.getCheckedRadioButtonId();
                RadioButton radioButton=findViewById(radioID);
                bloodtype= radioButton.getText().toString();

                PersonalInformation.setVisibility(View.GONE);
                HealthProfile.setVisibility(View.GONE);
                AssistantInfo.setVisibility(View.VISIBLE);

            }
        });


        //
        aSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(MenuActivity.this,MainActivity12.class);


                intent.putExtra("pfirstname",FName);
                intent.putExtra("plastname",LName);
                intent.putExtra("pphonenum",phonenum);
                intent.putExtra("pprofileimg", b);

                intent.putExtra("hnid",nationalid);
                intent.putExtra("hfullname",fullname);
                intent.putExtra("hmfile",mfile);
                intent.putExtra("hbloodtype",bloodtype);



                String pha = PhoneNoA.getText().toString();
                intent.putExtra("aPhonenum",pha);

                intent.putExtra("aMsg1",message1.getText().toString());
                intent.putExtra("aMsg2",message2.getText().toString());
                intent.putExtra("aMsg3",message3.getText().toString());


                PersonalInformation.setVisibility(View.GONE);
                HealthProfile.setVisibility(View.GONE);
                AssistantInfo.setVisibility(View.GONE);

                startActivity(intent);
                }


            });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode== Activity.RESULT_OK && data != null){

             selectPhotoUri = data.getData();



            try {
                b = MediaStore.Images.Media.getBitmap(getContentResolver(), selectPhotoUri);
                profileimg.setImageBitmap(b);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }
}