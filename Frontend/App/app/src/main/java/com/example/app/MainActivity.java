package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Activity responsible for main menu functionality.
 *
 * @author Josh Dwight
 * @author Nicholas Lynch
 */
public class MainActivity extends AppCompatActivity {
    private ImageView troopManagementButton;
    private ImageView displayButton;
    private ImageView resourceButton;
    private ImageView overworldButton;
    private ImageView globalChatButton;
    private ImageView clanButton;
    private ImageView researchButton;
    private ImageView signoutButton;

    private ImageView BuildingButton;
    private int userID;
    private TextView UID;
    private String username;

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

        troopManagementButton = findViewById(R.id.troopManagementButton);
        displayButton = findViewById(R.id.displayButton);
        resourceButton = findViewById(R.id.ResourceButton);
        clanButton = findViewById(R.id.Clan);
        globalChatButton = findViewById(R.id.globalChatButton);
        overworldButton = findViewById(R.id.OverworldButton);
        researchButton = findViewById(R.id.ResearchButton);
        signoutButton = findViewById(R.id.signout);
        BuildingButton = findViewById(R.id.BuildingButton);

        UID = findViewById(R.id.Kills);

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

        ImageView backgroundGif = findViewById(R.id.backgroundGif);
        Glide.with(this)
                .asGif()
                .load(R.raw.town)
                .into(backgroundGif);

        Glide.with(this)
                .asGif()
                .load(R.raw.research)
                .into(researchButton);

        // TODO NEED TO TRY AND FIX BUTTON PRESS NOT CHANGING IMAGEVIEW
        clanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the button appearance to the pressed state
                clanButton.setImageResource(R.drawable.clanb_pressed);

                // Schedule the task to revert the button back to the unpressed state
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Revert the button appearance back to the unpressed state
                        clanButton.setImageResource(R.drawable.clanb);

                        // Start the new activity
                        Intent intent = new Intent(MainActivity.this, ClanActivity.class);
                        intent.putExtra("ID", Integer.toString(userID));
                        startActivity(intent);
                    }
                }, 250); // 500 milliseconds delay
            }
        });

        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LaunchActivity.class);
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

        //Building button pressed
        BuildingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BuildingManagementActivity.class);
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

        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the button appearance to the pressed state
                displayButton.setImageResource(R.drawable.leaderboard_pressed);

                // Schedule the task to revert the button back to the unpressed state
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Revert the button appearance back to the unpressed state
                        displayButton.setImageResource(R.drawable.leaderboard);

                        // Start the new activity
                        Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                        intent.putExtra("ID", userID);
                        startActivity(intent);
                    }
                }, 250); // 500 milliseconds delay
            }
        });


        overworldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the button appearance to the pressed state
                overworldButton.setImageResource(R.drawable.go_forth_pressed);

                // Schedule the task to revert the button back to the unpressed state
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Revert the button appearance back to the unpressed state
                        overworldButton.setImageResource(R.drawable.go_forth);

                        // Start the new activity
                        Intent intent = new Intent(MainActivity.this, OverworldActivity.class);
                        intent.putExtra("ID", userID);
                        startActivity(intent);
                    }
                }, 250); // 500 milliseconds delay
            }
        });

        researchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResearchActivity.class);
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });

        globalChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the button appearance to the pressed state
                globalChatButton.setImageResource(R.drawable.global_pressed);

                // Schedule the task to revert the button back to the unpressed state
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Revert the button appearance back to the unpressed state
                        globalChatButton.setImageResource(R.drawable.global);

                        String url = "http://coms-309-048.class.las.iastate.edu:8080/players/getPlayer/" + String.valueOf(userID);

                        // makes JsonObjectRequest to get the current player. GETs the archerNum, warriorNum, mageNum, and cavalryNum
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            username = response.getString("userName");

                                            Log.d("Username", username);

                                            String domain = "ws://coms-309-048.class.las.iastate.edu:8080/chat/globalchat/";

                                            String serverUrl = domain + username;

                                            Log.d("URL", serverUrl);

                                            // Establish WebSocket connection and set listener
                                            WebSocketManager1.getInstance().connectWebSocket(serverUrl);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(MainActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(MainActivity.this, "Error fetching player data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                        // add to volley queue
                        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

                        // got to chat activity
                        Intent intent = new Intent(MainActivity.this, ChatActivity1.class);
                        intent.putExtra("ID", userID);
                        startActivity(intent);
                    }
                }, 250); // 500 milliseconds delay
            }
        });

        clanButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ClanActivity.class);
            intent.putExtra("ID", Integer.toString(userID));
            startActivity(intent);
        });
    }
}