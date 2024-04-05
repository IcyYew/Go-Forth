package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ClanJoin extends AppCompatActivity {

    private int userID;

    private EditText Name;

    private Button Join;

    private Button Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clan_join);

        //Get ID
        //Get ID
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }

        //Join = findViewById(R.id.Create);

        //Back = findViewById(R.id.Back);

        //Name = findViewById(R.id.Name);

        Back.setOnClickListener(new View.OnClickListener() {
            //Back button clicked
            @Override
            public void onClick(View v) {
                //goes to MainActivity with userID
                Intent intent = new Intent(ClanJoin.this, ClanActivity.class);
                intent.putExtra("ID", String.valueOf(userID));
                startActivity(intent);
            }
        });


        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://coms-309-048.class.las.iastate.edu:8080/players/getall"; //URL to get all players
                String clanString = Name.getText().toString(); //Get username inputed// make a StringRequest to get the users from the server. Converts JSONArray into StringBuilder.
                StringRequest request = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Display response", response);
                                try {
                                    JSONArray jsonArray = new JSONArray(response); //Array of users
                                    for (int i = 0; i < jsonArray.length(); i++) { //loop to get all players
                                        JSONObject playerObject = jsonArray.getJSONObject(i); //gets user at I
                                        if (playerObject.getString("clanName").equals(clanString)){
                                        }

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //username does not exist
                                Toast toast = Toast.makeText(ClanJoin.this, "Clan Does not Exist", Toast.LENGTH_SHORT);
                                toast.show();
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
        });
    }
}