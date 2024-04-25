package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.Random;

/**
 * Activity used to do most of the game stuff
 */
public class OverworldActivity extends AppCompatActivity {
    private static final int GRID_SIZE = 20;
    private static final int GRID_ITEM_SIZE_DP = 75;
    private static final int RESOURCE_CHANCE = 5; // Adjust the chance of placing a resource (1 in 10)
    private SharedPreferences sharedPreferences;
    private int userID;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Integer> xCoords = new ArrayList<>();
    private ArrayList<Integer> yCoords = new ArrayList<>();
    private ArrayList<Integer> resourceXCoords = new ArrayList<>();
    private ArrayList<Integer> resourceYCoords = new ArrayList<>();
    private ArrayList<String> resourceTypes = new ArrayList<>(); // To keep track of the type of resource (stone or wood)
    private LinearLayout gridLayout;
    private int playerRow, playerCol;
    private int originalRow, originalCol; // To store the player's original starting position
    private boolean isPlayerMoved = false; // To track whether the player has moved
    private Button collectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overworld);

        // Retrieve user ID from intent extras
        if (getIntent().hasExtra("ID")) {
            userID = getIntent().getIntExtra("ID", -1);
        }

        // Initialize shared preferences
        sharedPreferences = getSharedPreferences("MapData", Context.MODE_PRIVATE);

        // Initialize layout
        gridLayout = findViewById(R.id.gridLayout);

        // Initialize the collect button
        collectButton = findViewById(R.id.collectButton);
        collectButton.setVisibility(View.GONE);

        // Set up the button click listener for collecting resources
        collectButton.setOnClickListener(view -> collectResource());

        // Fetch player data and generate the initial map
        fetchPlayerDataAndGenerateMap();
    }

    /**
     * Get the locations of players and ensure we don't overwrite them
     */
    private void fetchPlayerDataAndGenerateMap() {
        String url = "http://coms-309-048.class.las.iastate.edu:8080/players/getall";

        // Make a GET request to fetch player data
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            // Iterate through player data
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject playerObject = jsonArray.getJSONObject(i);
                                int id = playerObject.getInt("playerID");
                                names.add(playerObject.getString("userName"));
                                xCoords.add(playerObject.getInt("locationX"));
                                yCoords.add(playerObject.getInt("locationY"));

                                // Identify player's initial position
                                if (id == userID) {
                                    playerCol = yCoords.get(i);
                                    playerRow = xCoords.get(i);
                                    originalRow = playerRow;
                                    originalCol = playerCol;
                                }
                            }

                            // Randomly generate resources on the map
                            generateResources();

                            // Display the initial map
                            displayMap();
                        } catch (JSONException e) {
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

        // Add request to Volley request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * Generate resource positions depending on the RESOURCE_CHANCE
     */
    private void generateResources() {
        Random random = new Random();
        // Clear any previous resource positions
        resourceXCoords.clear();
        resourceYCoords.clear();
        resourceTypes.clear();

        // Calculate the probability threshold based on RESOURCE_CHANCE
        double probabilityThreshold = 1.0 / RESOURCE_CHANCE;

        // Iterate through the grid
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                // Generate a random double between 0 and 1
                double randomValue = random.nextDouble();

                // Place a resource if the random value is less than the threshold
                if (randomValue < probabilityThreshold) {
                    Log.d("Coordinate", i + ", " + j);
                    resourceXCoords.add(i);
                    resourceYCoords.add(j);
                    // Randomly choose the type of resource (stone or wood)
                    resourceTypes.add(random.nextBoolean() ? "stone" : "wood");
                }
            }
        }
    }

    /**
     * Generates the displayed map (slots pictures in the correct grid positions)
     */
    private void displayMap() {
        // Clear current grid layout
        gridLayout.removeAllViews();

        // Loop to create grid items based on the player's position
        for (int row = 0; row < GRID_SIZE; row++) {
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);

            for (int col = 0; col < GRID_SIZE; col++) {
                ImageView gridItem = new ImageView(this);
                gridItem.setLayoutParams(new LinearLayout.LayoutParams(
                        dpToPx(GRID_ITEM_SIZE_DP), dpToPx(GRID_ITEM_SIZE_DP)
                ));

                // Determine the type of cell at the current position
                boolean isCellSet = false;  // Used to track if a cell is set with an image

                if (row == playerRow && col == playerCol) {
                    if (!isPlayerMoved) {
                        gridItem.setBackgroundResource(R.drawable.castle);
                    } else {
                        gridItem.setBackgroundResource(R.drawable.knight);
                    }
                    isCellSet = true;
                } else if (row == originalRow && col == originalCol) {
                    // Original player position
                    gridItem.setBackgroundResource(R.drawable.castle);
                    isCellSet = true;
                } else if (isUserPosition(row, col)) {
                    // Other players' positions
                    gridItem.setBackgroundResource(R.drawable.enemy);
                    isCellSet = true;
                } else if (isResourcePosition(row, col)) {
                    // Resource positions
                    // Find the index of the resource at the current position
                    int resourceIndex = -1;
                    for (int i = 0; i < resourceXCoords.size(); i++) {
                        if (resourceXCoords.get(i) == row && resourceYCoords.get(i) == col) {
                            resourceIndex = i;
                            break; // Exit the loop as soon as we find a match
                        }
                    }

                    // Check if a resource was found at the current position
                    if (resourceIndex != -1) {
                        String resourceType = resourceTypes.get(resourceIndex);
                        if (resourceType.equals("stone")) {
                            gridItem.setBackgroundResource(R.drawable.stone);
                        } else {
                            gridItem.setBackgroundResource(R.drawable.wood);
                        }
                        isCellSet = true;
                    }
                }

                // If no specific condition was met, set the cell as grass
                if (!isCellSet) {
                    gridItem.setBackgroundResource(R.drawable.grass);
                }

                // Add grid item to row layout
                rowLayout.addView(gridItem);
            }

            // Add row layout to grid layout
            gridLayout.addView(rowLayout);
        }
    }

    /**
     * Checks if a grid position is a user position
     *
     * @param x
     * @param y
     * @return true or false
     */
    private boolean isUserPosition(int x, int y) {
        // Check if the provided coordinates match any other players' positions
        for (int i = 0; i < xCoords.size(); i++) {
            if (x == xCoords.get(i) && y == yCoords.get(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a grid position is a resource position
     * @param x
     * @param y
     * @return true or false
     */
    private boolean isResourcePosition(int x, int y) {
        // Check if the provided coordinates match any resource positions
        for (int i = 0; i < resourceXCoords.size(); i++) {
            if (x == resourceXCoords.get(i) && y == resourceYCoords.get(i)) {
                return true;
            }
        }
        return false;
    }

    private int dpToPx(int dp) {
        // Convert dp to pixels
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }

    // Functions to move player in the respective directions
    public void moveUp(View view) {
        movePlayer(-1, 0);
    }

    public void moveDown(View view) {
        movePlayer(1, 0);
    }

    public void moveLeft(View view) {
        movePlayer(0, -1);
    }

    public void moveRight(View view) {
        movePlayer(0, 1);
    }

    /**
     * Moves the player in the desired direction
     * @param deltaY
     * @param deltaX
     */
    private void movePlayer(int deltaY, int deltaX) {
        // Calculate the new position of the player
        int newRow = playerRow + deltaY;
        int newCol = playerCol + deltaX;

        // Define the boundaries for the user based on their original position
        int minRow = originalRow - 3;
        int maxRow = originalRow + 3;
        int minCol = originalCol - 3;
        int maxCol = originalCol + 3;

        // Ensure the new position is within grid boundaries and within the defined boundaries from the original position
        if (newRow >= minRow && newRow <= maxRow && newCol >= minCol && newCol <= maxCol
                && newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE) {
            // Update the player's position
            playerRow = newRow;
            playerCol = newCol;

            if (!isPlayerMoved) {
                isPlayerMoved = true;
            }

            if (playerCol == originalCol && playerRow == originalRow && isPlayerMoved) {
                isPlayerMoved = false;
            }

            // Update the button visibility and text based on the current position
            updateCollectButton();

            // Update the map to reflect the player's new position
            displayMap();
        } else {
            Toast.makeText(getApplicationContext(), "You can only move up to 3 positions away from your base.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Checks to see if we need to display collect button
     */
    private void updateCollectButton() {
        // Check if the player is on a resource tile
        boolean isOnResourceTile = false;
        for (int i = 0; i < resourceXCoords.size(); i++) {
            int resourceRow = resourceXCoords.get(i);
            int resourceCol = resourceYCoords.get(i);
            if (playerRow == resourceRow && playerCol == resourceCol) {
                // Player is on a resource tile
                String resourceType = resourceTypes.get(i);
                collectButton.setText("Collect " + resourceType);
                collectButton.setVisibility(View.VISIBLE);
                isOnResourceTile = true;
                break;
            }
        }

        // If not on a resource tile, hide the button
        if (!isOnResourceTile) {
            collectButton.setVisibility(View.GONE);
        }
    }

    /**
     * Collects resource and updates map to no longer have the resource
     */
    private void collectResource() {
        // Collect the resource from the player's current tile
        for (int i = 0; i < resourceXCoords.size(); i++) {
            int resourceRow = resourceXCoords.get(i);
            int resourceCol = resourceYCoords.get(i);
            if (playerRow == resourceRow && playerCol == resourceCol) {
                String resourceType = resourceTypes.get(i);
                String upper = resourceType.toUpperCase();

                Random rand = new Random();
                int amount = rand.nextInt((400 - 100) + 1) + 100;

                addResource(amount, upper);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Remove the resource from the map
                resourceXCoords.remove(i);
                resourceYCoords.remove(i);
                resourceTypes.remove(i);

                // Hide the collect button
                collectButton.setVisibility(View.GONE);

                // Update the display
                displayMap();

                // Exit the loop as we've handled the resource collection
                break;
            }
        }
    }

    /**
     * Back to main
     * @param view
     */
    public void goBack(View view) {
        // Return to the main activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("ID", String.valueOf(userID));
        startActivity(intent);
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
                        String lower = resourceName.toLowerCase();
                        Toast.makeText(OverworldActivity.this, amount + " " + lower + " collected", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OverworldActivity.this, "Error updating resources: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // add to volley queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
