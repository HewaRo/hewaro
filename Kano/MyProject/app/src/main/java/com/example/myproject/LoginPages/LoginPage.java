package com.example.myproject.LoginPages;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.MainActivity;
import com.example.myproject.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginPage extends AppCompatActivity {
EditText Login_et_email,Login_et_Password;
Button Login_btn_Login;
TextView login_tv_have_account;
ProgressBar progressBar;
FirebaseAuth fbA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Login_et_email=findViewById(R.id.Login_ET_Email);
        Login_et_Password=findViewById(R.id.Login_ET_Password);
        Login_btn_Login=findViewById(R.id.Login_Button_Login);
        login_tv_have_account =findViewById(R.id.Login_TXT_Account);
        progressBar = findViewById(R.id.Login_ProgressBar_Login);

        fbA=FirebaseAuth.getInstance();
        Login_btn_Login.setOnClickListener(view -> {

            String email,password;
            email=Login_et_email.getText().toString();
            password=Login_et_Password.getText().toString();

            if(email.trim().length() == 0){
                Login_et_email.setError("Required");
                Login_et_email.requestFocus();
            }
            else if ((password.trim().length()<6)){
                if(password.trim().length()==0){
                    Login_et_Password.setError("Required");
                    Login_et_Password.requestFocus();
                }
                else {
                    Login_et_Password.setError("At least 6 digits");
                    Login_et_Password.requestFocus();
                }
            }
            else {
                progressBar.setVisibility(View.VISIBLE);
                Login_btn_Login.setEnabled(false);
                fbA.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    Login_btn_Login.setEnabled(true);
                    if(task.isSuccessful()){
                        Intent intent=new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                   else {
                        Toast.makeText(LoginPage.this,  Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    }

                });
            }
        });
        login_tv_have_account.setOnClickListener(view -> {
            Intent intent=new Intent(getBaseContext(),Signup_page.class);
            startActivity(intent);
            finish();
        });
    }

}