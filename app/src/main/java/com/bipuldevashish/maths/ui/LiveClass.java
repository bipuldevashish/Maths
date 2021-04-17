package com.bipuldevashish.maths.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.bipuldevashish.maths.R;

public class LiveClass extends AppCompatActivity {

    WebView webView;
    String url = "https://go.givni.in/Goswami%20Mathematics/liveEvent.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_class);

        webView = findViewById(R.id.webView);
        webView.loadUrl(url);
    }
}