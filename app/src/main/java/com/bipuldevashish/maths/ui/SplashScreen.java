package com.bipuldevashish.maths.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.bipuldevashish.maths.R;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean firstStart = sharedPreferences.getBoolean(getString(R.string.isAppFirstTime), true);
        boolean isUserLoggedIn = sharedPreferences.getBoolean(getString(R.string.isUserLoggedIn), false);
        editor.apply();
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        int SPLASH_DISPLAY_LENGTH = 2000;
        new Handler().postDelayed(() -> {
            /* Create an Intent that will start the Menu-Activity. */
            if (firstStart && isUserLoggedIn) {
                launchMainActivity();
            } else  {
                launchIntroActivity();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }



    private void launchIntroActivity() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
        Intent mainIntent = new Intent(SplashScreen.this, Intro.class);
        startActivity(mainIntent);
        finish();
    }

    private void launchMainActivity() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
        Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }
}