package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Shows all clans and their corresponding IDS
 */
public class ClanDisplay extends AppCompatActivity {

    private int userID;

    private Button Back;

    private Button Power;

    private Button Name;

    private Button ID;

    private TextView Text;

    private ArrayList<Clan> List;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clan_display);

        //Get ID
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }

        Back = findViewById(R.id.Back);

        Power = findViewById(R.id.Power);

        Name = findViewById(R.id.Name);

        ID = findViewById(R.id.ID);

        Text = findViewById(R.id.Text);

        List = new ArrayList<>();

        getClans();

        /**
         * Goes back to ClanActivity
         */
        Back.setOnClickListener(new View.OnClickListener() {
            //Back button clicked
            @Override
            public void onClick(View v) {
                //goes to MainActivity with userID
                Intent intent = new Intent(ClanDisplay.this, ClanActivity.class);
                intent.putExtra("ID", String.valueOf(userID));
                startActivity(intent);
            }
        });

        Power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(List, new sortByPower());
                displayClans();
            }
        });

        Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(List, new sortByName());
                displayClans();
            }
        });

        ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(List, new sortByID());
                displayClans();
            }
        });

    }

    /**
     * Displays all clans
     */
    private void getClans() {
        // use getallclans endpoint URL
        String url = "http://coms-309-048.class.las.iastate.edu:8080/clan/getallclans";

        // make a StringRequest to get the users from the server. Converts JSONArray into StringBuilder.
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Display response", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            //StringBuilder clansString = new StringBuilder();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //Prints clan ID and clan name
                                JSONObject playerObject = jsonArray.getJSONObject(i);
                                List.add(new Clan(playerObject.getInt("clanID"), (int) playerObject.getDouble("totalClanPower"), playerObject.getString("clanName")));
                                /*
                                clansString.append("Clan ID: ").append(playerObject.getInt("clanID")).append("\n");
                                clansString.append("Clan name: ").append(playerObject.getString("clanName")).append("\n");
                                //clansString.append("Clan power: ").append(Double.toString(playerObject.getDouble("totalClanPower"))).append("\n");
                                clansString.append("\n");
                                 */
                            }
                            Collections.sort(List, new sortByPower());
                            displayClans();

                            //Text.setText(clansString.toString()); //Sets text onscreen
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error fetching clans: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // add to the request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request); //Add to queue
    }
    /**
     * Class to store user data
     */
    private class Clan {

        private int clanID;

        private int clanPower;

        private String name;

        Clan(int clanID, int clanPower, String name) {
            this.clanID = clanID;
            this.clanPower = clanPower;
            this.name = name;
        }

    }

    private class sortByPower implements Comparator<Clan> {
        public int compare(Clan a, Clan b) {
            return b.clanPower - a.clanPower;
        }
    }

    private class sortByName implements Comparator<Clan> {
        public int compare(Clan a, Clan b) {
            return a.name.compareTo(b.name);
        }
    }

    private class sortByID implements Comparator<Clan> {
        public int compare(Clan a, Clan b) {
            return a.clanID - b.clanID;
        }
    }

    private void displayClans(){
        StringBuilder playersString = new StringBuilder();
        for(int i = 0; i < List.size(); i++){
            playersString.append("Rank: ").append(i);
            playersString.append(" Clan: ").append(List.get(i).name).append(" ID: ").append(List.get(i).clanID).append(" Power: ").append(List.get(i).clanPower);
            playersString.append("\n");
        }
        Text.setText(playersString.toString());
    }
}