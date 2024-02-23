package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Map;
import java.util.HashMap;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class DummyActivity extends AppCompatActivity {
    private int userID;
    private EditText Username;

    private EditText Password;

    private Button confirm;

    private Button back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }

        //UI initialization
        Username = findViewById(R.id.username);

        Password = findViewById(R.id.password);

        confirm = findViewById(R.id.confirm);

        back = findViewById(R.id.back);

        //Back button clicked
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when login button is pressed, use intent to switch to Login Activity */
                Intent intent = new Intent(DummyActivity.this, MainActivity.class);
                intent.putExtra("ID", userID);
                startActivity(intent);  // go to LoginActivity
            }
        });

        //Signup button clicked
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Convert user inputs to strings
                String usernameString = Username.getText().toString();
                String passwordString = Password.getText().toString();

                createNewPlayer(usernameString, passwordString);
            }
        });
    }

    private void createNewPlayer(String userName, String password) {
        String url = "http://10.0.2.2:8080/players/new";

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
                        // Optionally, update your UI or perform any additional actions
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response from the server
                        Log.e("User Creation", "Error creating user: " + error.getMessage());
                    }
                });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
