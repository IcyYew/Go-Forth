package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    private EditText Username;

    private EditText Password;

    private EditText ConfirmPassword;

    private Button Signup;

    private Button Back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Username = findViewById(R.id.username);

        Password = findViewById(R.id.password);

        ConfirmPassword = findViewById(R.id.confirm);

        Signup = findViewById(R.id.Button);

        Back = findViewById(R.id.back);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* when login button is pressed, use intent to switch to Login Activity */
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);  // go to LoginActivity
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameString = Username.getText().toString();
                String passwordString = Password.getText().toString();
                String confirmString = ConfirmPassword.getText().toString();
                //TODO: implement checking if username is used once frontend-backend communication is working
                /*
                if UsernameUsed:
                    print("Username used, pick a new one")
                 */
                if(passwordString.equals(confirmString)){
                    //TODO: Send new data to be saved in backend
                    //SendNewUser(usernameString, passwordString);

                    Intent intent = new Intent(SignupActivity.this, SignupSuccessActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast toast = Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
            }
        });
    }

}
