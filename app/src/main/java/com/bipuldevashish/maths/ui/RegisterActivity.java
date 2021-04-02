package com.bipuldevashish.maths.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.bipuldevashish.maths.R;

public class RegisterActivity extends AppCompatActivity {

    EditText etReg_name, etReg_email, etReg_phone, etReg_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etReg_name = findViewById(R.id.etNameRegister);
        etReg_email = findViewById(R.id.etEmailRegister);
        etReg_phone = findViewById(R.id.etPhoneRegister);
        etReg_password = findViewById(R.id.etPasswordRegister);


    }
}