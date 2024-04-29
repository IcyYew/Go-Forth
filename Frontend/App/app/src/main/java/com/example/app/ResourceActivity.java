package com.example.app;

import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.sleep;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import android.util.Log;

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
    private TextView foodCollect;
    private Button addFood;

    //Wood UI
    private TextView woodHeld;
    private TextView woodCollect;
    private Button addWood;

    //Stone UI
    private TextView stoneHeld;
    private TextView stoneCollect;
    private Button addStone;

    //Platinum UI
    private TextView platinumHeld;
    private TextView platinumCollect;
    private Button addPlatinum;
    
    private Thread updateThread;

    private boolean stopThread;

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

        foodHeld = findViewById(R.id.foodAmount);
        foodCollect = findViewById(R.id.foodCollect);
        addFood = findViewById(R.id.foodCollectButton);

        woodHeld = findViewById(R.id.woodAmount);
        woodCollect = findViewById(R.id.woodCollect);
        addWood = findViewById(R.id.woodCollectButton);

        stoneHeld = findViewById(R.id.stoneAmount);
        stoneCollect = findViewById(R.id.stoneCollect);
        addStone = findViewById(R.id.stoneCollectButton);

        platinumHeld = findViewById(R.id.platinumAmount);
        platinumCollect = findViewById(R.id.platCollect);
        addPlatinum = findViewById(R.id.platCollectButton);

        Back = findViewById(R.id.Back);
        updateCurrentStorage();
        updateBuildingAmount();

        stopThread = false;

        updateThread = new Thread(new UpdateThread());
        updateThread.start();

        Back.setOnClickListener(new View.OnClickListener() {
            //Back button clicked
            @Override
            public void onClick(View v) {
                //eventually use new collect function
                stopThread = true;
                updateThread.interrupt();
                try {
                    updateThread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Intent intent = new Intent(ResourceActivity.this, MainActivity.class);
                intent.putExtra("ID", String.valueOf(userID));
                startActivity(intent);
            }
        });

        addFood.setOnClickListener(new View.OnClickListener() {
            //Back add food clicked
            @Override
            public void onClick(View v) {
                addResource("FARM");
            }
        });

        addWood.setOnClickListener(new View.OnClickListener() {
            //Add wood clicked
            @Override
            public void onClick(View v) {
                addResource("LUMBERYARD");
            }
        });

        addStone.setOnClickListener(new View.OnClickListener() {
            //Add stone clicked
            @Override
            public void onClick(View v) {
                        addResource("QUARRY");
            }
        });

        addPlatinum.setOnClickListener(new View.OnClickListener() {
            //Add platinum clicked
            @Override
            public void onClick(View v) {
                addResource("PLATINUMMINE");
            }
        });

    }

    /**
     * Adds resources to the backend then updates the current count shown onscreen.
     *
     * @param resourceName Name of the resource to add
     */
    private void addResource(String resourceName){
        JSONObject jsonObject = new JSONObject(); //Initialize input JSON
        try {
            jsonObject.put("buildingType", resourceName);
            jsonObject.put("playerID", userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "http://coms-309-048.class.las.iastate.edu:8080/buildings/collectResources";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        updateCurrentStorage();
                        updateBuildingAmount();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        updateCurrentStorage();
                        updateBuildingAmount();
                    }
                });

        // add to volley queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void updateBuildingAmount() {
        String url = "http://coms-309-048.class.las.iastate.edu:8080/buildings/updateResources/" + String.valueOf(userID);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Get current resource values
                            JSONObject resourceObject = response.getJSONObject("resourceBuildings");
                            int food = resourceObject.getInt("farmresources");
                            int wood = resourceObject.getInt("lumberyardresources");
                            int stone = resourceObject.getInt("quarryresources");
                            int platinum = resourceObject.getInt("platinummineresources");

                            //print current resource values on screen.
                            foodCollect.setText(String.valueOf(food));
                            woodCollect.setText(String.valueOf(wood));
                            stoneCollect.setText(String.valueOf(stone));
                            platinumCollect.setText(String.valueOf(platinum));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ResourceActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                        }                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ResourceActivity.this, "updateBuildingAmount: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // add to volley queue
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
                        Toast.makeText(ResourceActivity.this, "updateCurrentStorage: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // add to volley queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    class UpdateThread implements Runnable {
    public void run()
    {
        try {
            while(!stopThread) {
                Thread.sleep(1000);
                updateBuildingAmount();
                //updateCurrentStorage();
            }
        }
        catch (Exception e) {
            if (Thread.interrupted()) try {
                throw new InterruptedException();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
}