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
import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity to manage current clan
 */
public class BuildingManagementActivity extends AppCompatActivity {

    private ArrayList<Building> List;

    private int clanID;

    private int userID;

    private int permission;

    private int userIndex;


    Button Back;

    Button Left;

    Button Right;

    Button Upgrade;

    TextView StatsText;

    TextView UpgradeText;

    private int Index;

    private int food;

    private int wood;

    private int stone;

    private int platinum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_management);

        //Get ID and ClanID
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }

        List = new ArrayList<>(); //Create a list of users
        fillList(); //Fills list of buildings

        Index = 0;


        Back = findViewById(R.id.Back);

        Left = findViewById(R.id.Left);

        Right = findViewById(R.id.Right);

        StatsText = findViewById(R.id.StatsText);

        UpgradeText = findViewById(R.id.UpgradeText);

        Upgrade = findViewById(R.id.UpgradeButton);


        /**
         * Goes back to ClanActivity
         */
        Back.setOnClickListener(new View.OnClickListener() {
            //Back button clicked
            @Override
            public void onClick(View v) {
                //goes to ClanActivity with userID
                Intent intent = new Intent(BuildingManagementActivity.this, MainActivity.class);
                intent.putExtra("ID", String.valueOf(userID));
                startActivity(intent);
            }
        });

        /**
         * Moves left through the list
         */
        Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Index == 0)
                    Index = List.size() - 1; //If the index is at 0, go all the way to the right of the list
                else Index--; //Current position to the leeft

            }
        });

        /**
         * Moves left through the list
         */
        Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Index == List.size() - 1)
                    Index = 0; //If index is at the far right, go to the beggining
                else Index++; //Goes right through the list

            }
        });

    }

        /**
         * Class to store building
         */
        private class Building{

            private int buildingID;

            private int buildingPower;

            private int productionRate;

            private String buildingName;

            private int level;

            private int foodToUpgrade;

            private int woodToUpgrade;

            private int stoneToUpgrade;

            private int platinumToUpgrade;


            Building(int buildingID, int buildingPower, int productionRate, String buildingName, int level,
                     int foodToUpgrade, int woodToUpgrade, int stoneToUpgrade, int platinumToUpgrade) {
                this.buildingID = buildingID;
                this.buildingPower = buildingPower;
                this.productionRate = productionRate;
                this.buildingName = buildingName;
                this.level = level;
                this.foodToUpgrade = foodToUpgrade;
                this.woodToUpgrade = woodToUpgrade;
                this.stoneToUpgrade = stoneToUpgrade;
                this.platinumToUpgrade = platinumToUpgrade;
            }

        }

        /**
         * Fills the list based on clan ID
         */
        private void fillList() {
            // use getall endpoint URL
            String url = "http://coms-309-048.class.las.iastate.edu:8080/buildings/getPlayerBuildings/" + Integer.toString(userID);
            List.clear();
            // make a StringRequest to get the users from the server. Converts JSONArray into StringBuilder.
            StringRequest request = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Display response", response);
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject buildingObject = jsonArray.getJSONObject(i);
                                    //List.add();
                                }

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
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
        }

    /**
     * This method gets the newly updated resource amount from the database.
     * It then displays the values on the corresponding TextView.
     */
    private void updateCurrentStorage() {
        String url = "http://coms-309-048.class.las.iastate.edu:8080/players/getPlayer/" + String.valueOf(userID);

        // makes JsonObjectRequest to get the current player. GETs the archerNum, warriorNum, mageNum, and cavalryNum
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Get current resource values
                            JSONObject resourceObject = response.getJSONObject("resources");
                            food = resourceObject.getInt("food");
                            wood = resourceObject.getInt("wood");
                            stone = resourceObject.getInt("stone");
                            platinum = resourceObject.getInt("platinum");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BuildingManagementActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BuildingManagementActivity.this, "updateCurrentStorage: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // add to volley queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void updateDisplay(){

    }

}
