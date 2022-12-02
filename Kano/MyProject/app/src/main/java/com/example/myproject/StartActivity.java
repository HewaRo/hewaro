package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myproject.LoginPages.LoginPage;
import com.example.myproject.LoginPages.authenitcation_activty;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {
    FirebaseAuth fbA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        findViews();


    }

    public void findViews() {
        fbA = FirebaseAuth.getInstance();
        Intent intent;
        if (fbA.getCurrentUser() != null) {
            intent = new Intent(this, MainActivity.class);

        } else {
            intent = new Intent(this, authenitcation_activty.class);
        }
        startActivity(intent);
        finish();
    }
}