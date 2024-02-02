package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signupButton;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String SIGNUP_STATUS_KEY = "signupStatus";
    private static final String SIGNUP_PASSWORD_KEY = "signupPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.login_username_edt);
        passwordEditText = findViewById(R.id.login_password_edt);
        loginButton = findViewById(R.id.login_login_btn);
        signupButton = findViewById(R.id.login_signup_btn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check signup status from SharedPreferences
                boolean isSignedUp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getBoolean(SIGNUP_STATUS_KEY, false);

                if (isSignedUp) {
                    String username = usernameEditText.getText().toString();
                    String enteredPassword = passwordEditText.getText().toString();

                    // Retrieve the stored password during signup
                    String storedPassword = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString(SIGNUP_PASSWORD_KEY, "");

                    if (enteredPassword.equals(storedPassword)) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("USERNAME", username);
                        intent.putExtra("PASSWORD", enteredPassword);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please sign up first", Toast.LENGTH_LONG).show();
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}