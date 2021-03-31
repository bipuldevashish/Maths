package com.bipuldevashish.maths;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText et_email,et_password;
    Button btn_login;
    String email,password;
    private ProgressBar progressBar;
    private String URL = "http://192.168.1.104/loginregister/login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.etEmail);
        et_password = findViewById(R.id.etPassword);
        btn_login = findViewById(R.id.loginBtn);
        progressBar = findViewById(R.id.progressBar);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                validate();
            }
        });
    }

    private void validate() {
        email = et_email.getText().toString();
        password = et_password.getText().toString();

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
                        progressBar.setVisibility(View.GONE);
                        String result = putData.getResult();
                        if (result.equals("Login Success")){
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, result   , Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}