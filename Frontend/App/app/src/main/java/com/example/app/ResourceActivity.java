package com.example.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This activity is respnsible for resource management.
 *
 * @author Nicholas Lynch
 */
public class ResourceActivity extends AppCompatActivity {
    //Current userID
    private int userID;

    //Back Button
    private Button Back;

    //Food UI
    private TextView foodHeld;
    private Button addFood;
    private Button removeFood;
    private EditText foodPrompt;

    //Wood UI
    private TextView woodHeld;
    private Button addWood;
    private Button removeWood;
    private EditText woodPrompt;

    //Stone UI
    private TextView stoneHeld;
    private Button addStone;
    private Button removeStone;
    private EditText stonePrompt;

    //Platinum UI
    private TextView platinumHeld;
    private Button addPlatinum;
    private Button removePlatinum;
    private EditText platinumPrompt;

    /**
     * On the creation of this activity, TextViews and Buttons are initialized.
     * Extras are received and put in userID variable (for carrying across activities)
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);

        //Get ID
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }

        //Initialize buttons
        Back = findViewById(R.id.Back);

        foodHeld = findViewById(R.id.FoodAmount);
        addFood = findViewById(R.id.FoodPlus);
        removeFood = findViewById(R.id.FoodMinus);
        foodPrompt = findViewById(R.id.FoodPrompt);

        woodHeld = findViewById(R.id.WoodAmount);
        addWood = findViewById(R.id.WoodPlus);
        removeWood = findViewById(R.id.WoodMinus);
        woodPrompt = findViewById(R.id.WoodPrompt);

        stoneHeld = findViewById(R.id.StoneAmount);
        addStone = findViewById(R.id.StonePlus);
        removeStone = findViewById(R.id.StoneMinus);
        stonePrompt = findViewById(R.id.StonePrompt);

        platinumHeld = findViewById(R.id.PlatinumAmount);
        addPlatinum = findViewById(R.id.PlatinumPlus);
        removePlatinum = findViewById(R.id.PlatinumMinus);
        platinumPrompt = findViewById(R.id.PlatinumPrompt);

        Back = findViewById(R.id.Back);
        updateAmount();

        Back.setOnClickListener(new View.OnClickListener() {
            //Back button clicked
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResourceActivity.this, MainActivity.class);
                intent.putExtra("ID", String.valueOf(userID));
                startActivity(intent);
            }
        });

        addFood.setOnClickListener(new View.OnClickListener() {
            //Back add food clicked
            @Override
            public void onClick(View v) {
                addResource(Integer.parseInt(foodPrompt.getText().toString()), "FOOD");
            }
        });

        removeFood.setOnClickListener(new View.OnClickListener() {
            //Remove food clicked
            @Override
            public void onClick(View v) {
                removeResource(Integer.parseInt(foodPrompt.getText().toString()), "FOOD");
            }
        });
        addWood.setOnClickListener(new View.OnClickListener() {
            //Add wood clicked
            @Override
            public void onClick(View v) {
                addResource(Integer.parseInt(woodPrompt.getText().toString()), "WOOD");
            }
        });

        removeWood.setOnClickListener(new View.OnClickListener() {
            //Remove wood clicked
            @Override
            public void onClick(View v) {
                removeResource(Integer.parseInt(woodPrompt.getText().toString()), "WOOD");
            }
        });

        addStone.setOnClickListener(new View.OnClickListener() {
            //Add stone clicked
            @Override
            public void onClick(View v) {
                addResource(Integer.parseInt(stonePrompt.getText().toString()), "STONE");
            }
        });

        removeStone.setOnClickListener(new View.OnClickListener() {
            //Remove stone clicked
            @Override
            public void onClick(View v) {
                removeResource(Integer.parseInt(stonePrompt.getText().toString()), "STONE");
            }
        });
        addPlatinum.setOnClickListener(new View.OnClickListener() {
            //Add platinum clicked
            @Override
            public void onClick(View v) {
                addResource(Integer.parseInt(platinumPrompt.getText().toString()), "PLATINUM");
            }
        });

        removePlatinum.setOnClickListener(new View.OnClickListener() {
            //Remove platinum clicked
            @Override
            public void onClick(View v) {
                removeResource(Integer.parseInt(platinumPrompt.getText().toString()), "PLATINUM");
            }
        });

    }

    /**
     * Adds resources to the backend then updates the current count shown onscreen.
     *
     * @param amount Amount of resources to add
     * @param resourceName Name of the resource to add
     */
    private void addResource(int amount, String resourceName){
        JSONObject jsonObject = new JSONObject(); //Initialize input JSON
        try {
            jsonObject.put("resourceType", resourceName);
            jsonObject.put("quantity", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "http://coms-309-048.class.las.iastate.edu:8080/players/addresource/" + userID;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        updateAmount(); //update screen when backend is done updating
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ResourceActivity.this, "Error updating resources: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // add to volley queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    /** Adds resources to the backend then updates the current count shown onscreen.
     *
     * @param amount Amount of resources to remove
     * @param resourceName Name of resource to remove
     */
    private void removeResource(int amount, String resourceName){
        JSONObject jsonObject = new JSONObject(); //Initialize input JSON
        try {
            jsonObject.put("resourceType", resourceName);
            jsonObject.put("quantity", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "http://coms-309-048.class.las.iastate.edu:8080/players/removeresource/" + userID;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        updateAmount(); //update screen when backend is done updating
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ResourceActivity.this, "Error updating resources: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // add to volley queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * This method gets the newly updated resource amount from the database.
     * It then displays the values on the corresponding TextView.
     */
    private void updateAmount() {
        String url = "http://coms-309-048.class.las.iastate.edu:8080/players/getPlayer/" + String.valueOf(userID);

        // makes JsonObjectRequest to get the current player. GETs the archerNum, warriorNum, mageNum, and cavalryNum
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Get current resource values
                            JSONObject resourceObject = response.getJSONObject("resources");
                            int food = resourceObject.getInt("food");
                            int wood = resourceObject.getInt("wood");
                            int stone = resourceObject.getInt("stone");
                            int platinum = resourceObject.getInt("platinum");

                            //print current resource values on screen.
                            foodHeld.setText(String.valueOf(food));
                            woodHeld.setText(String.valueOf(wood));
                            stoneHeld.setText(String.valueOf(stone));
                            platinumHeld.setText(String.valueOf(platinum));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ResourceActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ResourceActivity.this, "Error fetching player resources: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // add to volley queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}