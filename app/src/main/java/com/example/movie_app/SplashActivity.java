package com.example.movie_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                nextActivity();
            }
        }, 3000);
    }

    private void nextActivity(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            //not login
            Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
            startActivity(intent);

        }else{
            Intent intent = new Intent(SplashActivity.this, HomeFullActivity.class);
            startActivity(intent);
        }
    }
}

