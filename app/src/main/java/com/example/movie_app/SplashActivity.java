package com.example.movie_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String userId = preferences.getString("userId", "");
                if (userId.equals("")) {
                    userId = generateUserId(); // Implement this method to generate a unique user ID
                    preferences.edit().putString("userId", userId).apply();
                }
                Intent intent = new Intent(SplashActivity.this, HomeFullActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }
    public String generateUserId() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        String strDate = sdf.format(c.getTime());
        return "user_" + strDate;
    }

}

