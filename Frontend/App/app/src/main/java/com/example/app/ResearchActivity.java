package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ResearchActivity extends AppCompatActivity {
    private int userID;
    private ImageView troopTrainingImageView;
    private ImageView resourceGatheringImageView;
    private Button upgradeButton;
    private Button backButton;
    private TextView troopTrainingLevelTextView;
    private TextView troopTrainingBonusTextView;
    private TextView resourceGatheringLevelTextView;
    private TextView resourceGatheringBonusTextView;

    // Variables to store current level and bonus for troop training and resource gathering
    private int troopTrainingLevel = 0;
    private float troopTrainingBonus = 0.0f;

    private int resourceGatheringLevel = 0;
    private float resourceGatheringBonus = 0.0f;

    // Variable to track the currently selected skill
    private enum SelectedSkill { TROOP_TRAINING, RESOURCE_GATHERING }
    private SelectedSkill selectedSkill = SelectedSkill.TROOP_TRAINING; // Default to troop training

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }

        // Find the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Set the toolbar as the app bar
        setSupportActionBar(toolbar);

        // Initialize views
        troopTrainingImageView = findViewById(R.id.troopTrainingImageView);
        resourceGatheringImageView = findViewById(R.id.resourceGatheringImageView);
        upgradeButton = findViewById(R.id.upgradeButton);
        backButton = findViewById(R.id.backButton);
        troopTrainingLevelTextView = findViewById(R.id.troopTrainingLevelTextView);
        troopTrainingBonusTextView = findViewById(R.id.troopTrainingBonusTextView);
        resourceGatheringLevelTextView = findViewById(R.id.resourceGatheringLevelTextView);
        resourceGatheringBonusTextView = findViewById(R.id.resourceGatheringBonusTextView);

        // Set initial visibility of images
        updateImageViewVisibility(1); // Set initial tier to 1

        // back button pressed
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResearchActivity.this, MainActivity.class);
                intent.putExtra("ID", String.valueOf(userID));
                startActivity(intent);
            }
        });

        // Set click listeners for images
        troopTrainingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set selected skill to troop training
                selectedSkill = SelectedSkill.TROOP_TRAINING;
                // Display current level and bonus for troop training
                showTroopTrainingInfo();
            }
        });

        resourceGatheringImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set selected skill to resource gathering
                selectedSkill = SelectedSkill.RESOURCE_GATHERING;
                // Display current level and bonus for resource gathering
                showResourceGatheringInfo();
            }
        });

        // Set click listener for upgrade button
        upgradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle upgrade button click based on the selected skill
                switch (selectedSkill) {
                    case TROOP_TRAINING:
                        // Upgrade troop training
                        upgradeTroopTraining();
                        break;
                    case RESOURCE_GATHERING:
                        // Upgrade resource gathering
                        upgradeResourceGathering();
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tier_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.tier1) {
            updateImageViewVisibility(1);
            return true;
        } else if (id == R.id.tier2) {
            updateImageViewVisibility(2);
            return true;
        } else if (id == R.id.tier3) {
            updateImageViewVisibility(3);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateImageViewVisibility(int selectedTier) {
        // Hide images on tiers other than the selected tier
        if (selectedTier != 1) {
            troopTrainingImageView.setVisibility(View.INVISIBLE);
            resourceGatheringImageView.setVisibility(View.INVISIBLE);
            troopTrainingLevelTextView.setVisibility(View.INVISIBLE);
            troopTrainingBonusTextView.setVisibility(View.INVISIBLE);
            resourceGatheringLevelTextView.setVisibility(View.INVISIBLE);
            resourceGatheringBonusTextView.setVisibility(View.INVISIBLE);
        } else {
            troopTrainingImageView.setVisibility(View.VISIBLE);
            resourceGatheringImageView.setVisibility(View.VISIBLE);
            troopTrainingLevelTextView.setVisibility(View.VISIBLE);
            troopTrainingBonusTextView.setVisibility(View.VISIBLE);
            resourceGatheringLevelTextView.setVisibility(View.VISIBLE);
            resourceGatheringBonusTextView.setVisibility(View.VISIBLE);
        }
    }

    private void showTroopTrainingInfo() {
        // Display current level and bonus for troop training
        troopTrainingLevelTextView.setText("Troop Training Speed Level: " + troopTrainingLevel);
        troopTrainingBonusTextView.setText("Troop Training Speed Bonus: " + troopTrainingBonus * 100 + "%");
    }

    private void showResourceGatheringInfo() {
        // Display current level and bonus for resource gathering
        resourceGatheringLevelTextView.setText("Resource Gathering Speed Level: " + resourceGatheringLevel);
        resourceGatheringBonusTextView.setText("Resource Gathering Speed Bonus: " + resourceGatheringBonus * 100 + "%");
    }

    private void upgradeTroopTraining() {
        // Increment troop training level and calculate new bonus
        troopTrainingLevel++;
        troopTrainingBonus = calculateTroopTrainingBonus(troopTrainingLevel);
        // Update display
        showTroopTrainingInfo();
    }

    private void upgradeResourceGathering() {
        // Increment resource gathering level and calculate new bonus
        resourceGatheringLevel++;
        resourceGatheringBonus = calculateResourceGatheringBonus(resourceGatheringLevel);
        // Update display
        showResourceGatheringInfo();
    }

    private float calculateTroopTrainingBonus(int level) {
        // Calculate troop training bonus based on level
        // You can implement your calculation logic here
        return level * 0.03f; // Example: 3% bonus per level
    }

    private float calculateResourceGatheringBonus(int level) {
        // Calculate resource gathering bonus based on level
        // You can implement your calculation logic here
        return level * 0.03f; // Example: 3% bonus per level
    }
}
