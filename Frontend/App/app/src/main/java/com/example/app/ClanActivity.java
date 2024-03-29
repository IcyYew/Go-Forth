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

public class ClanActivity extends AppCompatActivity {

    private int userID;

    Button back;

    Button ClanManagement;

    Button ClanChat;

    Button JoinClan;

    Button CreateClan;

    TextView ClanName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clan);

        //Get ID
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }

        back = findViewById(R.id.Back);

        ClanManagement = findViewById(R.id.ClanManagement);

        ClanChat = findViewById(R.id.ClanChat);

        JoinClan =  findViewById(R.id.JoinClan);

        ClanName =  findViewById(R.id.ClanName);







    }
}