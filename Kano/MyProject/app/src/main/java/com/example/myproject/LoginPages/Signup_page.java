package com.example.myproject.LoginPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myproject.MainActivity;
import com.example.myproject.R;
import com.example.myproject.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class Signup_page extends AppCompatActivity {
    EditText Signup_et_name, Signup_et_email, Signup_et_password;
    Button Signup_btn_signup;
    TextView tv_haveAccount;
    ProgressBar progressBar;
    FirebaseAuth fbA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        findViews();
        Signup_et_email = findViewById(R.id.SingUp_ET_Email);
        Signup_et_password = findViewById(R.id.SingUp_ET_Password);
        Signup_et_name = findViewById(R.id.SingUp_ET_Name);
        tv_haveAccount = findViewById(R.id.SingUp_TXT_Account);
        Signup_btn_signup = findViewById(R.id.SignUp_Button_SingUp);
        progressBar = findViewById(R.id.SignUp_ProgressBar_SingUp);
        tv_haveAccount.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), LoginPage.class);
            startActivity(intent);
            finish();
        });


        Signup_btn_signup.setOnClickListener(view -> {
            String name, email, password;
            name = Signup_et_name.getText().toString();
            email = Signup_et_email.getText().toString();
            password = Signup_et_password.getText().toString();
            if (name.trim().length() <= 0) {
                Signup_et_name.setError("REQUIRED");
            } else if (email.trim().length() <= 0) {
               Signup_et_email.setError("REQUIRED");
            } else if (password.trim().length() < 6) {
                if(password.trim().length() <= 0){Signup_et_password.setError("REQUIRED");}
                else {Signup_et_password.setError("At least 6 digits");}

            } else {
                progressBar.setVisibility(View.VISIBLE);
                Signup_btn_signup.setEnabled(false);
                User user = new User("", name, "", "");
                signup(user, email, password);
            }

        });

    }
   public void findViews(){
        fbA = FirebaseAuth.getInstance();
   }

    public void signup(User user, String email, String Password) {
        fbA.createUserWithEmailAndPassword(email, Password).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            Signup_btn_signup.setEnabled(true);
            if (task.isSuccessful()) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Signup_page.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
}