package com.example.myapplication;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        RelativeLayout PersonalInformation =findViewById(R.id.PersonalInfo);
        RelativeLayout HealthProfile =findViewById(R.id.HealthProfile);
        RelativeLayout AssistantInfo =findViewById(R.id.assistantInfo);






    }
}