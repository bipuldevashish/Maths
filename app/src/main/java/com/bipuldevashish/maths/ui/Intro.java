package com.bipuldevashish.maths.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bipuldevashish.maths.R;

import java.util.Objects;

public class Intro extends AppCompatActivity {

    ViewFlipper viewFlipper;
    Button btn;
    int[] image = {
            R.drawable.insider_ic_online_test,
            R.drawable.insider_study_material,
            R.drawable.insider_ic_discussion,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Objects.requireNonNull(getSupportActionBar()).hide();
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intro.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        viewFlipper = findViewById(R.id.viewflipper);
        for (int i = 0; i < image.length; i++) {
            flip_image(image[i]);
        }
    }
    private void flip_image(int i) {
        ImageView view = new ImageView(this);
        view.setBackgroundResource(i);
        viewFlipper.addView(view);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}