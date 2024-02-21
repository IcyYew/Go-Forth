package com.example.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class TroopManagementActivity extends AppCompatActivity {

    // stores final count
    private int archersCount = 0;
    private int knightsCount = 0;
    private int magesCount = 0;
    private int cavalryCount = 0;

    // stores intermediary counts
    private int archersToTrainCount = 0;
    private int knightsToTrainCount = 0;
    private int magesToTrainCount = 0;
    private int cavalryToTrainCount = 0;

    // textviews
    private TextView archersToTrainCountTextView;
    private TextView knightsToTrainCountTextView;
    private TextView magesToTrainCountTextView;
    private TextView cavalryToTrainCountTextView;

    // checkboxes
    private CheckBox archersCheckbox;
    private CheckBox knightsCheckbox;
    private CheckBox magesCheckbox;
    private CheckBox cavalryCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troop_management);

        archersToTrainCountTextView = findViewById(R.id.archersToTrainCount);
        knightsToTrainCountTextView = findViewById(R.id.knightsToTrainCount);
        magesToTrainCountTextView = findViewById(R.id.magesToTrainCount);
        cavalryToTrainCountTextView = findViewById(R.id.cavalryToTrainCount);

        archersCheckbox = findViewById(R.id.archersCheckbox);
        knightsCheckbox = findViewById(R.id.knightsCheckbox);
        magesCheckbox = findViewById(R.id.magesCheckbox);
        cavalryCheckbox = findViewById(R.id.cavalryCheckbox);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TroopManagementActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button trainOneButton = findViewById(R.id.trainOneButton);
        trainOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainTroops(1);
            }
        });

        Button trainTenButton = findViewById(R.id.trainTenButton);
        trainTenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainTroops(10);
            }
        });

        Button trainFiftyButton = findViewById(R.id.trainFiftyButton);
        trainFiftyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainTroops(50);
            }
        });

        Button trainHundredButton = findViewById(R.id.trainHundredButton);
        trainHundredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainTroops(100);
            }
        });

        Button confirmTrainingButton = findViewById(R.id.confirmTrainingButton);
        confirmTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmTraining();
            }
        });
    }

    private void trainTroops(int amount) {
        if (archersCheckbox.isChecked()) {
            archersToTrainCount += amount;
            archersToTrainCountTextView.setText(String.valueOf(archersToTrainCount));
        }
        if (knightsCheckbox.isChecked()) {
            knightsToTrainCount += amount;
            knightsToTrainCountTextView.setText(String.valueOf(knightsToTrainCount));
        }
        if (magesCheckbox.isChecked()) {
            magesToTrainCount += amount;
            magesToTrainCountTextView.setText(String.valueOf(magesToTrainCount));
        }
        if (cavalryCheckbox.isChecked()) {
            cavalryToTrainCount += amount;
            cavalryToTrainCountTextView.setText(String.valueOf(cavalryToTrainCount));
        }
    }

    private void confirmTraining() {
        archersCount += archersToTrainCount;
        knightsCount += knightsToTrainCount;
        magesCount += magesToTrainCount;
        cavalryCount += cavalryToTrainCount;

        // Update the total troop counts
        TextView archersCountTextView = findViewById(R.id.archersCount);
        archersCountTextView.setText(String.valueOf(archersCount));

        TextView knightsCountTextView = findViewById(R.id.knightsCount);
        knightsCountTextView.setText(String.valueOf(knightsCount));

        TextView magesCountTextView = findViewById(R.id.magesCount);
        magesCountTextView.setText(String.valueOf(magesCount));

        TextView cavalryCountTextView = findViewById(R.id.cavalryCount);
        cavalryCountTextView.setText(String.valueOf(cavalryCount));

        // Reset the troops to be trained counts
        archersToTrainCount = 0;
        knightsToTrainCount = 0;
        magesToTrainCount = 0;
        cavalryToTrainCount = 0;

        archersToTrainCountTextView.setText(String.valueOf(archersToTrainCount));
        knightsToTrainCountTextView.setText(String.valueOf(knightsToTrainCount));
        magesToTrainCountTextView.setText(String.valueOf(magesToTrainCount));
        cavalryToTrainCountTextView.setText(String.valueOf(cavalryToTrainCount));

        // Clear checkboxes
        archersCheckbox.setChecked(false);
        knightsCheckbox.setChecked(false);
        magesCheckbox.setChecked(false);
        cavalryCheckbox.setChecked(false);
    }
}