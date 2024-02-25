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

import java.util.HashMap;
import java.util.Map;

/**
 * Activity that displays the current users (assuming there are any)
 */
public class DisplayActivity extends AppCompatActivity {
    // TextView to display users info (name, id, power level)
    private TextView users;

    // Button to go back to main
    private Button back;

    // tracks user ID so it can track across activities
    private int userID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_users);

        // getting the extras so ID tracks across activities
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }

        users = findViewById(R.id.msgResponse);
        back = findViewById(R.id.back);

        displayUsers();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this, MainActivity.class);
                intent.putExtra("ID", String.valueOf(userID));
                startActivity(intent);
            }
        });
    }

    /**
     * Uses the /players/getall endpoint to display users in a String format (converts endpoint's json output to string using StringBuilder)
     */
    private void displayUsers() {
        // use getall endpoint URL
        String url = "http://10.0.2.2:8080/players/getall";

        // make a StringRequest to get the users from the server. Converts JSONArray into StringBuilder.
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            StringBuilder playersString = new StringBuilder();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject playerObject = jsonArray.getJSONObject(i);
                                playersString.append("Player ID: ").append(playerObject.getInt("playerID")).append("\n");
                                playersString.append("Player name: ").append(playerObject.getString("userName")).append("\n");
                                playersString.append("Player power level: ").append(playerObject.getInt("power")).append("\n");
                                playersString.append("\n");
                            }

                            users.setText(playersString.toString());
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
}
