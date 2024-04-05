package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class ClanManagement extends AppCompatActivity {

    private ArrayList<User> List;

    private int clanID;

    private int userID;

    private int permission;

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

        //Get ID
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
            clanID = extras.getInt("clanID");
        }

        List = new ArrayList<>();
        fillList();

        Index = 0;

        Back =  findViewById(R.id.Back);

        Left =  findViewById(R.id.Left);

        Right =  findViewById(R.id.Right);

        Kick =  findViewById(R.id.Kick);

        Demote =  findViewById(R.id.Demote);

        Promote =  findViewById(R.id.Promote);

        Display =  findViewById(R.id.Display);


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

        Left.setOnClickListener(new View.OnClickListener() {
            //Back button clicked
            @Override
            public void onClick(View v) {
                if(Index == 0) Index = List.size() -1;
                else Index--;
                Display.setText("User: " + List.get(Index).name + "\nPower: " + List.get(Index).userPower + "\nRank: " + getRank(List.get(Index).userPermission));
            }
        });

        Right.setOnClickListener(new View.OnClickListener() {
            //Back button clicked
            @Override
            public void onClick(View v) {
                if(Index == List.size() -1) Index = 0;
                else Index++;
                Display.setText("User: " + List.get(Index).name + "\nPower: " + List.get(Index).userPower + "\nRank: " + getRank(List.get(Index).userPermission));
            }
        });

        Kick.setOnClickListener(new View.OnClickListener() {
            //Back button clicked
            @Override
            public void onClick(View v) {
                if(permission > List.get(Index).userPermission) {
                    int before;
                    kickCurrent();
                    List.remove(Index);
                    if(Index == List.size()) Index--;
                }
                else{
                    Toast toast = Toast.makeText(ClanManagement.this, "You Don't have Permission to do this", Toast.LENGTH_SHORT);
                    toast.show();
                }
                Display.setText("User: " + List.get(Index).name + "\nPower: " + List.get(Index).userPower + "\nRank: " + getRank(List.get(Index).userPermission));


            }
        });

        Demote.setOnClickListener(new View.OnClickListener() {
            //Back button clicked
            @Override
            public void onClick(View v) {
                if(List.get(Index).userPermission == 1){
                    Toast toast = Toast.makeText(ClanManagement.this, "User is already the lowest rank", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(permission > List.get(Index).userPermission) {
                    demoteCurrent();
                    List.get(Index).userPermission--;

                }
                else{
                    Toast toast = Toast.makeText(ClanManagement.this, "You Don't have Permission to do this", Toast.LENGTH_SHORT);
                    toast.show();
                }
                Display.setText("User: " + List.get(Index).name + "\nPower: " + List.get(Index).userPower + "\nRank: " + getRank(List.get(Index).userPermission));

            }
        });

        Promote.setOnClickListener(new View.OnClickListener() {
            //Back button clicked
            @Override
            public void onClick(View v) {
                if(List.get(Index).userPermission == 3){
                    Toast toast = Toast.makeText(ClanManagement.this, "User is already the highest rank", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(permission > List.get(Index).userPermission){
                    promoteCurrent();
                    if(permission == 3 && List.get(Index).userPermission == 2){
                        permission = 2;
                        List.get(Index).userPermission = 3;
                    }
                    else List.get(Index).userPermission++;
                }
                else{
                    Toast toast = Toast.makeText(ClanManagement.this, "You Don't have Permission to do this", Toast.LENGTH_SHORT);
                    toast.show();
                }
                Display.setText("User: " + List.get(Index).name + "\nPower: " + List.get(Index).userPower + "\nRank: " + getRank(List.get(Index).userPermission));

            }
        });
    }

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
                                if(playerObject.getInt("playerID") == userID) permission = playerObject.getInt("clanPermissions");
                                List.add(new User(playerObject.getInt("playerID"), (int)playerObject.getDouble("power"), playerObject.getInt("clanPermissions"), playerObject.getString("userName")));
                            }
                            Display.setText("User: " + List.get(Index).name + "\nPower: " + List.get(Index).userPower + "\nRank: " + getRank(List.get(Index).userPermission));

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

    private String getRank(int n){
        switch(n){
            case 1 : return "Member";
            case 2 : return "Elder";
            case 3 : return "Leader";
            default : return Integer.toString(n);
        }
    }

    private void kickCurrent() {
        String url = "http://coms-309-048.class.las.iastate.edu:8080/clans/removemember";

        // Create a JSONObject with the clan's details
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

    private void demoteCurrent(){
        String url = "http://coms-309-048.class.las.iastate.edu:8080/clan/demotemember";

        // Create a JSONObject with the clan's details
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

    private void promoteCurrent(){
        String url = "http://coms-309-048.class.las.iastate.edu:8080/clans/promotemember";

        // Create a JSONObject with the clan's details
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