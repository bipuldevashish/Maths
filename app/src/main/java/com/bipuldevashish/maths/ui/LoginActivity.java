package com.bipuldevashish.maths.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bipuldevashish.maths.R;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText et_email,et_password;
    Button btn_login;
    String email,password;
    private ProgressBar progressBar;
    private final String URL = "http://192.168.42.7/LoginRegister/login.php";
    LinearLayout noAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        et_email = findViewById(R.id.etEmail);
        et_password = findViewById(R.id.etPassword);
        btn_login = findViewById(R.id.loginBtn);
        progressBar = findViewById(R.id.progressBar);
        noAccount = findViewById(R.id.linearlayoutNoaccount);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                validate();
            }
        });

        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void validate() {
        email = et_email.getText().toString().trim();
        password = et_password.getText().toString().trim();

        if (!email.isEmpty() && !password.isEmpty()){
            login(email, password);
        }else {
            if (email.isEmpty()){
                et_email.setError("Please Enter Email");
                progressBar.setVisibility(View.GONE);
            }
            if (password.isEmpty()) {
                et_password.setError("Please Enter password");
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    private void login(String email, String password) {
        Log.d(TAG, "login: email = "+ email + " || password = "+ password );
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[2];
                field[0] = "email";
                field[1] = "password";
                String[] data = new String[2];
                data[0] = email;
                data[1] = password;
                PutData putData = new PutData(URL, "POST", field, data);
                if (putData.startPut()){
                    if (putData.onComplete()){
                        Log.d(TAG, "run: on Complete");
                        progressBar.setVisibility(View.GONE);
                        String result = putData.getResult();
                        Log.d(TAG, "result = "+ result);
                        if (result.equals("Login Success")){
                            Log.d(TAG, "login success");
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Log.d(TAG, "run: login failed");
                            Toast.makeText(LoginActivity.this, result   , Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}