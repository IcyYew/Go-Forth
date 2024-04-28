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

import java.util.ArrayList;
import java.util.Random;

/**
 * Activity used to do most of the game stuff
 */
public class OverworldActivity extends AppCompatActivity {
    private static final int GRID_SIZE = 30;
    private static final int GRID_ITEM_SIZE_DP = 75;
    private static final int RESOURCE_CHANCE = 5;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Integer> ids = new ArrayList<>();
    private ArrayList<Integer> xCoords = new ArrayList<>();
    private ArrayList<Integer> yCoords = new ArrayList<>();
    private ArrayList<Integer> resourceXCoords = new ArrayList<>();
    private ArrayList<Integer> resourceYCoords = new ArrayList<>();
    private ArrayList<String> resourceTypes = new ArrayList<>(); // To keep track of the type of resource (stone or wood)
    private LinearLayout gridLayout;
    private int userID, playerRow, playerCol, originalRow, originalCol, wood, stone, power, enemyIndex, enemyID;
    private boolean isPlayerMoved = false; // To track whether the player has moved
    private Button collectButton, moveButton, fightButton, backButton;
    private TextView woodHeld, stoneHeld, powerLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overworld);

        // Retrieve user ID from intent extras
        if (getIntent().hasExtra("ID")) {
            userID = getIntent().getIntExtra("ID", -1);
        }

        // Initialize layout
        gridLayout = findViewById(R.id.gridLayout);

        // Initialize buttons
        collectButton = findViewById(R.id.collectButton);
        collectButton.setVisibility(View.GONE);
        moveButton = findViewById(R.id.moveButton);
        moveButton.setVisibility(View.GONE);
        fightButton = findViewById(R.id.fightButton);
        fightButton.setVisibility(View.GONE);
        backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.GONE);

        // Set up the buttons
        collectButton.setOnClickListener(view -> collectResource());
        moveButton.setOnClickListener(view -> moveBase());
        fightButton.setOnClickListener(view -> startFight(userID, enemyID));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to the main activity
                Intent intent = new Intent(OverworldActivity.this, MainActivity.class);
                intent.putExtra("ID", String.valueOf(userID));
                startActivity(intent);
            }
        });

        woodHeld = findViewById(R.id.WoodAmount);
        stoneHeld = findViewById(R.id.StoneAmount);
        powerLevel = findViewById(R.id.PowerLevel);

        // Fetch player data and generate the initial map
        fetchPlayerDataAndGenerateMap();
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

                if (row == playerRow && col == playerCol && !isUserPosition(row, col)) {
                    fightButton.setVisibility(View.GONE);
                    backButton.setVisibility(View.GONE);

                    gridItem.setBackgroundResource(R.drawable.knight);
                    isCellSet = true;

                    if (isEmptyTile(row, col)) {
                        moveButton.setVisibility(View.VISIBLE);
                    } else {
                        moveButton.setVisibility(View.GONE);
                    }
                } else if (row == originalRow && col == originalCol) {
                    // Original player position
                    gridItem.setBackgroundResource(R.drawable.castle);
                    isCellSet = true;

                    if (!isPlayerMoved) {
                        backButton.setVisibility(View.VISIBLE);
                    }
                } else if (isUserPosition(row, col)) {
                    // Other players' positions
                    gridItem.setBackgroundResource(R.drawable.enemy);
                    isCellSet = true;

                    // If the player is at an enemy's position, show the fight button
                    if (row == playerRow && col == playerCol) {
                        fightButton.setVisibility(View.VISIBLE);
                        enemyIndex = getEnemyIndex(row, col);
                        fightButton.setText("Fight " + names.get(enemyIndex));
                        enemyID = ids.get(enemyIndex);
                    }
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

        updateAmount();
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
                                ids.add(playerObject.getInt("playerID"));
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
                    resourceXCoords.add(i);
                    resourceYCoords.add(j);
                    // Randomly choose the type of resource (stone or wood)
                    resourceTypes.add(random.nextBoolean() ? "stone" : "wood");
                }
            }
        }
    }





    /**
     * Moves the player's base to the new position and updates the original position
     */
    private void moveBase() {
        if (!(wood >= 5000 && stone >= 5000)) {
            Toast.makeText(this, "You need at least 5000 wood and 5000 stone to move your base!", Toast.LENGTH_SHORT).show();
            return;
        }

        removeResource(5000, "WOOD");

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        removeResource(5000, "STONE");

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < xCoords.size(); i++) {
            if (xCoords.get(i) == originalRow && yCoords.get(i) == originalCol) {
                xCoords.remove(i);
                yCoords.remove(i);
                break;
            }
        }

        // Save the current position as the new base position
        originalRow = playerRow;
        originalCol = playerCol;

        // Add the new base position to the list of player coordinates
        xCoords.add(playerRow);
        yCoords.add(playerCol);

        // Send an update to the server
        updateBasePosition();

        // Hide the move button after moving the base
        moveButton.setVisibility(View.GONE);

        isPlayerMoved = false;

        // Re-render the map to reflect the changes
        displayMap();
    }

    private void updateBasePosition() {
        JSONObject jsonObject = new JSONObject(); //Initialize input JSON
        try {
            jsonObject.put("locationX", playerRow);
            jsonObject.put("locationY", playerCol);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "http://coms-309-048.class.las.iastate.edu:8080/players/changePlayerLocation/" + userID;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

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
        int minRow = originalRow - 10;
        int maxRow = originalRow + 10;
        int minCol = originalCol - 10;
        int maxCol = originalCol + 10;

        // Ensure the new position is within grid boundaries and within the defined boundaries from the original position
        if (newRow >= minRow && newRow <= maxRow && newCol >= minCol && newCol <= maxCol
                && newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE) {
            // Update the player's position
            playerRow = newRow;
            playerCol = newCol;

            // Hide or show the move button depending on the new position
            if (isEmptyTile(newRow, newCol)) {
                moveButton.setVisibility(View.VISIBLE);
            } else {
                moveButton.setVisibility(View.GONE);
            }

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

                updateAmount();

                // Update the display
                displayMap();

                // Exit the loop as we've handled the resource collection
                break;
            }
        }
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
                        updateAmount();
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
                        Toast.makeText(OverworldActivity.this, "Error updating resources: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                            power = response.getInt("power");
                            //Get current resource values
                            JSONObject resourceObject = response.getJSONObject("resources");
                            wood = resourceObject.getInt("wood");
                            stone = resourceObject.getInt("stone");

                            String woodText = "Wood: " + wood;
                            String stoneText = "Stone: " + stone;
                            String powerText = "Power: " + power;
                            //print current resource values on screen.
                            woodHeld.setText(woodText);
                            stoneHeld.setText(stoneText);
                            powerLevel.setText(powerText);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(OverworldActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OverworldActivity.this, "Error fetching player resources: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // add to volley queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }





    private int getEnemyIndex(int row, int col) {
        for (int i = 0; i < xCoords.size(); i++) {
            if (xCoords.get(i) == row && yCoords.get(i) == col) {
                return i;
            }
        }
        return -1; // If no enemy found at the given coordinates
    }

    /**
     * Simulates fight by calling the players/fight/{user1}/{user2} endpoint. Displays returned value
     *
     * @param player1ID
     * @param player2ID
     */
    private void startFight(int player1ID, int player2ID) {
        // use the players/fight/{user1}/{user2} endpoint
        String url = "http://coms-309-048.class.las.iastate.edu:8080/players/fight/" + player1ID + "/" + player2ID;

        // Makes StringRequest at the endpoint and sets the TextView to the result
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleFightResult(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error during fight: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // add request to volley queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * Method to handle fight results and declare a winner
     *
     * @param response
     */
    private void handleFightResult(String response) {
        Log.d("Response", response);
        // parse the response
        String[] parts = response.split("\n");
        String result = parts[0];

        if (result.equals("ATTACKER_WIN")) {
            Toast.makeText(this, "You won!", Toast.LENGTH_SHORT).show();
        } else if (result.equals("DEFENDER_WIN")) {
            Toast.makeText(this, "You lost!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "It's a draw", Toast.LENGTH_SHORT).show();
        }

        updateAmount();
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

    /**
     * Check if the tile is empty at the given coordinates
     */
    private boolean isEmptyTile(int row, int col) {
        // Check if the given position is free of other players and resources
        return !isUserPosition(row, col) && !isResourcePosition(row, col);
    }
}
