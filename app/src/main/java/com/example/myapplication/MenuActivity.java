package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class MenuActivity extends AppCompatActivity {
     String FName,LName,phonenum,nationalid,fullname,mfile,bloodtype;
     Bitmap b;
     private boolean i=false;
    private ImageView profileimg;
    Uri selectPhotoUri;
    String intent2="";
    EditText FirstName,message3,LastName,PhoneNo,NationalID,FullName,MFile,PhoneNoA,message1,message2;



    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    HashMap<String,String> userMap = new HashMap<>();

    ConstraintLayout PersonalInformation;
    ConstraintLayout HealthProfile;
    ConstraintLayout AssistantInfo;
    RadioGroup BloodType;

    Button aSave , hSave,pSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
            firebaseDatabase = FirebaseDatabase.getInstance();

            databaseReference  = firebaseDatabase.getReference("userData");

            final String keyUser = "anas";


        //data passed from main activity to decide which relative layout to show:

        try{

             intent2 =  getIntent().getStringExtra("key");

            Toast.makeText(getApplicationContext() ,intent2 , Toast.LENGTH_LONG).show();
            assert intent2 != null;
            if (intent2.equals("InfoC" )) {
                PersonalInformation.setVisibility(View.VISIBLE);
                HealthProfile.setVisibility(View.GONE);
                AssistantInfo.setVisibility(View.GONE);


                i = true;

            }

            else if (intent2.equals("MedcK")) {


                HealthProfile.setVisibility(View.VISIBLE);
                AssistantInfo.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext() ,intent2 , Toast.LENGTH_LONG).show();

                PersonalInformation.setVisibility(View.GONE);

                i = true;
            }

            else if (intent2.equals( "HelpK" )) {
                AssistantInfo.setVisibility(View.VISIBLE);
                HealthProfile.setVisibility(View.GONE);
                PersonalInformation.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext() ,intent2 , Toast.LENGTH_LONG).show();

                i = true;

            }

            if (i){

                switch (Objects.requireNonNull(intent2)){

                    case "InfoC" :

                        pSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MenuActivity.this , MainActivity12.class);
                                FName= FirstName.getText().toString();
                                LName =LastName.getText().toString();
                                phonenum= PhoneNo.getText().toString();
                                profileimg.setDrawingCacheEnabled(true);
                                b=profileimg.getDrawingCache();

                                if (phonenum.length() != 10){
                                    Toast.makeText(getApplicationContext(), " Write a valid number please" , Toast.LENGTH_LONG).show();

                                }else if(phonenum.charAt(0) == '0'){
                                    phonenum  = "+966" +  phonenum.substring(1);



                                    userMap.put("plastname" , LName);
                                    userMap.put("pphonenum" , phonenum);
                                    userMap.put("pfirstname" , FName);

                                    firebaseDatabase.getReference("userData/anas").setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {



                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("dataBaseError" , e.toString());
                                        }
                                    });




                                    startActivity(intent);
                                }




                            }
                        });

                        break;

                    case "MedcK" :
                        hSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MenuActivity.this ,MainActivity12.class);
                                nationalid= NationalID.getText().toString();
                                fullname=FullName.getText().toString();
                                mfile=MFile.getText().toString();
                                int radioID=BloodType.getCheckedRadioButtonId();
                                RadioButton radioButton=findViewById(radioID);
                                bloodtype = radioButton.getText().toString();


                                userMap.put("hnid" , nationalid);
                                userMap.put("hfullname" , fullname);
                                userMap.put("hmfile" , mfile);
                                userMap.put("hbloodtype" , bloodtype);

                                firebaseDatabase.getReference("userData/anas").setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {



                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("dataBaseError" , e.toString());
                                    }
                                });


                                startActivity(intent);
                            }
                        });

                        break;

                    case "HelpK" :




                        aSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent =new Intent(MenuActivity.this,MainActivity12.class);

                                String pha = PhoneNoA.getText().toString();
                                intent.putExtra("aPhonenum",pha);

                                if (pha.length() != 10){
                                    Toast.makeText(getApplicationContext(), " Write a valid number please" , Toast.LENGTH_LONG).show();
                                }else if(pha.charAt(0) == '0'){
                                    pha  = "+966" +  pha.substring(1);
                                }

                                String msg1 = message1.getText().toString();

                                String msg2 = message2.getText().toString();

                                String msg3 = message3.getText().toString();

                                if (msg1.isEmpty()){
                                    msg1 = "I'm not in in hurry but can you please if you are free come to help me with something ";
                                }else if (msg2.isEmpty()){
                                    msg2 = " I'm in wired situation Im not sure but if you are free come to me please  ";
                                }else if (msg3.isEmpty()){
                                    msg3 = "!!!  Im in very Dangers situation come and help me please ";
                                }


                                userMap.put("aPhonenum" , pha);

                                userMap.put("aMsg1" ,msg1);
                                userMap.put("aMsg2" , msg2);
                                userMap.put("aMsg3" , msg3);

                                firebaseDatabase.getReference("userData/anas").setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {



                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("dataBaseError" , e.toString());
                                    }
                                });

                                startActivity(intent);


                            }
                        });




                        break;

                }


            }

            }

        catch (Exception e){e.printStackTrace();}







        //3 Relative Layout (GONE)
         PersonalInformation =findViewById(R.id.PersonalInfo);
         HealthProfile =findViewById(R.id.HealthProfile);
         AssistantInfo =findViewById(R.id.assistantInfo);

        //Personal Information Layout variables declaration :
         FirstName=findViewById(R.id.FirstNameNew);
         LastName=findViewById(R.id.LastName);
         PhoneNo=findViewById(R.id.PhoneNo);
         profileimg  =findViewById(R.id.profile_image);
         pSave=findViewById(R.id.SavePersonalInfo);


        //Health Profile Layout variables declaration :
         NationalID=findViewById(R.id.NID);
         FullName=findViewById(R.id.FullName);
         MFile=findViewById(R.id.MedicalFile);
         BloodType =findViewById(R.id.radio);
         hSave=findViewById(R.id.SaveHealthProfile);


        //Assistant Info Layout variables declaration :
         PhoneNoA=findViewById(R.id.phoneNoo);
         message1=findViewById(R.id.M1);
         message2=findViewById(R.id.M2);
         message3=findViewById(R.id.M3);

         aSave=findViewById(R.id.SaveAssistantInfo);




        //data passed from main activity to decide which relative layout to show:





        //
        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1);
            }
        });


        //
        if (!i) {
            pSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FName = FirstName.getText().toString();
                    LName = LastName.getText().toString();
                    phonenum = PhoneNo.getText().toString();
                    profileimg.setDrawingCacheEnabled(true);
                    b = profileimg.getDrawingCache();

                    if (phonenum.length() != 10) {
                        Toast.makeText(getApplicationContext(), " Write a valid number please", Toast.LENGTH_LONG).show();

                    } else if (phonenum.charAt(0) == '0') {
                        phonenum = "+966" + phonenum.substring(1);
                        PersonalInformation.setVisibility(View.GONE);
                        HealthProfile.setVisibility(View.VISIBLE);
                        AssistantInfo.setVisibility(View.GONE);
                    }
                }


            });

            //
            hSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nationalid = NationalID.getText().toString();
                    fullname = FullName.getText().toString();
                    mfile = MFile.getText().toString();
                    int radioID = BloodType.getCheckedRadioButtonId();
                    RadioButton radioButton = findViewById(radioID);
                    bloodtype = radioButton.getText().toString();


                    PersonalInformation.setVisibility(View.GONE);
                    HealthProfile.setVisibility(View.GONE);
                    AssistantInfo.setVisibility(View.VISIBLE);

                }
            });


            //
            aSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MenuActivity.this, MainActivity12.class);


                    String pha = PhoneNoA.getText().toString();
                    intent.putExtra("aPhonenum", pha);

                    if (pha.length() != 10) {
                        Toast.makeText(getApplicationContext(), " Write a valid number please", Toast.LENGTH_LONG).show();
                    } else if (pha.charAt(0) == '0') {
                        pha = "+966" + pha.substring(1);
                    }

                    String msg1 = message1.getText().toString();

                    String msg2 = message2.getText().toString();

                    String msg3 = message3.getText().toString();

                    if (msg1.isEmpty()) {
                        msg1 = "I'm not in in hurry but can you please if you are free come to help me with something ";
                    } else if (msg2.isEmpty()) {
                        msg2 = " I'm in wired situation Im not sure but if you are free come to me please  ";
                    } else if (msg3.isEmpty()) {
                        msg3 = "!!!  Im in very Dangers situation come and help me please ";
                    }


                    PersonalInformation.setVisibility(View.GONE);
                    HealthProfile.setVisibility(View.GONE);
                    AssistantInfo.setVisibility(View.GONE);

                    userMap.put("plastname", LName);
                    userMap.put("pphonenum", phonenum);
                    userMap.put("pfirstname", FName);

                    userMap.put("hnid", nationalid);
                    userMap.put("hfullname", fullname);
                    userMap.put("hmfile", mfile);
                    userMap.put("hbloodtype", bloodtype);

                    userMap.put("aPhonenum", pha);

                    userMap.put("aMsg1", msg1);
                    userMap.put("aMsg2", msg2);
                    userMap.put("aMsg3", msg3);

                    firebaseDatabase.getReference("userData/" + keyUser).setValue(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("dataBaseError", e.toString());
                        }
                    });

                    startActivity(intent);

                }


            });
        }

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