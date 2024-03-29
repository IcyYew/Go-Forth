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

/**
 * Activity for viewing the troops a user has as well as updating the troops by adding new ones.
 *
 * @author Josh Dwight
 */
public class TroopManagementActivity extends AppCompatActivity {
    // stores userID so it can track across activities
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

    /**
     * onCreate sets onClickListeners to all of the buttons and initializes UI elements. Also gets extras.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troop_management);

        // get extras so userID can track across activities
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }

        // UI initialization
        archersToTrainCountTextView = findViewById(R.id.archersToTrainCount);
        knightsToTrainCountTextView = findViewById(R.id.knightsToTrainCount);
        magesToTrainCountTextView = findViewById(R.id.magesToTrainCount);
        cavalryToTrainCountTextView = findViewById(R.id.cavalryToTrainCount);

        archersCheckbox = findViewById(R.id.archersCheckbox);
        knightsCheckbox = findViewById(R.id.knightsCheckbox);
        magesCheckbox = findViewById(R.id.magesCheckbox);
        cavalryCheckbox = findViewById(R.id.cavalryCheckbox);

        Button backButton = findViewById(R.id.backButton);

        // get troop data from server
        getPlayerData();

        // back button pressed
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TroopManagementActivity.this, MainActivity.class);
                intent.putExtra("ID", String.valueOf(userID));
                startActivity(intent);
            }
        });

        // train one button pressed (update correct troop to train count)
        Button trainOneButton = findViewById(R.id.trainOneButton);
        trainOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainTroops(1);
            }
        });

        // train 10 button pressed (update correct troop to train count)
        Button trainTenButton = findViewById(R.id.trainTenButton);
        trainTenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainTroops(10);
            }
        });

        // train 50 button pressed (update correct troop to train count)
        Button trainFiftyButton = findViewById(R.id.trainFiftyButton);
        trainFiftyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainTroops(50);
            }
        });

        // train 100 button pressed (update correct troop to train count)
        Button trainHundredButton = findViewById(R.id.trainHundredButton);
        trainHundredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainTroops(100);
            }
        });

        // confirm training button pressed
        Button confirmTrainingButton = findViewById(R.id.confirmTrainingButton);
        confirmTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmTraining();
            }
        });
    }

    /**
     * Checks which troops' checkboxes are currently checked and updates their "to train" counts accordingly
     *
     * @param amount
     */
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

    /**
     * When training is confirmed, update the troop counts (addtroops endpoint) and get the new troop counts from server (getPlayer endpoint)
     */
    private void confirmTraining() {
        // server stuff
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

    /**
     * Uses the /players/getPlayer/{userID} endpoint to get the troop counts of the currently selected user.
     *
     * @apiNote GET
     */
    private void getPlayerData() {
        archersCountTextView = findViewById(R.id.archersCount);
        knightsCountTextView = findViewById(R.id.knightsCount);
        magesCountTextView = findViewById(R.id.magesCount);
        cavalryCountTextView = findViewById(R.id.cavalryCount);

        // use the /players/getplayer/{userID} endpoint
        String url = "http://coms-309-048.class.las.iastate.edu:8080/players/getPlayer/" + String.valueOf(userID);

        // makes JsonObjectRequest to get the current player. GETs the archerNum, warriorNum, mageNum, and cavalryNum
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

        // add to volley queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * Uses the /players/addtroops/{userID} endpoint to POST the new troop counts to the server
     *
     * @param archersCount number of archers to add
     * @param warriorsCount number of warrior to add
     * @param magesCount number of mages to add
     * @param cavalryCount number of cavalry to add
     */
    private void updateTroopCounts(int archersCount, int warriorsCount, int magesCount, int cavalryCount) {
        String baseURL = "http://coms-309-048.class.las.iastate.edu:8080/players/addtroops/" + userID;

        JSONObject archerJSON = createTroopJSON("ARCHER", archersCount);
        JSONObject warriorJSON = createTroopJSON("WARRIOR", warriorsCount);
        JSONObject mageJSON = createTroopJSON("MAGE", magesCount);
        JSONObject cavalryJSON = createTroopJSON("CAVALRY", cavalryCount);

        makePOSTRequest(baseURL, archerJSON);
        makePOSTRequest(baseURL, warriorJSON);
        makePOSTRequest(baseURL, mageJSON);
        makePOSTRequest(baseURL, cavalryJSON);
    }

    /**
     * create a troop JSON object of the selected type to use in POST
     *
     * @param troopType type of troop
     * @param quantity number of troops
     * @return returns a troop JSON
     */
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

    /**
     * Makes a POST request using the given url and request body
     *
     * @param url
     * @param requestBody
     *
     * @apiNote POST
     */
    private void makePOSTRequest(String url, JSONObject requestBody) {
        // make a JsonObjectRequest to POST to server
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

        // add to volley queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}