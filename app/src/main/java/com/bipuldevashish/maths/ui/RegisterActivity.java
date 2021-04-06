package com.bipuldevashish.maths.ui;

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

import androidx.appcompat.app.AppCompatActivity;

import com.bipuldevashish.maths.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    EditText etReg_name, etReg_email, etReg_phone, etReg_password;
    String name, email, phoneNumber, password;
    Button sign_up;
    LinearLayout alreadyHaveLayout;
    ProgressBar progressBar;
    private final String URL = "http://192.168.1.106/LoginRegister/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etReg_name = findViewById(R.id.etNameRegister);
        etReg_email = findViewById(R.id.etEmailRegister);
        etReg_phone = findViewById(R.id.etPhoneRegister);
        etReg_password = findViewById(R.id.etPasswordRegister);

        progressBar = findViewById(R.id.pBar_register);
        sign_up = findViewById(R.id.registerBtn);
        alreadyHaveLayout = findViewById(R.id.linearlayoutAlreadyHaveaccount);


        alreadyHaveLayout.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        sign_up.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            validate();
        });
    }

    private void validate() {

        name = etReg_name.getText().toString();
        email = etReg_email.getText().toString();
        phoneNumber = etReg_phone.getText().toString();
        password = etReg_password.getText().toString();

        if ( !name.isEmpty() && !email.isEmpty() && !phoneNumber.isEmpty() && !password.isEmpty() ){
            Log.d(TAG, "validate: calling sign up function");
            signUp(name, email, phoneNumber, password);
        }else {
            if (name.isEmpty()){
                etReg_name.setError("Please Enter Name");
                progressBar.setVisibility(View.GONE);
            }
            if (email.isEmpty()){
                etReg_email.setError("Please Enter Email");
                progressBar.setVisibility(View.GONE);
            }
            if (phoneNumber.isEmpty()){
                etReg_phone.setError("Please Enter PhoneNumber");
                progressBar.setVisibility(View.GONE);
            }

            if (password.isEmpty()) {
                etReg_password.setError("Please Enter password");
                progressBar.setVisibility(View.GONE);
            }
        }

    }



    private void signUp(String Name, String Email, String PhoneNumber, String Password) {
        Log.d(TAG, "signUp: called");
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[4];
                field[0] = "name";
                field[1] = "email";
                field[2] = "mobile";
                field[3] = "password";
                String[] data = new String[4];
                data[0] = Name;
                data[1] = Email;
                data[2] = PhoneNumber;
                data[3] = Password;
                PutData putData = new PutData(URL, "POST", field, data);
                if (putData.startPut()) {
                    Log.d(TAG, "signUp: put started ");
                    if (putData.onComplete()) {
                        Log.d(TAG, "run: on Complete");
                        progressBar.setVisibility(View.GONE);
                        String result = putData.getResult();
                        Log.d(TAG, "result = " + result);
                        if (result.equals("Sign Up Success")) {
                            Log.d(TAG, "Sign Up success");
                            Toast.makeText(RegisterActivity.this.getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            RegisterActivity.this.startActivity(intent);
                            RegisterActivity.this.finish();
                        } else {
                            Log.d(TAG, "run: Sign up failed");
                            Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

}