package com.example.app;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class OverworldActivity extends AppCompatActivity {
    private static final int GRID_SIZE = 10; // Adjust grid size as needed
    private static final int GRID_ITEM_SIZE_DP = 50; // Adjust grid item size as needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overworld);

        LinearLayout gridLayout = findViewById(R.id.gridLayout);

        // Loop to create grid items dynamically
        for (int i = 0; i < GRID_SIZE; i++) {
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);

            for (int j = 0; j < GRID_SIZE; j++) {
                TextView gridItem = new TextView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        dpToPx(GRID_ITEM_SIZE_DP),
                        dpToPx(GRID_ITEM_SIZE_DP)
                );
                gridItem.setLayoutParams(params);
                gridItem.setPadding(5, 5, 5, 5);
                gridItem.setText("Grid Item");
                // Set random type for grid item
                setRandomType(gridItem);

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

    // Function to randomly assign types to grid items
    private void setRandomType(TextView gridItem) {
        Random random = new Random();
        int type = random.nextInt(3); // Generates a random number between 0 and 2

        switch (type) {
            case 0:
                gridItem.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                gridItem.setText("Enemy Base");
                break;
            case 1:
                gridItem.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                gridItem.setText("Resource Space");
                break;
            case 2:
                gridItem.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                gridItem.setText("Empty Space");
                break;
        }
    }
}
