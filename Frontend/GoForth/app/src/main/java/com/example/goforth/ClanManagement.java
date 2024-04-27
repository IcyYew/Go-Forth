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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Activity to manage current clan
 */
public class ClanManagement extends AppCompatActivity {

    private ArrayList<User> List;

    private int clanID;

    private int userID;

    private int permission;

    private int userIndex;


    Button Back;

    Button Left;

    Button Right;

    Button Kick;

    Button Demote;

    Button Promote;

    TextView Display;

    private int Index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clan_management);

        //Get ID and ClanID
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
            clanID = extras.getInt("clanID");
        }

        List = new ArrayList<>(); //Create a list of users
        fillList(); //Fills list of users

        Index = 0;


        Back =  findViewById(R.id.Back);

        Left =  findViewById(R.id.Left);

        Right =  findViewById(R.id.Right);

        Kick =  findViewById(R.id.Kick);

        Demote =  findViewById(R.id.Demote);

        Promote =  findViewById(R.id.Promote);

        Display =  findViewById(R.id.Display);


        /**
         * Goes back to ClanActivity
         */
        Back.setOnClickListener(new View.OnClickListener() {
            //Back button clicked
            @Override
            public void onClick(View v) {
                //goes to ClanActivity with userID
                Intent intent = new Intent(ClanManagement.this, ClanActivity.class);
                intent.putExtra("ID", String.valueOf(userID));
                startActivity(intent);
            }
        });

        /**
         * Moves left through the list
         */
        Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Index == 0) Index = List.size() -1; //If the index is at 0, go all the way to the right of the list
                else Index--; //Current position to the leeft
                Display.setText("User: " + List.get(Index).name + "\nPower: " + List.get(Index).userPower + "\nRank: " + getRank(List.get(Index).userPermission)); //set text
            }
        });

        /**
         * Moves left through the list
         */
        Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Index == List.size() -1) Index = 0; //If index is at the far right, go to the beggining
                else Index++; //Goes right through the list
                Display.setText("User: " + List.get(Index).name + "\nPower: " + List.get(Index).userPower + "\nRank: " + getRank(List.get(Index).userPermission)); //set text
            }
        });

        /**
         * Kick the current player shown on screen
         */
        Kick.setOnClickListener(new View.OnClickListener() {@Override
            public void onClick(View v) {
                if(permission > List.get(Index).userPermission) { //If user permission is greater than the current
                    int before;
                    kickCurrent(); //Kick the current player
                    List.remove(Index); //Remove player from list
                    if(Index == List.size()) Index--; //Go to the left of the list if currently on the far right
                }
                else{ //If user does not have permission over the current player
                    Toast toast = Toast.makeText(ClanManagement.this, "You Don't have Permission to do this", Toast.LENGTH_SHORT);
                    toast.show();
                }
                Display.setText("User: " + List.get(Index).name + "\nPower: " + List.get(Index).userPower + "\nRank: " + getRank(List.get(Index).userPermission)); //set text


            }
        });

        /**
         * Demote the current player selected
         */
        Demote.setOnClickListener(new View.OnClickListener() {
            //Back button clicked
            @Override
            public void onClick(View v) {
                if(List.get(Index).userPermission == 1){ //Player is a member
                    Toast toast = Toast.makeText(ClanManagement.this, "User is already the lowest rank", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(permission > List.get(Index).userPermission) { //Player has permission over the other
                    demoteCurrent();
                    List.get(Index).userPermission--;

                }
                else{ //Player does not have permission over the other
                    Toast toast = Toast.makeText(ClanManagement.this, "You Don't have Permission to do this", Toast.LENGTH_SHORT);
                    toast.show();
                }
                Display.setText("User: " + List.get(Index).name + "\nPower: " + List.get(Index).userPower + "\nRank: " + getRank(List.get(Index).userPermission));

            }
        });

        /**
         * Promotes the current player selected
         */
        Promote.setOnClickListener(new View.OnClickListener() {
            //Back button clicked
            @Override
            public void onClick(View v) {
                if(List.get(Index).userPermission == 3){ //If user is a leader
                    Toast toast = Toast.makeText(ClanManagement.this, "User is already the highest rank", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(permission > List.get(Index).userPermission){ //If user promoting is Leader and target is Elder
                    promoteCurrent(); //Promote
                    if(permission == 3 && List.get(Index).userPermission == 2){ //Swap their permissions
                        permission = 2;
                        List.get(Index).userPermission = 3;
                        List.get(userIndex).userPermission = 2;
                    }
                    else List.get(Index).userPermission++; //Increment permission on the list
                }
                else{
                    Toast toast = Toast.makeText(ClanManagement.this, "You Don't have Permission to do this", Toast.LENGTH_SHORT);
                    toast.show();
                }
                Display.setText("User: " + List.get(Index).name + "\nPower: " + List.get(Index).userPower + "\nRank: " + getRank(List.get(Index).userPermission)); //set text

            }
        });
    }

    /**
     * Class to store user data
     */
    private class User{

        private int userID;

        private int userPower;

        private int userPermission;

        private String name;

        User(int userID, int userPower, int userPermission, String name){
            this.userID = userID;
            this.userPower = userPower;
            this.userPermission = userPermission;
            this.name = name;
        }

    }

    /**
     * Fills the list based on clan ID
     */
    private void fillList(){
        // use getall endpoint URL
        String url = "http://coms-309-048.class.las.iastate.edu:8080/clans/memberlist/" + Integer.toString(clanID);
        List.clear();
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
                                if(playerObject.getInt("playerID") == userID){ //If the user is the current user
                                    permission = playerObject.getInt("clanPermissions"); //set user permission
                                    userIndex = i; //set user index
                                }
                                List.add(new User(playerObject.getInt("playerID"), (int)playerObject.getDouble("power"), playerObject.getInt("clanPermissions"), playerObject.getString("userName"))); //Add new player
                            }
                            Display.setText("User: " + List.get(Index).name + "\nPower: " + List.get(Index).userPower + "\nRank: " + getRank(List.get(Index).userPermission)); //set text

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
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * Converts integer rank to string rank
     * @param n integer representation of rank
     * @return corresponding string
     */
    private String getRank(int n){
        switch(n){
            case 1 : return "Member";
            case 2 : return "Elder";
            case 3 : return "Leader";
            default : return Integer.toString(n);
        }
    }

    /**
     * Kicks the current player
     */
    private void kickCurrent() {
        String url = "http://coms-309-048.class.las.iastate.edu:8080/clans/removemember";

        // Create a JSONObject with the player's details
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("clanID", clanID);
            requestBody.put("playerID", List.get(Index).userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a JsonObjectRequest with the POST method
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle successful response from the server
                        Log.d("Player Kicked", "player kicked: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response from the server
                        Log.e("Kick Player", "Error kicking player: " + error.getMessage());
                    }
                });

        // add to volley request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * Demotes current user
     */
    private void demoteCurrent(){
        String url = "http://coms-309-048.class.las.iastate.edu:8080/clan/demotemember";

        // Create a JSONObject with current and target users information
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("initiatorPermissionsLevel", permission);
            requestBody.put("initiatorID", userID);
            requestBody.put("targetPermissionsLevel", List.get(Index).userPermission);
            requestBody.put("targetID", List.get(Index).userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a JsonObjectRequest with the POST method
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle successful response from the server
                        Log.d("Player Demoted", "player demoted: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response from the server
                        Log.e("Demote Player", "Error demoting player: " + error.getMessage());
                    }
                });

        // add to volley request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * Promotes current player
     */
    private void promoteCurrent(){
        String url = "http://coms-309-048.class.las.iastate.edu:8080/clans/promotemember";

        // Create a JSONObject with current and target users information
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("initiatorPermissionsLevel", permission);
            requestBody.put("initiatorID", userID);
            requestBody.put("targetPermissionsLevel", List.get(Index).userPermission);
            requestBody.put("targetID", List.get(Index).userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a JsonObjectRequest with the POST method
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle successful response from the server
                        Log.d("Player promoted", "player promoted: " + response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response from the server
                        Log.e("Promote Player", "Error promoting player: " + error.getMessage());
                    }
                });

        // add to volley request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}