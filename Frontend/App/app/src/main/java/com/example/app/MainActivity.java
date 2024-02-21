package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;     // define login button variable
    private Button signupButton;    // define signup button variable
    private Button troopManagementButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);
        troopManagementButton = findViewById(R.id.troopManagementButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast toast = Toast.makeText(MainActivity.this, "Login Pressed", Toast.LENGTH_SHORT);
                //toast.show();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast toast = Toast.makeText(MainActivity.this, "Signup Pressed", Toast.LENGTH_SHORT);
                //toast.show();

                //Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        troopManagementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TroopManagementActivity.class);
                startActivity(intent);
            }
        });
    }
}