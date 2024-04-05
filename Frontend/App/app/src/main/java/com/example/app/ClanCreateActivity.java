package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ClanCreateActivity extends AppCompatActivity {

    private int userID;

    private Button Create;

    private Button Back;

    private EditText Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clan_create);

        //Get ID
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }

        Create = findViewById(R.id.Create);

        Back = findViewById(R.id.Back);

        Name = findViewById(R.id.Name);



        Back.setOnClickListener(new View.OnClickListener() {
            //Back button clicked
            @Override
            public void onClick(View v) {
                //goes to ClanActivity with userID
                Intent intent = new Intent(ClanCreateActivity.this, ClanActivity.class);
                intent.putExtra("ID", String.valueOf(userID));
                startActivity(intent);
            }
        });


        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Convert user inputs to strings
                String clannameString = Name.getText().toString(); //Username given by user

                String url = "http://coms-309-048.class.las.iastate.edu:8080/clan/getallclans"; //URL to get all existing users
                // make a StringRequest to get the users from the server. Converts JSONArray into StringBuilder.
                StringRequest request = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Display response", response);
                                try {
                                    JSONArray jsonArray = new JSONArray(response); //Array of clans
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject clanObject = jsonArray.getJSONObject(i); //Get clan at current i
                                        if ((clanObject.getString("clanName")).equals((Name.getText().toString()))) { //If the user name exists
                                            Toast toast = Toast.makeText(ClanCreateActivity.this, "Clan already exists", Toast.LENGTH_SHORT);
                                            toast.show();
                                            return; //exit
                                        }
                                    }
                                    createNewClan(clannameString); //Creates clan


                                    return;

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
        });
    }

    private void createNewClan(String clanname) {
        String url = "http://coms-309-048.class.las.iastate.edu:8080/clans/createclan";

        // Create a JSONObject with the clan's details
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("playerID", userID);
            requestBody.put("clanName", clanname);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a JsonObjectRequest with the POST method
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle successful response from the server
                        Log.d("Clan Creation", "New clan created: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response from the server
                        Log.e("User Creation", "Error creating user: " + error.getMessage());
                    }
                });

        // add to volley request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
        Intent intent = new Intent(ClanCreateActivity.this, ClanActivity.class);
        intent.putExtra("ID", String.valueOf(userID));
        startActivity(intent);
    }
}
