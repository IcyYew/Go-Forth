package com.example.goforth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Shows all clans and their corresponding IDS
 */
public class ClanDisplay extends AppCompatActivity {

    private int userID;

    private Button Back;

    private TextView Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clan_display);

        //Get ID
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }

        Back = findViewById(R.id.Back);

        Text = findViewById(R.id.Text);

        displayClans();

        /**
         * Goes back to ClanActivity
         */
        Back.setOnClickListener(new View.OnClickListener() {
            //Back button clicked
            @Override
            public void onClick(View v) {
                //goes to MainActivity with userID
                Intent intent = new Intent(ClanDisplay.this, ClanActivity.class);
                intent.putExtra("ID", String.valueOf(userID));
                startActivity(intent);
            }
        });

    }

    /**
     * Displays all clans
     */
    private void displayClans() {
        // use getallclans endpoint URL
        String url = "http://coms-309-048.class.las.iastate.edu:8080/clan/getallclans";

        // make a StringRequest to get the users from the server. Converts JSONArray into StringBuilder.
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Display response", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            StringBuilder clansString = new StringBuilder();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //Prints clan ID and clan name
                                JSONObject playerObject = jsonArray.getJSONObject(i);
                                clansString.append("Clan ID: ").append(playerObject.getInt("clanID")).append("\n");
                                clansString.append("Clan name: ").append(playerObject.getString("clanName")).append("\n");
                                //clansString.append("Clan power: ").append(Double.toString(playerObject.getDouble("totalClanPower"))).append("\n");
                                clansString.append("\n");
                            }

                            Text.setText(clansString.toString()); //Sets text onscreen
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
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request); //Add to queue
    }
}