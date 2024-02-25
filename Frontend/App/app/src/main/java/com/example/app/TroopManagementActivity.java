package com.example.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class TroopManagementActivity extends AppCompatActivity {

    private int userID;

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
    private TextView archersCountTextView;
    private TextView knightsCountTextView;
    private TextView magesCountTextView;
    private TextView cavalryCountTextView;

    // checkboxes
    private CheckBox archersCheckbox;
    private CheckBox knightsCheckbox;
    private CheckBox magesCheckbox;
    private CheckBox cavalryCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troop_management);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }

        archersToTrainCountTextView = findViewById(R.id.archersToTrainCount);
        knightsToTrainCountTextView = findViewById(R.id.knightsToTrainCount);
        magesToTrainCountTextView = findViewById(R.id.magesToTrainCount);
        cavalryToTrainCountTextView = findViewById(R.id.cavalryToTrainCount);

        archersCheckbox = findViewById(R.id.archersCheckbox);
        knightsCheckbox = findViewById(R.id.knightsCheckbox);
        magesCheckbox = findViewById(R.id.magesCheckbox);
        cavalryCheckbox = findViewById(R.id.cavalryCheckbox);

        Button backButton = findViewById(R.id.backButton);

        getPlayerData();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TroopManagementActivity.this, MainActivity.class);
                intent.putExtra("ID", String.valueOf(userID));
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
        updateTroopCounts(archersToTrainCount, knightsToTrainCount, magesToTrainCount, cavalryToTrainCount);
        getPlayerData();

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

    private void getPlayerData() {
        archersCountTextView = findViewById(R.id.archersCount);
        knightsCountTextView = findViewById(R.id.knightsCount);
        magesCountTextView = findViewById(R.id.magesCount);
        cavalryCountTextView = findViewById(R.id.cavalryCount);

        String url = "http://10.0.2.2:8080/players/getPlayer/" + String.valueOf(userID);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject troopsObject = response.getJSONObject("troops");
                            int archersCount = troopsObject.getInt("archerNum");
                            int knightsCount = troopsObject.getInt("warriorNum");
                            int magesCount = troopsObject.getInt("mageNum");
                            int cavalryCount = troopsObject.getInt("cavalryNum");

                            archersCountTextView.setText(String.valueOf(archersCount));
                            knightsCountTextView.setText(String.valueOf(knightsCount));
                            magesCountTextView.setText(String.valueOf(magesCount));
                            cavalryCountTextView.setText(String.valueOf(cavalryCount));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(TroopManagementActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TroopManagementActivity.this, "Error fetching player data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void updateTroopCounts(int archersCount, int knightsCount, int magesCount, int cavalryCount) {
        String baseURL = "http://10.0.2.2:8080/players/addtroops/" + userID;

        JSONObject archerJSON = createTroopJSON("ARCHER", archersCount);
        JSONObject warriorJSON = createTroopJSON("WARRIOR", knightsCount);
        JSONObject mageJSON = createTroopJSON("MAGE", magesCount);
        JSONObject cavalryJSON = createTroopJSON("CAVALRY", cavalryCount);

        makePOSTRequest(baseURL, archerJSON);
        makePOSTRequest(baseURL, warriorJSON);
        makePOSTRequest(baseURL, mageJSON);
        makePOSTRequest(baseURL, cavalryJSON);
    }

    private JSONObject createTroopJSON(String troopType, int quantity) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("troopType", troopType);
            jsonObject.put("quantity", quantity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void makePOSTRequest(String url, JSONObject requestBody) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getPlayerData();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TroopManagementActivity.this, "Error updating troops: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}