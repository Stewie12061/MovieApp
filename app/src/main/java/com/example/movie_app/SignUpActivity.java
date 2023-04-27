package com.example.movie_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button buttonSignUp;
    private ImageButton buttonBack;
    private FirebaseFirestore db;
    private EditText username, email, password, confirmPassword;
    private String usernameTxt, emailTxt, passwordTxt, confirmPasswordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUi();

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        onClickButtonBack();

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameTxt = username.getText().toString().trim();
                emailTxt = email.getText().toString().trim();
                passwordTxt = password.getText().toString().trim();
                confirmPasswordTxt = confirmPassword.getText().toString().trim();

                if(usernameTxt.isEmpty() || emailTxt.isEmpty() || passwordTxt.isEmpty() || confirmPasswordTxt.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                }
                else if(!passwordTxt.equals(confirmPasswordTxt)){
                    Toast.makeText(SignUpActivity.this, "Password are not matching", Toast.LENGTH_SHORT).show();
                }
                else {
//                    realtimeCheck();
                    authenticationCheck();
                }
            }
        });
    }

    private void authenticationCheck() {
        mAuth.createUserWithEmailAndPassword(emailTxt, passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Create Successfully", Toast.LENGTH_SHORT).show();
                    String currentUserId = mAuth.getCurrentUser().getUid();
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("username", usernameTxt);
                    userdataMap.put("email", emailTxt);
                    userdataMap.put("password", passwordTxt);
                    db.collection("users").document(currentUserId)
                            .set(userdataMap);
                    onSignInActivity();
                }else{
                    Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void onClickButtonBack(){
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignInActivity();
            }
        });
    }

    private void initUi(){
        buttonSignUp =(Button)findViewById(R.id.button_signup);
        username = findViewById(R.id.signup_username);
        email =  findViewById(R.id.signup_email);
        password =  findViewById(R.id.signup_password);
        confirmPassword = findViewById(R.id.signup_confirm_password);
        buttonBack = (ImageButton) findViewById(R.id.button_back_signup);
    }

    public void onSignInActivity(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}