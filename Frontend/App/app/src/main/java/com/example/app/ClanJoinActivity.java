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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity to join a clan
 */
public class ClanJoinActivity extends AppCompatActivity {

    private int userID;

    private EditText Name;

    private Button Join;

    private Button Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clan_join2);

        //Get ID
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }

        Join = findViewById(R.id.Join);

        Back = findViewById(R.id.Back);

        Name = findViewById(R.id.Name);

        /**
         * Goes back to ClanActivity
         */
        Back.setOnClickListener(new View.OnClickListener() {
            //Back button clicked
            @Override
            public void onClick(View v) {
                //goes to MainActivity with userID
                Intent intent = new Intent(ClanJoinActivity.this, ClanActivity.class);
                intent.putExtra("ID", String.valueOf(userID));
                startActivity(intent);
            }
        });


        /**
         * Joins clan inputted by user
         */
        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://coms-309-048.class.las.iastate.edu:8080/clan/getallclans"; //URL to get all players
                String clanString = Name.getText().toString(); //Get clan inputed
                StringRequest request = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Display response", response);
                                try {
                                    JSONArray jsonArray = new JSONArray(response); //Array of clans
                                    for (int i = 0; i <= jsonArray.length(); i++) {
                                        JSONObject clanObject = jsonArray.getJSONObject(i); //Get clan at current i
                                        if ((clanObject.getString("clanName")).equals((Name.getText().toString())) && clanObject.getInt("clanMembersNumber") != 50) { //If the clan exists
                                            join(jsonArray.getJSONObject(i).getInt("clanID")); //join clan
                                            return; //exit
                                        }
                                        else if(clanObject.getInt("clanMemberNumber") != 50){
                                            Toast toast = Toast.makeText(ClanJoinActivity.this, "Clan Does not Exist", Toast.LENGTH_SHORT);
                                            toast.show();
                                        }
                                        else{
                                            Toast toast = Toast.makeText(ClanJoinActivity.this, "Clan is full", Toast.LENGTH_SHORT);
                                            toast.show();
                                        }

                                        }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //username does not exist

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Error fetching clans: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                // add to the request queue
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

            }
        });
    }

    /**
     * Join a clan
     * @param ID ID of the clan to join
     */
    private void join(int ID) {
        String url = "http://coms-309-048.class.las.iastate.edu:8080/clans/addmember";

        // Create a JSONObject with the clan's details
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("clanID", ID);
            requestBody.put("playerID", userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a JsonObjectRequest with the POST method
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle successful response from the server
                        Log.d("Clan Join", "clan joined: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response from the server
                        Log.e("Clan Join", "Error joining clan: " + error.getMessage());
                    }
                });

        // add to volley request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
        //goes to ClanActivity with userID
        Intent intent = new Intent(ClanJoinActivity.this, ClanActivity.class);
        intent.putExtra("ID", String.valueOf(userID));
        startActivity(intent);
    }
}