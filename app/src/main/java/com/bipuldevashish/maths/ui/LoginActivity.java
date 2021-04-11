package com.bipuldevashish.maths.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bipuldevashish.maths.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    EditText et_email,et_password;
    Button btn_login;
    String email,password;
    private ProgressBar progressBar;
    private final String URL = "http://192.168.0.106/LoginRegister/login.php";
    LinearLayout noAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.etEmail);
        et_password = findViewById(R.id.etPassword);
        btn_login = findViewById(R.id.loginBtn);
        progressBar = findViewById(R.id.progressBar);
        noAccount = findViewById(R.id.linearlayoutNoaccount);

        btn_login.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            validate();
        });

        noAccount.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
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
        handler.post(() -> {
            String[] field = new String[2];
            field[0] = "email";
            field[1] = "password";
            String[] data = new String[2];
            data[0] = email;
            data[1] = password;
            try {
                PutData putData = new PutData(URL, "POST", field, data);
                if (putData.startPut()) {
                    Log.d(TAG, "login: put started");
                    if (putData.onComplete()) {
                        Log.d(TAG, "run: on Complete");
                        progressBar.setVisibility(View.GONE);
                        String result = putData.getResult();
                        Log.d(TAG, "result = " + result);
                        if (result.equals("Login Success")) {
                            Log.d(TAG, "login success");

                            SharedPreferences sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(getString(R.string.isUserLoggedIn), true);
                            editor.apply();

                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.d(TAG, "run: login failed");
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Something went wrong with server", Toast.LENGTH_SHORT).show();
                    }
                }
            }catch (Exception e){
                Log.d(TAG, "run: " + e.getMessage());
            }
        });
    }
}