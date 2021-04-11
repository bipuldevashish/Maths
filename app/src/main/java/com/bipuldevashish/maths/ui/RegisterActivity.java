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
import com.bipuldevashish.maths.util.Utils;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    EditText etReg_name, etReg_email, etReg_phone, etReg_password, etReg_cnfPass;
    String name, email, phoneNumber, password, cnf_pass;
    Button sign_up;
    LinearLayout alreadyHaveLayout;
    ProgressBar progressBar;
    private final String URL = "http://192.168.0.106/LoginRegister/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etReg_name = findViewById(R.id.etNameRegister);
        etReg_email = findViewById(R.id.etEmailRegister);
        etReg_phone = findViewById(R.id.etPhoneRegister);
        etReg_password = findViewById(R.id.etPasswordRegister);
        etReg_cnfPass = findViewById(R.id.etCnfPasswordRegister);

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
        cnf_pass = etReg_cnfPass.getText().toString();

        if (!name.isEmpty() && !email.isEmpty() && !phoneNumber.isEmpty() && !password.isEmpty() && !cnf_pass.isEmpty() && password.equals(cnf_pass) & Utils.isEmailValid(email)) {
            Log.d(TAG, "validate: calling sign up function");
            signUp(name, email, phoneNumber, password);
        } else {
            if (name.isEmpty()) {
                etReg_name.setError("Please Enter Name");
                progressBar.setVisibility(View.GONE);
            }
            if (email.isEmpty()) {
                etReg_email.setError("Please Enter Email");
                progressBar.setVisibility(View.GONE);
            }
            if (!Utils.isEmailValid(email)) {
                etReg_email.setError("Please Enter Valid Email");
                progressBar.setVisibility(View.GONE);
            }
            if (phoneNumber.isEmpty()) {
                etReg_phone.setError("Please Enter PhoneNumber");
                progressBar.setVisibility(View.GONE);
            }

            if (password.isEmpty()) {
                etReg_password.setError("Please Enter Password");
                progressBar.setVisibility(View.GONE);
            }

            if (cnf_pass.isEmpty()) {
                etReg_cnfPass.setError("Please Confirm Your Password");
                progressBar.setVisibility(View.GONE);
            }

            if (!password.equals(cnf_pass)) {
                etReg_cnfPass.setError("Password and Confirm Password Should be Same");
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
                        String result = putData.getResult();
                        Log.d(TAG, "result = " + result);
                        if (result.equals("Sign Up Success")) {
                            Log.d(TAG, "Sign Up success");
                            progressBar.setVisibility(View.GONE);

                            SharedPreferences sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(getString(R.string.isUserLoggedIn), true);
                            editor.apply();

                            Toast.makeText(RegisterActivity.this.getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Log.d(TAG, "run: Sign up failed");
                            Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, "Something went wrong with server", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}