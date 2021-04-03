package com.bipuldevashish.maths.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bipuldevashish.maths.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    EditText etReg_name, etReg_email, etReg_phone, etReg_password;
    String name, email, phoneNumber, password;
    Button sign_up;
    Uri imageUri;
    ImageView mProfileImageView;
    LinearLayout alreadyHaveLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etReg_name = findViewById(R.id.etNameRegister);
        etReg_email = findViewById(R.id.etEmailRegister);
        etReg_phone = findViewById(R.id.etPhoneRegister);
        etReg_password = findViewById(R.id.etPasswordRegister);

        mProfileImageView = findViewById(R.id.profile_image);
        sign_up = findViewById(R.id.registerBtn);
        alreadyHaveLayout = findViewById(R.id.linearlayoutAlreadyHaveaccount);

        getDataFromUser();

        alreadyHaveLayout.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        mProfileImageView.setOnClickListener(v -> pickImageFromGallery());

        sign_up.setOnClickListener(v -> signUp());
    }


    private void getDataFromUser() {

        name = etReg_name.getText().toString();
        email = etReg_email.getText().toString();
        phoneNumber = etReg_phone.getText().toString();
        password = etReg_password.getText().toString();

    }

    private void pickImageFromGallery() {
        Log.d(TAG, "pickImageFromGallery: select image from gallery");
        Intent intent = CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(10, 10)
                .getIntent(RegisterActivity.this);
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Log.d(TAG, "onActivityResult: Fetched data");

            if (resultCode == Activity.RESULT_OK) {
                imageUri = result.getUri();
                Log.d(TAG, "onActivityResult: image uri =" + imageUri);
                mProfileImageView.setImageURI(imageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(RegisterActivity.this, "You did not choose any image !", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void signUp() {
    }

}