package com.example.app;

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

import java.util.Objects;

public class SignupSuccessActivity extends AppCompatActivity {

    private TextView messageText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_success);


        //Initialize UI
        messageText = findViewById(R.id.textView);
        loginButton = findViewById(R.id.button);

        Bundle extras = getIntent().getExtras();

        //To Main Menu button pressed
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupSuccess();
            }
        });
    }
    private void SignupSuccess(){
        String url = "http://coms-309-048.class.las.iastate.edu:8080/players/getall"; //URL to get all existing users
        // make a StringRequest to get the users from the server. Converts JSONArray into StringBuilder.
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Display response", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response); //Array of users
                            Intent intent = new Intent(SignupSuccessActivity.this, MainActivity.class);
                            intent.putExtra("ID", Integer.toString(jsonArray.getJSONObject(jsonArray.length() - 1).getInt("playerID")));
                            startActivity(intent); //go to SignupSuccess activity
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
