package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private Button signupButton;
    private Button troopManagementButton;
    private Button displayButton;
    private Button fightButton;
    private Button resourceButton;
    private Button clanchatButton;
    private int userID;
    private TextView UID;

    /**
     * onCreate sets onClickListeners to all of the buttons and gets the extras
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UI initialization
        loginButton = findViewById(R.id.loginButton);

        signupButton = findViewById(R.id.signupButton);

        troopManagementButton = findViewById(R.id.troopManagementButton);

        displayButton = findViewById(R.id.displayButton);

        fightButton = findViewById(R.id.fightButton);

        resourceButton = findViewById(R.id.ResourceButton);

        clanchatButton = findViewById(R.id.ClanChat);

        UID = findViewById(R.id.ID);

        // gets extras and sets userID to whatever we got from the extras. IF there were no extras, empty userID
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            if (userID == 0) {
                userID = 0;
                UID.setText("User ID: ");
            }
        } else {
            String number = Objects.requireNonNull(extras.getString("ID"));
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
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });

        clanchatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast toast = Toast.makeText(MainActivity.this, "Login Pressed", Toast.LENGTH_SHORT);
                //toast.show();

                Intent intent = new Intent(MainActivity.this, ClanChat.class);
                intent.putExtra("ID", userID);
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
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });

        //Resource button pressed
        resourceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResourceActivity.class);
                intent.putExtra("ID", userID);
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

        // display users button pressed
        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });

        // fight button pressed
        fightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FightActivity.class);
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });
    }
}