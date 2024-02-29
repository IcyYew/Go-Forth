package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
 * Activity to simulate fights between two users
 */
public class FightActivity extends AppCompatActivity {
    // EditTexts for users
    private EditText user1;
    private EditText user2;

    // TextViews for confirmed users
    private TextView user1confirmed;
    private TextView user2confirmed;

    // TextView for result
    private TextView resultTextView;

    // Buttons for confirming users, going back to main, and starting the fight
    private Button confirm;
    private Button back;
    private Button fight;

    // variables to store user IDs
    private int user1ID;
    private int user2ID;

    // variables to store user power
    private int player1Power;
    private int player2Power;

    private boolean onPlayer1 = true;

    /**
     * onCreate that sets onClickListeners for the back, confirm, and fight buttons
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        // UI Initialization
        user1 = findViewById(R.id.user1Input);
        user2 = findViewById(R.id.user2Input);
        user1confirmed = findViewById(R.id.user1confirmed);
        user2confirmed = findViewById(R.id.user2confirmed);
        resultTextView = findViewById(R.id.result);
        confirm = findViewById(R.id.Button);
        back = findViewById(R.id.back);
        fight = findViewById(R.id.fightButton);

        // back button pressed
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FightActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // confirm button pressed
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user1ID = Integer.parseInt(user1.getText().toString());
                user2ID = Integer.parseInt(user2.getText().toString());

                user1confirmed.setText("User 1: " + user1ID);
                user2confirmed.setText("User 2: " + user2ID);
            }
        });

        // fight button pressed
        fight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFight(user1ID, user2ID);
            }
        });
    }

    /**
     * Simulates fight by calling the players/fight/{user1}/{user2} endpoint. Displays returned value
     *
     * @param player1ID
     * @param player2ID
     */
    private void startFight(int player1ID, int player2ID) {
        // use the players/fight/{user1}/{user2} endpoint
        String url = "http://coms-309-048.class.las.iastate.edu:8080/players/fight/" + player1ID + "/" + player2ID;

        // Makes StringRequest at the endpoint and sets the TextView to the result
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleFightResult(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error during fight: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // add request to volley queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * Method to handle fight results and declare a winner
     *
     * @param response
     */
    private void handleFightResult(String response) {
        Log.d("Response", response);
        // parse the response
        String[] parts = response.split("\n");
        String result = parts[0];
        String player1Info = parts[1];
        String player2Info = parts[3];

        Log.d("Player1 ID", "Player ID: " + player1Info);
        Log.d("Player2 ID", "Player ID: " + player2Info);

        int player1ID = Integer.parseInt(player1Info);
        int player2ID = Integer.parseInt(player2Info);

        // display the appropriate message based on the result
        String message;
        if (result.equals("ATTACKER_WIN")) {
            message = "Attacker wins! (User 1)";
        } else if (result.equals("DEFENDER_WIN")) {
            message = "Defender wins! (User 2)";
        } else {
            message = "It's a draw!";
        }

        getPowerLevel(message, player1ID);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        getPowerLevel(message, player2ID);
    }

    /**
     * Method to handle getting the power levels of users after a fight
     *
     * @param message
     * @param playerID
     */
    private void getPowerLevel(String message, int playerID) {
        String url = "http://coms-309-048.class.las.iastate.edu:8080/players/getPlayer/" + String.valueOf(playerID);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (onPlayer1) {
                                player1Power = response.getInt("power");
                                onPlayer1 = false;
                                Log.d("Power 1", "Power: " + player1Power);
                            } else {
                                player2Power = response.getInt("power");
                                Log.d("Power 2", "Power: " + player2Power);
                                resultTextView.setText("\n\n" + message + "\n\nNew Power Levels:\n\n" +
                                        "Player 1 Power: " + String.valueOf(player1Power) + "\n" +
                                        "Player 2 Power: " + String.valueOf(player2Power));
                                onPlayer1 = true;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FightActivity.this, "Error fetching power", Toast.LENGTH_SHORT).show();
                    }
                });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
