package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SuccessActivity extends AppCompatActivity {

    private TextView messageText;   // define message textview variable
    private Button loginButton;     // define login button variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);             // link to Main activity XML

        /* initialize UI elements */
        messageText = findViewById(R.id.textView);      // link to message textview in the Main activity XML
        loginButton = findViewById(R.id.button);    // link to login button in the Main activity XML

        /* click listener on login button pressed */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when login button is pressed, use intent to switch to Login Activity */
                Intent intent = new Intent(SuccessActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
