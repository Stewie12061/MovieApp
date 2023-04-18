package com.example.movie_app;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movie_app.Fragment.AccountFragment;
import com.google.firebase.auth.FirebaseAuth;

public class AccountActivity extends AppCompatActivity {
    private ImageButton buttonBack;
    private Button buttonLogout;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        InitUi();
        onClickButtonBack();
        onClickButtonLogout();
    }

    private void onClickButtonLogout(){
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent signInActivity = new Intent(AccountActivity.this, SignInActivity.class);
                signInActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signInActivity);
                finish();
            }
        });
    }

    private void onClickButtonBack(){
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void InitUi(){
        buttonLogout = (Button) findViewById(R.id.account_logout);
        buttonBack = (ImageButton) findViewById(R.id.account_button_back);
    }
}