package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

/**
 * Activity used for research functionality.
 */
public class ResearchActivity extends AppCompatActivity {
    private int userID;
    private ImageView troopTrainingImageView;
    private ImageView researchCostImageView;
    private ImageView buildingCostImageView;
    private ImageView attackBonusImageView;
    private ImageView troopCapacityImageView;
    private ImageView buildingSpeedImageView;

    private Button upgradeButton;
    private Button backButton;

    private TextView troopTrainingLevelTextView;
    private TextView troopTrainingBonusTextView;
    private TextView researchCostLevelTextView;
    private TextView researchCostBonusTextView;
    private TextView buildingCostLevelTextView;
    private TextView buildingCostBonusTextView;
    private TextView attackBonusLevelTextView;
    private TextView attackBonusBonusTextView;
    private TextView troopCapacityLevelTextView;
    private TextView troopCapacityBonusTextView;
    private TextView buildingSpeedLevelTextView;
    private TextView buildingSpeedBonusTextView;

    // Variables to store current level and bonus for troop training and resource gathering. Starts at 0.
    private int troopTrainingLevel = 0;
    private float troopTrainingBonus = 0.0f;
    private int researchCostLevel = 0;
    private float researchCostBonus = 0.0f;
    private int buildingCostLevel = 0;
    private float buildingCostBonus = 0.0f;
    private int attackBonusLevel = 0;
    private float attackBonusBonus = 0.0f;
    private int troopCapacityLevel = 0;
    private float troopCapacityBonus = 0.0f;
    private int buildingSpeedLevel = 0;
    private float buildingSpeedBonus = 0.0f;

    // Variable to track the currently selected skill
    private enum SelectedSkill { TROOP_TRAINING, RESEARCH_COST, BUILDING_COST, ATTACK_BONUS, TROOP_CAPACITY, BUILDING_SPEED }
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
        researchCostImageView = findViewById(R.id.researchCostImageView);
        buildingCostImageView = findViewById(R.id.buildingCostImageView);
        attackBonusImageView = findViewById(R.id.attackBonusImageView);
        troopCapacityImageView = findViewById(R.id.troopCapacityImageView);
        buildingSpeedImageView = findViewById(R.id.buildingSpeedImageView);

        troopTrainingLevelTextView = findViewById(R.id.troopTrainingLevelTextView);
        troopTrainingBonusTextView = findViewById(R.id.troopTrainingBonusTextView);
        researchCostLevelTextView = findViewById(R.id.researchCostLevelTextView);
        researchCostBonusTextView = findViewById(R.id.researchCostBonusTextView);
        buildingCostLevelTextView = findViewById(R.id.buildingCostLevelTextView);
        buildingCostBonusTextView = findViewById(R.id.buildingCostBonusTextView);
        attackBonusLevelTextView = findViewById(R.id.attackBonusLevelTextView);
        attackBonusBonusTextView = findViewById(R.id.attackBonusBonusTextView);
        troopCapacityLevelTextView = findViewById(R.id.troopCapacityLevelTextView);
        troopCapacityBonusTextView = findViewById(R.id.troopCapacityBonusTextView);
        buildingSpeedLevelTextView = findViewById(R.id.buildingSpeedLevelTextView);
        buildingSpeedBonusTextView = findViewById(R.id.buildingSpeedBonusTextView);

        upgradeButton = findViewById(R.id.upgradeButton);
        backButton = findViewById(R.id.backButton);

        // Set initial visibility of images
        updateImageViewVisibility(1); // Set initial tier to 1

        getResearchLevels();

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

        researchCostImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set selected skill to resource gathering
                selectedSkill = SelectedSkill.RESEARCH_COST;
                // Display current level and bonus for resource gathering
                showResearchCostInfo();
            }
        });

        buildingCostImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedSkill = SelectedSkill.BUILDING_COST;
                showBuildingCostInfo();
            }
        });

        attackBonusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedSkill = SelectedSkill.ATTACK_BONUS;
                showAttackBonusInfo();
            }
        });

        troopCapacityImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedSkill = SelectedSkill.TROOP_CAPACITY;
                showTroopCapacityInfo();
            }
        });

        buildingSpeedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedSkill = SelectedSkill.BUILDING_SPEED;
                showBuildingSpeedInfo();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResearchActivity.this, MainActivity.class);
                intent.putExtra("ID", String.valueOf(userID));
                startActivity(intent);
            }
        });

        // Set click listener for upgrade button
        upgradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle upgrade button click based on the selected skill
                switch (selectedSkill) {
                    case TROOP_TRAINING:
                        upgradeTroopTraining();
                        break;
                    case RESEARCH_COST:
                        upgradeResearchCost();
                        break;
                    case BUILDING_COST:
                        upgradeBuildingCost();
                        break;
                    case ATTACK_BONUS:
                        upgradeAttackBonus();
                        break;
                    case TROOP_CAPACITY:
                        upgradeTroopCapacity();
                        break;
                    case BUILDING_SPEED:
                        upgradeBuildingSpeed();
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
        if (selectedTier == 1) {
            troopTrainingImageView.setVisibility(View.VISIBLE);
            researchCostImageView.setVisibility(View.VISIBLE);
            troopTrainingLevelTextView.setVisibility(View.VISIBLE);
            troopTrainingBonusTextView.setVisibility(View.VISIBLE);
            researchCostLevelTextView.setVisibility(View.VISIBLE);
            researchCostBonusTextView.setVisibility(View.VISIBLE);
        } else {
            troopTrainingImageView.setVisibility(View.INVISIBLE);
            researchCostImageView.setVisibility(View.INVISIBLE);
            troopTrainingLevelTextView.setVisibility(View.INVISIBLE);
            troopTrainingBonusTextView.setVisibility(View.INVISIBLE);
            researchCostLevelTextView.setVisibility(View.INVISIBLE);
            researchCostBonusTextView.setVisibility(View.INVISIBLE);
        }

        if (selectedTier == 2) {
            buildingCostImageView.setVisibility(View.VISIBLE);
            buildingCostLevelTextView.setVisibility(View.VISIBLE);
            buildingCostBonusTextView.setVisibility(View.VISIBLE);
            attackBonusImageView.setVisibility(View.VISIBLE);
            attackBonusLevelTextView.setVisibility(View.VISIBLE);
            attackBonusBonusTextView.setVisibility(View.VISIBLE);
        } else {
            buildingCostImageView.setVisibility(View.INVISIBLE);
            buildingCostLevelTextView.setVisibility(View.INVISIBLE);
            buildingCostBonusTextView.setVisibility(View.INVISIBLE);
            attackBonusImageView.setVisibility(View.INVISIBLE);
            attackBonusLevelTextView.setVisibility(View.INVISIBLE);
            attackBonusBonusTextView.setVisibility(View.INVISIBLE);
        }

        if (selectedTier == 3) {
            troopCapacityImageView.setVisibility(View.VISIBLE);
            troopCapacityLevelTextView.setVisibility(View.VISIBLE);
            troopCapacityBonusTextView.setVisibility(View.VISIBLE);
            buildingSpeedImageView.setVisibility(View.VISIBLE);
            buildingSpeedLevelTextView.setVisibility(View.VISIBLE);
            buildingSpeedBonusTextView.setVisibility(View.VISIBLE);
        } else {
            troopCapacityImageView.setVisibility(View.INVISIBLE);
            troopCapacityLevelTextView.setVisibility(View.INVISIBLE);
            troopCapacityBonusTextView.setVisibility(View.INVISIBLE);
            buildingSpeedImageView.setVisibility(View.INVISIBLE);
            buildingSpeedLevelTextView.setVisibility(View.INVISIBLE);
            buildingSpeedBonusTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void showTroopTrainingInfo() {
        troopTrainingLevelTextView.setText("Troop Training Speed Level: " + troopTrainingLevel);
        troopTrainingBonusTextView.setText("Troop Training Speed Bonus: " + troopTrainingBonus * 100 + "%");
    }

    private void showResearchCostInfo() {
        researchCostLevelTextView.setText("Cheaper Research Level: " + researchCostLevel);
        researchCostBonusTextView.setText("Cheaper Research Bonus: " + researchCostBonus * 100 + "%");
    }

    private void showBuildingCostInfo() {
        buildingCostLevelTextView.setText("Cheaper Building Upgrades Level: " + buildingCostLevel);
        buildingCostBonusTextView.setText("Cheaper Building Upgrades Bonus: " + buildingCostBonus * 100 + "%");
    }

    private void showAttackBonusInfo() {
        attackBonusLevelTextView.setText("All Troops Attack Bonus Level: " + attackBonusLevel);
        attackBonusBonusTextView.setText("All Troops Attack Bonus: " + attackBonusBonus * 100 + "%");
    }

    private void showTroopCapacityInfo() {
        troopCapacityLevelTextView.setText("Increased Troop Capacity Level: " + troopCapacityLevel);
        troopCapacityBonusTextView.setText("Increased Troop Capacity Bonus: +" + troopCapacityBonus + " troops");
    }

    private void showBuildingSpeedInfo() {
        buildingSpeedLevelTextView.setText("Building Upgrade Speed Level: " + buildingSpeedLevel);
        buildingSpeedBonusTextView.setText("Building Upgrade Speed Bonus: " + buildingSpeedBonus * 100 + "%");
    }

    private void upgradeTroopTraining() {
        if (troopTrainingLevel < 5) {
            levelUpRequest("trainingspeed");
            troopTrainingLevel++;
            getBonus("Training Speed");
        } else {
            Toast.makeText(this, "MAX LEVEL REACHED", Toast.LENGTH_SHORT).show();
        }

        showTroopTrainingInfo();
    }

    private void upgradeResearchCost() {
        if (researchCostLevel < 5) {
            levelUpRequest("researchcost");
            researchCostLevel++;
            getBonus("Research Cost");
        } else {
            Toast.makeText(this, "MAX LEVEL REACHED", Toast.LENGTH_SHORT).show();
        }

        showResearchCostInfo();
    }

    private void upgradeBuildingCost() {
        if (buildingCostLevel < 5) {
            levelUpRequest("buildingcost");
            buildingCostLevel++;
            getBonus("Building Cost");
        } else {
            Toast.makeText(this, "MAX LEVEL REACHED", Toast.LENGTH_SHORT).show();
        }

        showBuildingCostInfo();
    }

    private void upgradeAttackBonus() {
        if (attackBonusLevel < 5) {
            levelUpRequest("attackbonus");
            attackBonusLevel++;
            getBonus("Attack Bonus");
        } else {
            Toast.makeText(this, "MAX LEVEL REACHED", Toast.LENGTH_SHORT).show();
        }

        showAttackBonusInfo();
    }

    private void upgradeTroopCapacity() {
        if (troopCapacityLevel < 5) {
            levelUpRequest("trainingcapacity");
            troopCapacityLevel++;
            getBonus("Training Capacity");
        } else {
            Toast.makeText(this, "MAX LEVEL REACHED", Toast.LENGTH_SHORT).show();
        }

        showTroopCapacityInfo();
    }

    private void upgradeBuildingSpeed() {
        if (buildingSpeedLevel < 5) {
            levelUpRequest("buildingspeed");
            buildingSpeedLevel++;
            getBonus("Building Speed");
        } else {
            Toast.makeText(this, "MAX LEVEL REACHED", Toast.LENGTH_SHORT).show();
        }

        showBuildingSpeedInfo();
    }

    private void getResearchLevels() {
        String url = "http://coms-309-048.class.las.iastate.edu:8080/buildings/research/getallresearch/" + userID;

        // make a StringRequest to get the users from the server. Converts JSONArray into StringBuilder.
        StringRequest request = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Display response", response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject researchObject = jsonArray.getJSONObject(i);
                            if (researchObject.get("researchName").equals("Attack Bonus")) {
                                attackBonusLevel = researchObject.getInt("level");
                                getBonus("Attack Bonus");
                                showAttackBonusInfo();
                            } else if (researchObject.get("researchName").equals("Research Cost")) {
                                researchCostLevel = researchObject.getInt("level");
                                getBonus("Research Cost");
                                showResearchCostInfo();
                            } else if (researchObject.get("researchName").equals("Training Capacity")) {
                                troopCapacityLevel = researchObject.getInt("level");
                                getBonus("Training Capacity");
                                showTroopCapacityInfo();
                            } else if (researchObject.get("researchName").equals("Training Speed")) {
                                troopTrainingLevel = researchObject.getInt("level");
                                getBonus("Training Speed");
                                showTroopTrainingInfo();
                            } else if (researchObject.get("researchName").equals("Building Speed")) {
                                buildingSpeedLevel = researchObject.getInt("level");
                                getBonus("Building Speed");
                                showBuildingSpeedInfo();
                            } else {
                                buildingCostLevel = researchObject.getInt("level");
                                getBonus("Building Cost");
                                showBuildingCostInfo();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error fetching players: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        // add to the request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void getBonus(String type) {
        switch (type) {
            case "Attack Bonus":
                attackBonusBonus = (float)(attackBonusLevel * 1.05);
                break;
            case "Research Cost":
                researchCostBonus = (float)(Math.pow(0.97, researchCostLevel));
                break;
            case "Training Capacity":
                troopCapacityBonus = troopCapacityLevel * 10;
                break;
            case "Training Speed":
                troopTrainingBonus = (float)(Math.pow(0.95, troopTrainingLevel));
                break;
            case "Building Speed":
                buildingSpeedBonus = 0; // TODO
                break;
            case "Buildng Cost":
                buildingCostBonus = 0; // TODO
                break;
        }
    }

    private void levelUpRequest(String type) {
        String baseURL = "http://coms-309-048.class.las.iastate.edu:8080/buildings/research/levelresearch/" + type;

        JSONObject body = new JSONObject();
        switch (type) {
            case "attackbonus":
                body = createResearchJSON("Attack Bonus");
                break;
            case "researchcost":
                body = createResearchJSON("Research Cost");
                break;
            case "trainingcapacity":
                body = createResearchJSON("Training Capacity");
                break;
            case "trainingspeed":
                body = createResearchJSON("Training Speed");
                break;
            case "buildingspeed":
                body = createResearchJSON("Building Speed");
                break;
            case "buildingcost":
                body = createResearchJSON("Building Cost");
                break;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, baseURL, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {}
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                });

        // add to volley queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private JSONObject createResearchJSON(String type) {
        JSONObject research = new JSONObject();
        try {
            research.put("researchName", type);
            research.put("playerID", userID);
        } catch (JSONException e) {}

        return research;
    }
}
