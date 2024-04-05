package com.example.app;

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

import java.util.Objects;


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


        logIntoCurrentClan();

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

        ClanManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClanActivity.this, ClanManagement.class);
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });

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

        ClanChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goes to MainActivity with userID
                Intent intent = new Intent(ClanActivity.this, ClanChat.class);
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });

        JoinClan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goes to MainActivity with userID
                Intent intent = new Intent(ClanActivity.this, ClanJoinActivity.class);
                intent.putExtra("ID", userID);
                startActivity(intent);
            }
        });

        CreateClan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goes to MainActivity with userID
                Intent intent = new Intent(ClanActivity.this, ClanCreateActivity.class);
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


    private void logIntoCurrentClan(){
        String url = "http://coms-309-048.class.las.iastate.edu:8080/players/getall";

        // make a StringRequest to get the users from the server. Converts JSONArray into StringBuilder.
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Display response", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject playerObject = jsonArray.getJSONObject(userID - 1);
                            clanID = playerObject.getInt("clanMembershipID");
                            ClanName.setText("Clan ID: " + Integer.toString(clanID));
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
