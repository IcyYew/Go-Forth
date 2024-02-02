package com.example.androidexample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmEditText;
    private Button loginButton;
    private Button signupButton;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String SIGNUP_STATUS_KEY = "signupStatus";
    private static final String SIGNUP_PASSWORD_KEY = "signupPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameEditText = findViewById(R.id.signup_username_edt);
        passwordEditText = findViewById(R.id.signup_password_edt);
        confirmEditText = findViewById(R.id.signup_confirm_edt);
        loginButton = findViewById(R.id.signup_login_btn);
        signupButton = findViewById(R.id.signup_signup_btn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirm = confirmEditText.getText().toString();

                if (password.equals(confirm)) {
                    // Save signup status to SharedPreferences
                    SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean(SIGNUP_STATUS_KEY, true);
                    editor.putString(SIGNUP_PASSWORD_KEY, password);  // Save the password during signup
                    editor.apply();

                    Toast.makeText(getApplicationContext(), "Signing up", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Password don't match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}