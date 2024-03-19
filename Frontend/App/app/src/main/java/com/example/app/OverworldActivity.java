package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.MainActivity;

import java.util.Random;

public class OverworldActivity extends AppCompatActivity {

    private static final int GRID_SIZE = 20; // Adjust grid size as needed
    private static final int GRID_ITEM_SIZE_DP = 100; // Adjust grid item size as needed
    private static final String MAP_DATA_KEY = "map_data";
    private SharedPreferences sharedPreferences;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overworld);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }

        sharedPreferences = getSharedPreferences("MapData", Context.MODE_PRIVATE);

        // Check if map data exists in SharedPreferences
        if (sharedPreferences.contains(MAP_DATA_KEY)) {
            // If map data exists, load and display the map
            String mapData = sharedPreferences.getString(MAP_DATA_KEY, "");
            displayMap(mapData);
        } else {
            // If map data doesn't exist, generate and save a new map
            String mapData = generateMap();
            saveMapData(mapData);
            displayMap(mapData);
        }
    }

    // Function to generate map data
    private String generateMap() {
        StringBuilder mapBuilder = new StringBuilder();

        // Generate map data (example: randomly assign types)
        Random random = new Random();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                int type = random.nextInt(3); // Generates a random number between 0 and 2
                mapBuilder.append(type).append(" "); // Append type to map data
            }
            mapBuilder.append("\n"); // Add newline after each row
        }

        return mapBuilder.toString();
    }

    // Function to save map data to SharedPreferences
    private void saveMapData(String mapData) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MAP_DATA_KEY, mapData);
        editor.apply();
    }

    // Function to display the map using the provided map data
    private void displayMap(String mapData) {
        LinearLayout gridLayout = findViewById(R.id.gridLayout);

        // Loop to create grid items based on the provided map data
        String[] rows = mapData.split("\n");
        for (String row : rows) {
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);

            String[] types = row.split(" ");
            for (String typeStr : types) {
                int type = Integer.parseInt(typeStr);

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
                        gridItem.setText("Enemy");
                        break;
                    case 1:
                        gridItem.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                        gridItem.setText("Resource");
                        break;
                    case 2:
                        gridItem.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        gridItem.setText("Empty");
                        break;
                }

                rowLayout.addView(gridItem);
            }

            gridLayout.addView(rowLayout);
        }
    }

    // Function to convert dp to pixels
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public void goBack(View view) {
        Intent intent = new Intent(OverworldActivity.this, MainActivity.class);
        intent.putExtra("ID", String.valueOf(userID));
        startActivity(intent);
    }
}
