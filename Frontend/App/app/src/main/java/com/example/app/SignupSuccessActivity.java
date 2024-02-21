package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignupSuccessActivity extends AppCompatActivity {

    private TextView messageText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_success);

        //Initialize UI
        messageText = findViewById(R.id.textView);
        loginButton = findViewById(R.id.button);

        //Login button pressed
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupSuccessActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
