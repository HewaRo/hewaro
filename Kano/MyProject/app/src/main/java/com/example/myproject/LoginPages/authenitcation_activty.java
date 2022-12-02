package com.example.myproject.LoginPages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.myproject.R;


public class authenitcation_activty extends AppCompatActivity {
Button btn_authentication_gmail,btn_authentication_email,btn_authentication_facebook;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenitcation_activty);

        btn_authentication_email = findViewById(R.id.btn_authentication_email1);
        btn_authentication_facebook=findViewById(R.id.btn_authentication_facbook);
        btn_authentication_gmail=findViewById(R.id.btn_authentication_gmail);

        btn_authentication_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent =new Intent(authenitcation_activty.this,Signup_page.class);
                startActivity(intent);
                finish();

            }
        });
btn_authentication_gmail.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
         intent=new Intent(getBaseContext(),LoginPage.class);
        startActivity(intent);
        finish();
    }
});

    }
}
