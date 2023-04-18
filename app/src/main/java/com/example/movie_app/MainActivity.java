package com.example.movie_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignInActivity();
            }
        });
    }

    private void initUi(){
        button = (Button)findViewById(R.id.btn_getStarted);
    }

    public void onSignInActivity(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}