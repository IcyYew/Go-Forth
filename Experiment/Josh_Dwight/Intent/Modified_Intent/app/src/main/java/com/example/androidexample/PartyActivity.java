package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;

public class PartyActivity extends AppCompatActivity {
    private Button returnButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);

        returnButton = findViewById(R.id.return_btn);

        ImageView gifImageView = findViewById(R.id.gif_image);

        Glide.with(this)
                .load(R.raw.celebrate_party)
                .into(gifImageView);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PartyActivity.this, MainActivity.class);
                intent.putExtra("PARTY", String.valueOf(1));
                startActivity(intent);
            }
        });
    }
}
