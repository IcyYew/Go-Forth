package com.example.app;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
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

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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

    // stores end times
    private String archerFinalDate;
    private String warriorFinalDate;
    private String mageFinalDate;
    private String cavalryFinalDate;

    // textviews
    private TextView archersToTrainCountTextView;
    private TextView knightsToTrainCountTextView;
    private TextView magesToTrainCountTextView;
    private TextView cavalryToTrainCountTextView;
    private TextView archersCountTextView;
    private TextView knightsCountTextView;
    private TextView magesCountTextView;
    private TextView cavalryCountTextView;
    private TextView trainingTimeValue;

    // checkboxes
    private CheckBox archersCheckbox;
    private CheckBox knightsCheckbox;
    private CheckBox magesCheckbox;
    private CheckBox cavalryCheckbox;

    // Countdown timer variable
    private CountDownTimer countDownTimer;
    private long totalTimeInMillis = 0;
    private long timeLeftInMillis = 0;
    private SharedPreferences prefs;
    private static final String PREF_NAME = "troop_training_timer";
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

        // Initialize SharedPreferences
        prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        archersToTrainCount = prefs.getInt("archersToTrainCount", 0);
        knightsToTrainCount = prefs.getInt("knightsToTrainCount", 0);
        magesToTrainCount = prefs.getInt("magesToTrainCount", 0);
        cavalryToTrainCount = prefs.getInt("cavalryToTrainCount", 0);

        // UI initialization
        archersToTrainCountTextView = findViewById(R.id.archersToTrainCount);
        knightsToTrainCountTextView = findViewById(R.id.knightsToTrainCount);
        magesToTrainCountTextView = findViewById(R.id.magesToTrainCount);
        cavalryToTrainCountTextView = findViewById(R.id.cavalryToTrainCount);
        trainingTimeValue = findViewById(R.id.trainingTimeValue);

        archersToTrainCountTextView.setText(String.valueOf(archersToTrainCount));
        knightsToTrainCountTextView.setText(String.valueOf(knightsToTrainCount));
        magesToTrainCountTextView.setText(String.valueOf(magesToTrainCount));
        cavalryToTrainCountTextView.setText(String.valueOf(cavalryToTrainCount));


        archersCheckbox = findViewById(R.id.archersCheckbox);
        knightsCheckbox = findViewById(R.id.knightsCheckbox);
        magesCheckbox = findViewById(R.id.magesCheckbox);
        cavalryCheckbox = findViewById(R.id.cavalryCheckbox);

        Button backButton = findViewById(R.id.backButton);

        // get troop data from server
        getPlayerData();

        // Load remaining time from SharedPreferences
        timeLeftInMillis = prefs.getLong("millisLeft", 0);
        updateCountdownText();
        if (timeLeftInMillis != 0) {
            startCountdownTimer();
        }


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
                startCountdownTimer();
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
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("archersToTrainCount", archersToTrainCount);
        editor.putInt("knightsToTrainCount", knightsToTrainCount);
        editor.putInt("magesToTrainCount", magesToTrainCount);
        editor.putInt("cavalryToTrainCount", cavalryToTrainCount);
        editor.apply();
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

        // Clear SharedPreferences for troop counts
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("archersToTrainCount");
        editor.remove("knightsToTrainCount");
        editor.remove("magesToTrainCount");
        editor.remove("cavalryToTrainCount");
        editor.apply();
    }

    /**
     * Uses the /players/getPlayer/{userID} endpoint to get the troop counts of the currently selected user.
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

    /**
     * Calculates the total time to train troops
     *
     * @return time
     */
    private void calculateTotalTrainingTime() {
        JSONObject archers = createTroopJSON("ARCHER", archersToTrainCount);
        JSONObject warriors = createTroopJSON("WARRIOR", knightsToTrainCount);
        JSONObject mages = createTroopJSON("MAGE", magesToTrainCount);
        JSONObject cavalry = createTroopJSON("CAVALRY", cavalryToTrainCount);

        getTrainingTime(archers);
        getTrainingTime(warriors);
        getTrainingTime(mages);
        getTrainingTime(cavalry);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        getUpdatedTime();
    }

    /**
     * Method used to update countdown textview.
     */
    private void updateCountdownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        trainingTimeValue.setText(timeLeftFormatted);
    }

    /**
     * Method that starts the timer.
     * Also declares what happens on tick and on finish.
     */
    private void startCountdownTimer() {
        // Calculate total training time based on troops count
        calculateTotalTrainingTime();
    }

    /**
     * Method that runs when you switch to a different activity.
     */
    @Override
    protected void onPause() {
        super.onPause();
        // Save remaining time to SharedPreferences
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("millisLeft", timeLeftInMillis);
        editor.apply();
        // Cancel the countdown timer
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void getTrainingTime(JSONObject troopJSON) {
        String url = "http://coms-309-048.class.las.iastate.edu:8080/players/calculateTrainingTime/" + userID;
        // make a JsonObjectRequest to POST to server
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, troopJSON,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (troopJSON.get("troopType").equals("ARCHER")){
                                archerFinalDate = response.getString("archerFinalDate");
                            } else if (troopJSON.get("troopType").equals("WARRIOR")) {
                                warriorFinalDate = response.getString("warriorFinalDate");
                            } else if (troopJSON.get("troopType").equals("MAGE")) {
                                mageFinalDate = response.getString("mageFinalDate");
                            } else {
                                cavalryFinalDate = response.getString("cavalryFinalDate");
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TroopManagementActivity.this, "Error calculating time: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // add to volley queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void getUpdatedTime() {
        // use the /players/getplayer/{userID} endpoint
        String url = "http://coms-309-048.class.las.iastate.edu:8080/players/getPlayer/" + String.valueOf(userID);

        // makes JsonObjectRequest to get the current player. GETs the archerNum, warriorNum, mageNum, and cavalryNum
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            archerFinalDate = response.getString("archerFinalDate");
                            warriorFinalDate = response.getString("warriorFinalDate");
                            mageFinalDate = response.getString("mageFinalDate");
                            cavalryFinalDate = response.getString("cavalryFinalDate");

                            Log.d("Archer end", "Archer end time: " + archerFinalDate);
                            Log.d("Warrior end", "Warrior end time: " + warriorFinalDate);
                            Log.d("Mage end", "Mage end time: " + mageFinalDate);
                            Log.d("Cavalry end", "Cavalry end time: " + cavalryFinalDate);

                            totalTimeInMillis = doTimeStuff();

                            Log.d("Total Time", "Total time = " + totalTimeInMillis);

                            if (timeLeftInMillis <= 5) {
                                timeLeftInMillis = totalTimeInMillis;
                            }

                            // Start countdown timer
                            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    timeLeftInMillis = millisUntilFinished;
                                    updateCountdownText();
                                }

                                @Override
                                public void onFinish() {
                                    // Reset countdown timer variables
                                    timeLeftInMillis = 0;
                                    updateCountdownText();
                                    confirmTraining();
                                }
                            }.start();
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

    private long doTimeStuff() {
        DateTimeFormatter format;
        LocalDateTime now;
        long totalDifference = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            format = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            LocalDateTime archerFinal = LocalDateTime.parse(archerFinalDate, format);
            LocalDateTime mageFinal = LocalDateTime.parse(mageFinalDate, format);
            LocalDateTime cavalryFinal = LocalDateTime.parse(cavalryFinalDate, format);

            now = LocalDateTime.now();

            Duration archerDuration = Duration.between(now, archerFinal);
            Duration mageDuration = Duration.between(now, mageFinal);
            Duration cavalryDuration = Duration.between(now, cavalryFinal);

            if (archersToTrainCount > 0) {
                totalDifference += archerDuration.toMillis() - 7000;
            }
            if (magesToTrainCount > 0) {
                totalDifference += mageDuration.toMillis() - 7000;
            }
            if (cavalryToTrainCount > 0) {
                totalDifference += cavalryDuration.toMillis() - 7000;
            }
        }

        return totalDifference;
    }
}