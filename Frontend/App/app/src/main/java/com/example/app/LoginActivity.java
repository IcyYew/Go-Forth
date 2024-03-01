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

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText Username;

    private EditText Password;

    private Button Login;

    private Button Back;

    private int userID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }


        //Initialize UI
        Username = findViewById(R.id.username);

        Password = findViewById(R.id.password);

        Login = findViewById(R.id.Button);

        Back = findViewById(R.id.back);

        Back.setOnClickListener(new View.OnClickListener() {
            //Back button clicked
            @Override
            public void onClick(View v) {
                //goes to MainActivity with userID
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("ID", String.valueOf(userID));
                startActivity(intent);
            }
        });
        //Login Button Clicked
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://coms-309-048.class.las.iastate.edu:8080/players/getall"; //URL to get all players
                String usernameString = Username.getText().toString(); //Get username inputed
                String passwordString = Password.getText().toString(); //Get password inputed
                // make a StringRequest to get the users from the server. Converts JSONArray into StringBuilder.
                StringRequest request = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Display response", response);
                                try {
                                    JSONArray jsonArray = new JSONArray(response); //Array of users
                                    for (int i = 0; i < jsonArray.length(); i++) { //loop to get all players
                                        JSONObject playerObject = jsonArray.getJSONObject(i); //gets user at I
                                        if (playerObject.getString("userName").equals(usernameString)) //If the username matches the current username at i
                                            if (playerObject.getString("password").equals(passwordString)) { //If the password matches the username
                                                //username and password match
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                intent.putExtra("ID", playerObject.getString("playerID")); //Brings user ID to main activity
                                                startActivity(intent);  // go to LoginActivity
                                                return;
                                            } else {
                                                //username exist but password does not match
                                                Toast toast = Toast.makeText(LoginActivity.this, "Username Doesnt exist or password doesn't match (Password)", Toast.LENGTH_SHORT);
                                                toast.show();
                                                return;
                                            }
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //username does not exist
                                Toast toast = Toast.makeText(LoginActivity.this, "Username Doesnt exist or password doesn't match (Username)", Toast.LENGTH_SHORT);
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