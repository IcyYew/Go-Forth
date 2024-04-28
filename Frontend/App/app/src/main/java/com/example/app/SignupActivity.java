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

import java.util.Objects;

/**
 * This activity is responsible for sign-up/user creation.
 *
 * @author Nicholas Lynch
 */
public class SignupActivity extends AppCompatActivity {
    private EditText Username;

    private EditText Password;

    private EditText ConfirmPassword;

    private Button Signup;

    private Button Back;

    private int userID;

    /**
     * On the creation of this activity, TextViews and Buttons are initialized.
     * Extras are received and put in userID variable (for carrying across activities)
     * The signup button's on-click is used for making a GET (and then POST) to see if the user exists, and then POSTs if it doesn't.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }

        //UI initialization
        Username = findViewById(R.id.username);

        Password = findViewById(R.id.password);

        ConfirmPassword = findViewById(R.id.confirm);

        Signup = findViewById(R.id.Button);

        Back = findViewById(R.id.back);

        //Back button clicked
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when login button is pressed, use intent to switch to Main Activity */
                Intent intent = new Intent(SignupActivity.this, LaunchActivity.class);
                startActivity(intent);  // go to MainActivity
            }
        });

        //Signup button clicked
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Convert user inputs to strings
                String usernameString = Username.getText().toString(); //Username given by user
                String passwordString = Password.getText().toString(); //Password given by user
                String confirmString = ConfirmPassword.getText().toString(); //confirm given by user

                if(!passwordString.equals(confirmString)){ //checks if password and confirm match
                    Toast toast = Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                String url = "http://coms-309-048.class.las.iastate.edu:8080/players/getall"; //URL to get all existing users
                // make a StringRequest to get the users from the server. Converts JSONArray into StringBuilder.
                StringRequest request = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Display response", response);
                                try {
                                    JSONArray jsonArray = new JSONArray(response); //Array of users
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject playerObject = jsonArray.getJSONObject(i); //Get user at current i
                                        if ((playerObject.getString("userName")).equals((Username.getText().toString()))) { //If the user name exists
                                            Toast toast = Toast.makeText(SignupActivity.this, "Username already exists", Toast.LENGTH_SHORT);
                                            toast.show();
                                            return; //exit
                                        }
                                    }
                                    //If no existing user is found, create a new user and switch to Main Activity
                                    createNewPlayer(usernameString, passwordString); //Creates player with username and password given by user
                                    Intent intent = new Intent(SignupActivity.this, SignupSuccessActivity.class);
                                    startActivity(intent); //go to SignupSuccess activity

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

    /**
     * creates a new player in the server with the given username and password
     *
     * @param userName
     * @param password
     */
    private void createNewPlayer(String userName, String password) {
        // use the new player endpoint
        String url = "http://coms-309-048.class.las.iastate.edu:8080/players/new";

        // Create a JSONObject with the user's details
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("userName", userName);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a JsonObjectRequest with the POST method
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle successful response from the server
                        Log.d("User Creation", "New user created: " + response.toString());
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
    }
}
