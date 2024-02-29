package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

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

        Bundle extras = getIntent().getExtras();

        //Login button pressed
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupSuccessActivity.this, MainActivity.class);
                intent.putExtra("ID", Objects.requireNonNull(extras.getString("ID")));
                startActivity(intent);
            }
        });
    }
}
