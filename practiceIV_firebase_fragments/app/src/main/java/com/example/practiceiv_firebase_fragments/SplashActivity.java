package com.example.practiceiv_firebase_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practiceiv_firebase_fragments.databinding.ActivityMainBinding;

public class SplashActivity extends AppCompatActivity {

    private static int splash_timeout = 3000; //in ms

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.splash_activity);

        // Handler to delay and automatically proceed to the LoginActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                // Finish the splash activity so the user can't return to it
                finish();
            }
        }, splash_timeout);

    }
}
