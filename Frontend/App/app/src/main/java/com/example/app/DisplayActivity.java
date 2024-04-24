package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Activity that displays the current users (assuming there are any)
 *
 * @author Josh Dwight
 */
public class DisplayActivity extends AppCompatActivity {

    private ArrayList<User> List;

    // TextView to display users info (name, id, power level)
    private TextView users;

    // Button to go back to main
    private Button back;

    // tracks user ID so it can track across activities
    private int userID;

    private Button Power;

    private Button Name;

    private Button ID;

    /**
     * On the creation of this activity, TextViews and Buttons are initialized.
     * Extras are received and put in userID variable (for carrying across activities)
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_users);

        // getting the extras so ID tracks across activities
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }

        List = new ArrayList<>();

        users = findViewById(R.id.msgResponse);

        back = findViewById(R.id.back);

        Power = findViewById(R.id.Power);

        Name = findViewById(R.id.Name);

        ID = findViewById(R.id.ID);

        fillList();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this, MainActivity.class);
                intent.putExtra("ID", String.valueOf(userID));
                startActivity(intent);
            }
        });

        Power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(List, new sortByPower());
                displayUsers();
            }
        });

        Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(List, new sortByName());
                displayUsers();
            }
        });

        ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(List, new sortByID());
                displayUsers();
            }
        });
    }

    /**
     * Uses the /players/getall endpoint to display users in a String format (converts endpoint's json output to string using StringBuilder)
     */
    private void fillList() {
        // use getall endpoint URL
        String url = "http://coms-309-048.class.las.iastate.edu:8080/players/getall";

        // make a StringRequest to get the users from the server. Converts JSONArray into StringBuilder.
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Display response", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject playerObject = jsonArray.getJSONObject(i);
                                List.add(new User(playerObject.getInt("playerID"), (int) playerObject.getDouble("power"), playerObject.getString("userName")));
                                /*
                                playersString.append("Player ID: ").append(playerObject.getInt("playerID")).append("\n");
                                playersString.append("Player name: ").append(playerObject.getString("userName")).append("\n");
                                playersString.append("Password: ").append(playerObject.getString("password")).append("\n");
                                playersString.append("Player power level: ").append(playerObject.getInt("power")).append("\n");
                                playersString.append("Player Map Position: (").append(playerObject.getInt("locationX")).append(", ").append(playerObject.getInt("locationY")).append(")\n");
                                playersString.append("\n");
                                 */
                            }

                            //users.setText(playersString.toString());
                            Collections.sort(List, new sortByPower());
                            displayUsers();
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
     * Class to store user data
     */
    private class User {

        private int userID;

        private int userPower;

        private String name;

        User(int userID, int userPower, String name) {
            this.userID = userID;
            this.userPower = userPower;
            this.name = name;
        }

    }

    private class sortByPower implements Comparator<User> {
        public int compare(User a, User b) {
            return b.userPower - a.userPower;
        }
    }

    private class sortByName implements Comparator<User> {
        public int compare(User a, User b) {
            return a.name.compareTo(b.name);
        }
    }

    private class sortByID implements Comparator<User> {
        public int compare(User a, User b) {
            return a.userID - b.userID;
        }
    }

    private void displayUsers(){
        StringBuilder playersString = new StringBuilder();
        for(int i = 0; i < List.size(); i++){
            playersString.append("Rank: ").append(i);
            playersString.append(" User: ").append(List.get(i).name).append(" ID: ").append(List.get(i).userID).append(" Power: ").append(List.get(i).userPower);
            playersString.append("\n");
        }
        users.setText(playersString.toString());
    }
}