package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity to manage current resources
 */
public class BuildingManagementActivity extends AppCompatActivity {

    private ArrayList<Building> List;


    private int userID;

    Button Back;

    Button Left;

    Button Right;

    Button Upgrade;

    TextView StatsText;

    TextView UpgradeText;

    TextView BuildingText;

    private int Index;

    private int wood;

    private int stone;

    ImageView BuildingGif;

    private int mainLevel;

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
        updateCurrentStorage();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        fillList(); //Fills list of buildings

        Index = 0;


        Back = findViewById(R.id.Back);

        Left = findViewById(R.id.Left);

        Right = findViewById(R.id.Right);

        StatsText = findViewById(R.id.StatsText);

        UpgradeText = findViewById(R.id.UpgradeText);

        BuildingText = findViewById(R.id.BuildingText);

        Upgrade = findViewById(R.id.UpgradeButton);

        BuildingGif = findViewById(R.id.BuildingGif);



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
                else Index--; //Current position to the left
                updateDisplay();
            }
        });

        /**
         * Moves right through the list
         */
        Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Index == List.size() - 1)
                    Index = 0; //If index is at the far right, go to the beggining
                else Index++; //Goes right through the list
                updateDisplay();

            }
        });

        Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Index == 0)
                    Index = List.size() - 1; //If the index is at 0, go all the way to the right of the list
                else Index--; //Current position to the left
                updateDisplay();
            }
        });

        /**
         * Upgrades current buildings
         */
        Upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(List.get(Index).level == 5  || (List.get(Index).level == mainLevel && !(List.get(Index).buildingName.equals("MAINBUILDING")))){
                    Toast.makeText(getApplicationContext(), "Building max level", Toast.LENGTH_SHORT).show();
                }
                else if(List.get(Index).woodToUpgrade > wood || List.get(Index).stoneToUpgrade > stone){
                    Toast.makeText(getApplicationContext(), "Insufficient funds", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(List.get(Index).buildingName.equals("MAINBUILDING"))
                        mainLevel++;
                    upgradeCurrent();
                }
            }
        });

    }

        /**
         * Class to store building
         */
        private class Building{

            private int buildingPower;

            private double productionRate;

            private String buildingName;

            private int level;

            private int woodToUpgrade;

            private int stoneToUpgrade;

            private int maxCapacity;

            private String type;
            Building(int buildingPower, double productionRate, String buildingName,
                     int level, int woodToUpgrade, int stoneToUpgrade, int maxCapacity, String type) {this.buildingPower = buildingPower;
                this.productionRate = productionRate;
                this.buildingName = buildingName;
                this.level = level;
                this.woodToUpgrade = woodToUpgrade;
                this.stoneToUpgrade = stoneToUpgrade;
                this.maxCapacity = maxCapacity;
                this.type = type;
            }
        }

        /**
         * Fills the list based on players buildings
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
                                    String name = buildingObject.getString("buildingType");
                                    if(name.equals("FARM") || name.equals("LUMBERYARD") || name.equals("QUARRY") || name.equals("PLATINUMMINE")) {
                                        List.add(new Building(buildingObject.getInt("power"),
                                                (double)buildingObject.getInt("resourceProductionRate"), name,
                                                buildingObject.getInt("level"), buildingObject.getInt("woodUpgradeCost"),
                                                buildingObject.getInt("stoneUpgradeCost"), buildingObject.getInt("resourceLimit"), "Resource"));
                                    }
                                    else if(name.equals("WARRIORSCHOOL") || name.equals("STABLES") || name.equals("ARCHERYRANGE") || name.equals("MAGETOWER")){
                                        List.add(new Building(buildingObject.getInt("power"),
                                                buildingObject.getDouble("trainingTime"), name,
                                                buildingObject.getInt("level"), buildingObject.getInt("woodUpgradeCost"),
                                                buildingObject.getInt("stoneUpgradeCost"), buildingObject.getInt("trainingCapacity"), "Training"));
                                    }
                                    else if(name.equals("MAINBUILDING")){
                                        mainLevel = buildingObject.getInt("level");
                                        List.add(new Building(buildingObject.getInt("power"),
                                                0, name,
                                                buildingObject.getInt("level"), buildingObject.getInt("woodUpgradeCost"),
                                                buildingObject.getInt("stoneUpgradeCost"), 0, "Main"));
                                    }
                                    else if(name.equals("RESEARCHBUILDING")){
                                        List.add(new Building(buildingObject.getInt("power"),
                                                0, name,
                                                buildingObject.getInt("level"), buildingObject.getInt("woodUpgradeCost"),
                                                buildingObject.getInt("stoneUpgradeCost"), 0, "Research"));
                                    }
                                }
                                updateDisplay();
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
     * This method gets resourcesstored from the database.
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
                            wood = resourceObject.getInt("wood");
                            stone = resourceObject.getInt("stone");
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

    /**
     * Upgrades building the list is currently on
     */
    private void upgradeCurrent(){
        // use the new player endpoint
        String url = "http://coms-309-048.class.las.iastate.edu:8080/buildings/upgrade";

        // Create a JSONObject with the user's details
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("playerID", userID);
            requestBody.put("buildingType", List.get(Index).buildingName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

<<<<<<< HEAD
    /**
     * Updates all textviews based what is currently selected on list
     */
=======
        // Create a JsonObjectRequest with the POST method
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle successful response from the server
                        Log.d("Building Upgraded", "Building upgraded: " + response.toString());
                        updateDisplay();
                        updateCurrentStorage();
                        fillList();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response from the server
                        Log.e("Building Upgrade", "Error upgrading building: " + error.getMessage());
                    }
                });
        // add to volley request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

>>>>>>> d09c3528968879d9ac580b15832c381972f82591
    private void updateDisplay(){
        StringBuilder upgradeString = new StringBuilder();
        StringBuilder statsString = new StringBuilder();
        StringBuilder buildingString = new StringBuilder();
        buildingString.append(getName(List.get(Index).buildingName)).append(" ").append("(").append(Index + 1).append("/10)\n");
        String name = getName(List.get(Index).buildingName);
        if(name.equals("Resource") && name.equals("Training")){
            statsString.append("Level:           ").append(List.get(Index).level).append("/5\n");
            statsString.append("Building Power:  ").append(List.get(Index).buildingPower).append("\n");
            statsString.append("Max Capacity:    ").append(List.get(Index).maxCapacity).append("\n");
        }
        else{
            statsString.append("Level:           ").append(List.get(Index).level).append("/5\n");
            statsString.append("Building Power:  ").append(List.get(Index).buildingPower).append("\n");
        }
        upgradeString.append("Wood to upgrade:   ").append(List.get(Index).woodToUpgrade).append(" (Currently have ").append(wood).append(")\n");
        upgradeString.append("Stone to upgrade:  ").append(List.get(Index).stoneToUpgrade).append(" (Currently have ").append(stone).append(")\n");
        Glide.with(this).clear(BuildingGif);
        Glide.with(this)
                .asGif()
                .load(getGIF(List.get(Index).buildingName))
                .into(BuildingGif);
        BuildingText.setText(buildingString.toString());
        StatsText.setText(statsString.toString());
        UpgradeText.setText(upgradeString.toString());

    }

    /**
     * Gets the name to print
     * @param n name from the database
     * @return name with proper capitalization and spaces
     */
    private String getName(String n){
        switch(n){
            case "FARM":
                return "Farm";
            case "LUMBERYARD":
                return "Lumberyard";
            case "QUARRY":
                return "Quarry";
            case "PLATINUMMINE":
                return "Platinum Mine";
            case "WARRIORSCHOOL":
                return "Warrior School";
            case "STABLES":
                return "Stable";
            case "ARCHERYRANGE":
                return "Archery Range";
            case "MAGETOWER":
                return "Mage Tower";
            case "MAINBUILDING":
                return "Main Building";
            case "RESEARCHBUILDING":
                return "Research Building";
        }
        return "";
    }

    /**
     * Gets the gif to display
     * @param n string gotten from database
     * @return Int ID of gif
     */
    
    private int getGIF(String n){
        switch(n){
            case "FARM":
                return R.raw.farm;
            case "LUMBERYARD":
                return R.raw.lumberyard;
            case "QUARRY":
                return R.raw.quarry;
            case "PLATINUMMINE":
                return R.raw.platinummine;
            case "WARRIORSCHOOL":
                return R.raw.warriorcamp;
            case "STABLES":
                return R.raw.stables;
            case "ARCHERYRANGE":
                return R.raw.archeryrange;
            case "MAGETOWER":
                return R.raw.wizardtower;
            case "MAINBUILDING":
                return R.raw.mainbuilding;
            case "RESEARCHBUILDING":
                return R.raw.researchbuilding;
        }
        return 0;
    }

}
