package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.app.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * This activity is responsible for displaying and updating the overworld.
 *
 * @author Josh Dwight
 */
public class OverworldActivity extends AppCompatActivity {
    private static final int GRID_SIZE = 20; // Adjust grid size as needed
    private static final int GRID_ITEM_SIZE_DP = 100; // Adjust grid item size as needed
    private static final String MAP_DATA_KEY = "map_data";
    private SharedPreferences sharedPreferences;
    private int userID;

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
        setContentView(R.layout.activity_overworld);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }

        sharedPreferences = getSharedPreferences("MapData", Context.MODE_PRIVATE);

        fetchPlayerDataAndGenerateMap();
    }

    /**
     * This method displays a map.
     * If the activity is launched for the first time in a session,
     * then it will be a new map.
     * Each subsequent time will be the same map.
     *
     * @param mapData new or stored map
     */
    private void displayMap(String mapData, ArrayList<String> names) {
        LinearLayout gridLayout = findViewById(R.id.gridLayout);

        // Loop to create grid items based on the provided map data
        String[] rows = mapData.split("\n");
        int currentPlayer = 0;

        for (int i = 0; i < rows.length; i++) {
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);

            String[] types = rows[i].split(" ");
            for (int j = 0; j < types.length; j++) {
                int type = Integer.parseInt(types[j]);

                TextView gridItem = new TextView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        dpToPx(GRID_ITEM_SIZE_DP),
                        dpToPx(GRID_ITEM_SIZE_DP)
                );
                gridItem.setLayoutParams(params);
                gridItem.setPadding(5, 5, 5, 5);

                // Set color and text based on the type
                switch (type) {
                    case 0:
                        gridItem.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                        gridItem.setText("");
                        break;
                    case 1:
                        gridItem.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                        gridItem.setText("");
                        break;
                    case 2:
                        gridItem.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        gridItem.setText("");
                        break;
                    case 3:
                        gridItem.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

                        gridItem.setText("Player");
                        break;
                    case 4:
                        gridItem.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));

                        gridItem.setText("Me");
                        break;
                }

                rowLayout.addView(gridItem);
            }

            gridLayout.addView(rowLayout);
        }
    }

    /**
     * Allows us to convert dp to px so we can accurately display the grid blocks.
     *
     * @param dp dp to convert
     * @return px
     */
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    /**
     * This method returns you to the main activity and puts the ID as an extra.
     *
     * @param view current activity (overworld)
     */
    public void goBack(View view) {
        Intent intent = new Intent(OverworldActivity.this, MainActivity.class);
        intent.putExtra("ID", String.valueOf(userID));
        startActivity(intent);
    }

    private void fetchPlayerDataAndGenerateMap() {
        // use getall endpoint URL
        String url = "http://coms-309-048.class.las.iastate.edu:8080/players/getall";

        // make a StringRequest to get the users from the server. Converts JSONArray into StringBuilder.
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            ArrayList<Integer> ids = new ArrayList<>();
                            ArrayList<String> names = new ArrayList<>();
                            ArrayList<Integer> xCoords = new ArrayList<>();
                            ArrayList<Integer> yCoords = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject playerObject = jsonArray.getJSONObject(i);
                                ids.add(playerObject.getInt("playerID"));
                                names.add(playerObject.getString("userName"));
                                xCoords.add(playerObject.getInt("locationX"));
                                yCoords.add(playerObject.getInt("locationY"));
                            }

                            // Generate and display map after player data is fetched
                            String mapData = generateMap(ids, xCoords, yCoords);
                            displayMap(mapData, names);
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

        // add to the request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * This method is used to generate the map after player data is fetched.
     *
     * @param ids List of player ids
     * @param xCoords List of player x-coordinates
     * @param yCoords List of player y-coordinates
     * @return the built map
     */
    private String generateMap(ArrayList<Integer> ids, ArrayList<Integer> xCoords, ArrayList<Integer> yCoords) {
        StringBuilder mapBuilder = new StringBuilder();

        // Generate map data
        Random random = new Random();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                boolean isUserPosition = false;
                boolean isCurrentUser = false;

                // Check if current grid position matches any user's position
                for (int k = 0; k < xCoords.size(); k++) {
                    if (i == xCoords.get(k) && j == yCoords.get(k)) {
                        Log.d("User at", i + ", " + j);
                        if (ids.get(k) == userID) {
                            isCurrentUser = true;
                            break;
                        }
                        isUserPosition = true;
                        break;
                    }
                }

                int type;
                if (isUserPosition) {
                    type = 3; // Assuming 3 represents the user
                } else if (isCurrentUser) {
                    type = 4;
                } else {
                    type = random.nextInt(3); // Generates a random number between 0 and 2
                }
                mapBuilder.append(type).append(" "); // Append type to map data
            }
            mapBuilder.append("\n"); // Add newline after each row
        }

        return mapBuilder.toString();
    }
}
