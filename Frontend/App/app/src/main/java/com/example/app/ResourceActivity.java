package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ResourceActivity extends AppCompatActivity {

    private int food;

    private int platinum;

    private int stone;

    private int wood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);
    }
}