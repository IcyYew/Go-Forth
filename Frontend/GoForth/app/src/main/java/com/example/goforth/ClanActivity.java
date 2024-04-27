package com.example.goforth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Main Screen for clans
 */
public class ClanActivity extends AppCompatActivity {

    private int userID;

    Button back;

    Button DisplayClans;

    Button ClanManagement;

    Button ClanChat;

    Button JoinClan;

    Button CreateClan;

    TextView ClanName;

    Button LeaveClan;

    private int clanID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clan);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = Integer.parseInt(extras.getString("ID"));
        }

        back = findViewById(R.id.Back);

        ClanManagement = findViewById(R.id.ClanManagement);

        ClanChat = findViewById(R.id.ClanChat);

        JoinClan =  findViewById(R.id.JoinClan);

        CreateClan = findViewById(R.id.Create);

        ClanName =  findViewById(R.id.Name);

        DisplayClans =  findViewById(R.id.ClanList);

        LeaveClan =  findViewById(R.id.LeaveClan);


        logIntoCurrentClan(); //Loads current clan information for user
        /**
         * Goes back to main activity
         */
        back.setOnClickListener(new View.OnClickListener() {
            //Back button clicked
            @Override
            public void onClick(View v) {
                //goes to MainActivity with userID
                Intent intent = new Intent(ClanActivity.this, MainActivity.class);
                intent.putExtra("ID", String.valueOf(userID));
                startActivity(intent);
            }
        });
        /**
         * Goes to clan manager
         */
        ClanManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClanActivity.this, ClanManagement.class);
                intent.putExtra("ID", userID);
                intent.putExtra("clanID", clanID);
                startActivity(intent);
            }
        });
        /**
         * Leaves current clan
         */
        LeaveClan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://coms-309-048.class.las.iastate.edu:8080/clans/removemember";

                // Create a JSONObject with the clan's details
                JSONObject requestBody = new JSONObject();
                try {
                    requestBody.put("clanID", clanID);
                    requestBody.put("playerID", userID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Create a JsonObjectRequest with the POST method
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Handle successful response from the server
                                Log.d("Clan leave", "clan left: " + response.toString());


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle error response from the server
                                Log.e("Clan Leave", "Error leaving clan: " + error.getMessage());
                            }
                        });

                // add to volley request queue
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
                logIntoCurrentClan();
            }
        });
        /**
         * Goes to CreateClanActivity
         */
        CreateClan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goes to MainActivity with userID
                Intent intent = new Intent(ClanActivity.this, ClanCreateActivity.class);
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });
        /**
         * Goes to JoinClanActivity
         */
        JoinClan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goes to MainActivity with userID
                Intent intent = new Intent(ClanActivity.this, ClanJoinActivity.class);
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });
        /**
         * Goes to clan chat
         */
        ClanChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://coms-309-048.class.las.iastate.edu:8080/players/getPlayer/" + String.valueOf(userID);

                // makes JsonObjectRequest to get the current player. GETs the archerNum, warriorNum, mageNum, and cavalryNum
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String serverUrl = "ws://coms-309-048.class.las.iastate.edu:8080/chat/clan/" + response.getString("userName"); //Get url to join server
                                    Log.d("URL", serverUrl);

                                    // Establish WebSocket connection and set listener
                                    ClanChatManager.getInstance().connectWebSocket(serverUrl); //Connects to the clan chat server
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(ClanActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ClanActivity.this, "Error fetching player data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                // add to volley queue
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

                // got to chat activity
                Intent intent = new Intent(ClanActivity.this, ClanChat.class);
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });

        DisplayClans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goes to MainActivity with userID
                Intent intent = new Intent(ClanActivity.this, ClanDisplay.class);
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });
    }

    /**
     * Gets players current clan
     */
    private void logIntoCurrentClan(){
        String url = "http://coms-309-048.class.las.iastate.edu:8080/players/getall";

        // make a StringRequest to get the user from the server. Converts JSONArray into StringBuilder.
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Display response", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject playerObject = jsonArray.getJSONObject(userID - 1);
                            clanID = playerObject.getInt("clanMembershipID"); //Put players clan into clan ID
                            ClanName.setText("Clan ID: " + Integer.toString(clanID)); //Set clanname
                            }
                         catch (JSONException e) {
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
}
