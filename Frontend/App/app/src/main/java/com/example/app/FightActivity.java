package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class FightActivity extends AppCompatActivity {
    private EditText user1;
    private EditText user2;
    private TextView user1confirmed;
    private TextView user2confirmed;
    private TextView resultTextView;
    private Button confirm;
    private Button back;
    private Button fight;
    private int user1ID;
    private int user2ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        user1 = findViewById(R.id.user1Input);
        user2 = findViewById(R.id.user2Input);
        user1confirmed = findViewById(R.id.user1confirmed);
        user2confirmed = findViewById(R.id.user2confirmed);
        resultTextView = findViewById(R.id.result);
        confirm = findViewById(R.id.Button);
        back = findViewById(R.id.back);
        fight = findViewById(R.id.fightButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FightActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user1ID = Integer.parseInt(user1.getText().toString());
                user2ID = Integer.parseInt(user2.getText().toString());

                user1confirmed.setText("User 1: " + user1ID);
                user2confirmed.setText("User 2: " + user2ID);
            }
        });

        fight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFight(user1ID, user2ID);
            }
        });
    }

    private void startFight(int player1ID, int player2ID) {
        String url = "http://10.0.2.2:8080/players/fight/" + player1ID + "/" + player2ID;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        resultTextView.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error during fight: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
