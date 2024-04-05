package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Objects;

/**
 * Activity responsible for main menu functionality.
 *
 * @author Josh Dwight
 * @author Nicholas Lynch
 */
public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private Button signupButton;
    private Button troopManagementButton;
    private Button displayButton;
    private Button fightButton;
    private Button resourceButton;
    private Button overworldButton;
    private Button globalChatButton;
    private Button clanChatButton;
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

        //UI initialization
        loginButton = findViewById(R.id.loginButton);

        signupButton = findViewById(R.id.signupButton);

        troopManagementButton = findViewById(R.id.troopManagementButton);

        displayButton = findViewById(R.id.displayButton);

        fightButton = findViewById(R.id.fightButton);

        resourceButton = findViewById(R.id.ResourceButton);

        clanButton = findViewById(R.id.ClanChat);

        globalChatButton = findViewById(R.id.globalChatButton);

        clanChatButton = findViewById(R.id.clanChatButton);

        UID = findViewById(R.id.ID);


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

        //Login button pressed
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast toast = Toast.makeText(MainActivity.this, "Login Pressed", Toast.LENGTH_SHORT);
                //toast.show();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });

        clanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClanActivity.class);
                intent.putExtra("ID", Integer.toString(userID));
                startActivity(intent);
            }
        });

        //Signup button pressed
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast toast = Toast.makeText(MainActivity.this, "Signup Pressed", Toast.LENGTH_SHORT);
                //toast.show();

                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                intent.putExtra("ID", userID);
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

        //Troop Management pressed
        troopManagementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TroopManagementActivity.class);
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });

        // display users button pressed
        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });

        // fight button pressed
        fightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FightActivity.class);
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });

        overworldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OverworldActivity.class);
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });

        globalChatButton.setOnClickListener(view -> {
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
            Intent intent = new Intent(this, ChatActivity1.class);
            intent.putExtra("ID", userID);
            startActivity(intent);
        });

        clanChatButton.setOnClickListener(view -> {
            String url = "http://coms-309-048.class.las.iastate.edu:8080/players/getPlayer/" + String.valueOf(userID);

            // makes JsonObjectRequest to get the current player. GETs the archerNum, warriorNum, mageNum, and cavalryNum
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String serverUrl = "ws://10.0.2.2:8080/chat/clan/" + response.getString("userName");
                                Log.d("URL", serverUrl);

                                // Establish WebSocket connection and set listener
                                WebSocketManager2.getInstance().connectWebSocket(serverUrl);
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
            Intent intent = new Intent(this, ChatActivity2.class);
            intent.putExtra("ID", userID);
            startActivity(intent);
        });
    }
}