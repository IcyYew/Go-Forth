package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText Username;

    private EditText Password;

    private Button Login;

    private Button Back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = findViewById(R.id.username);

        Password = findViewById(R.id.password);

        Login = findViewById(R.id.Button);

        Back = findViewById(R.id.back);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameString = Username.getText().toString();
                String passwordString = Password.getText().toString();
                //TODO: Get actual username and password data from backend
                if(usernameString.equals("username")){
                    if(passwordString.equals("password")){
                        Toast toast = Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        Toast toast = Toast.makeText(LoginActivity.this, "Username or password do not exist (Password)", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else{
                    Toast toast = Toast.makeText(LoginActivity.this, "Username or password do not exist (Username)", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }
}
