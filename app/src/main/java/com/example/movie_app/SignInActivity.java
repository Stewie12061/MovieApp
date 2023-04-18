package com.example.movie_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movie_app.Database.DataLocalManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private String emailTxt, passwordTxt;
    private TextView textViewSignUp;
    private Button buttonSignIn, buttonSignUp;
    private CheckBox mCheckBookRemember;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://movie-app-eb471-default-rtdb.firebaseio.com/");
    private FirebaseAuth mAuth;
    private SharedPreferences mPreferences;
    public static final String SHARED_PREFS = "sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initUi();
        onClickButtonSignUp();
        setTextViewColor(textViewSignUp, getResources().getColor(R.color.light_gradient1), getResources().getColor(R.color.light_gradient2));
//      Login
        mPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        getPreferencesData();
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailTxt = emailEditText.getText().toString();
                passwordTxt = passwordEditText.getText().toString();
                if(emailTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(SignInActivity.this, "Please enter your email or password", Toast.LENGTH_SHORT).show();
                }else{
                    if(mCheckBookRemember.isChecked()){
                        Boolean boolIsChecked = mCheckBookRemember.isChecked();
                        SharedPreferences.Editor editor = mPreferences.edit();
                        editor.putString("pref_email", emailTxt);
                        editor.putString("pref_password", passwordTxt);
                        editor.putBoolean("pref_check", boolIsChecked);
                        editor.apply();
                    }else{
                        mPreferences.edit().clear().apply();
                    }
                    authenticationUser();
                }
            }
        });
    }

    private void getPreferencesData() {
        SharedPreferences sp = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        if(sp.contains("pref_email")){
            String email = sp.getString("pref_email", "not found");
            emailEditText.setText(email.toString());
        }
        if(sp.contains("pref_password")){
            String password = sp.getString("pref_password", "not found");
            passwordEditText.setText(password.toString());
        }
        if(sp.contains("pref_check")){
            Boolean isChecked = sp.getBoolean("pref_check", false);
            mCheckBookRemember.setChecked(isChecked);
        }
    }

    private void authenticationUser(){
        mAuth.signInWithEmailAndPassword(emailTxt, passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                DataLocalManager.setUserUid(mAuth.getCurrentUser().getUid());
                if(task.isSuccessful()){
                    Toast.makeText(SignInActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    onHomeFullActivity();
                }else{
                    Toast.makeText(SignInActivity.this, "Wrong password or email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initUi(){
        textViewSignUp = findViewById(R.id.btnSignUp);
        buttonSignIn = findViewById(R.id.signIn_btn);
        buttonSignUp = findViewById(R.id.btnSignUp);
        emailEditText = findViewById(R.id.email_data);
        passwordEditText = findViewById(R.id.Fname_data);
        mCheckBookRemember = findViewById(R.id.signin_checkbox_remember);
    }

    private void setTextViewColor(TextView textView, int...color) {
        TextPaint paint = textView.getPaint();
        float width = paint.measureText(textView.getText().toString());
        Shader shader = new LinearGradient(0, 0, width, textView.getTextSize(), color, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(shader);
        textView.setTextColor(color[0]);
    }

    private void onClickButtonSignUp(){
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUpActivity();
            }
        });
    }

    public void onHomeFullActivity(){
        Intent intent = new Intent(this, HomeFullActivity.class);
        startActivity(intent);
    }

    public void onSignUpActivity(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

}