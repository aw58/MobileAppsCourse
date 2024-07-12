package com.example.practiceiv_firebase_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

//simple splash with custom xml
//lasts for 3 seconds
//no interaction
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        // Delay to simulate loading process
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Start the main activity
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 3000); // 3 seconds delay
    }
}