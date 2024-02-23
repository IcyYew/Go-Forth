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
    private Button loginButton;
    private Button signupButton;
    private Button troopManagementButton;
    private Button dummyButton;
    private Button displayButton;
    private int userID;
    private TextView UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UI initialization
        loginButton = findViewById(R.id.loginButton);

        signupButton = findViewById(R.id.signupButton);

        troopManagementButton = findViewById(R.id.troopManagementButton);

        dummyButton = findViewById(R.id.dummyButton);

        displayButton = findViewById(R.id.displayButton);

        UID = findViewById(R.id.userID);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            if (userID == 0) {
                userID = 0;
                UID.setText("User ID: ");
            }
        } else {
            String number = extras.getString("ID");
            userID = Integer.parseInt(number);
            UID.setText("User ID: " + number);
        }

        //Login button pressed
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast toast = Toast.makeText(MainActivity.this, "Login Pressed", Toast.LENGTH_SHORT);
                //toast.show();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //Signup button pressed
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast toast = Toast.makeText(MainActivity.this, "Signup Pressed", Toast.LENGTH_SHORT);
                //toast.show();

                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        //Troop Management pressed
        troopManagementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TroopManagementActivity.class);
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });

        dummyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DummyActivity.class);
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });

        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });
    }
}