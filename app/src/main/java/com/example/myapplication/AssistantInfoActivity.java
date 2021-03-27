package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AssistantInfoActivity extends AppCompatActivity {

    Button aSave;
    EditText message1,message2,message3, PhoneNoA;
    HashMap<String,String> userMap = new HashMap<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant_info);

        message1=findViewById(R.id.M1New);
        message2=findViewById(R.id.M2New);
        message3=findViewById(R.id.M3New);
        PhoneNoA=findViewById(R.id.phoneNooNew);
        aSave=findViewById(R.id.SaveAssistantInfoNew);


        //database:
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference  = firebaseDatabase.getReference("userData");


        aSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AssistantInfoActivity.this,MainActivity12.class);

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
    }
}