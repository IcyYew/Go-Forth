package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import org.java_websocket.handshake.ServerHandshake;
import org.w3c.dom.Text;

import java.util.Objects;
public class ClanChat extends AppCompatActivity implements WebSocketListener{

    private String BASE_URL = "ws://10.0.2.2:8080/chat/clan/";

    private Button Back;
    private TextView Chat;

    private EditText Message;

    private Button SendMessage;

    private int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clan_chat);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }

        Back = findViewById(R.id.Back);

        Chat = findViewById(R.id.textView2);

        SendMessage = findViewById(R.id.SendButton);
        
        Message = findViewById(R.id.input);

        String serverUrl = BASE_URL + "Test_User";

        // Establish WebSocket connection and set listener
        ClanChatManager.getInstance().connectWebSocket(serverUrl);
        ClanChatManager.getInstance().setWebSocketListener(ClanChat.this);

        Back.setOnClickListener(new View.OnClickListener() {
            //Back button clicked
            @Override
            public void onClick(View v) {
                //goes to MainActivity with userID
                Intent intent = new Intent(ClanChat.this, MainActivity.class);
                intent.putExtra("ID", String.valueOf(userID));
                startActivity(intent);
            }
        });

        /* send button listener */
        SendMessage.setOnClickListener(v -> {
            try {
                // send message
                ClanChatManager.getInstance().sendMessage(Message.getText().toString());
            } catch (Exception e) {
                Log.d("ExceptionSendMessage:", e.getMessage().toString());
            }
        });
    }

    @Override
    public void onWebSocketMessage(String message) {
        /**
         * In Android, all UI-related operations must be performed on the main UI thread
         * to ensure smooth and responsive user interfaces. The 'runOnUiThread' method
         * is used to post a runnable to the UI thread's message queue, allowing UI updates
         * to occur safely from a background or non-UI thread.
         */
        runOnUiThread(() -> {
            String s = Chat.getText().toString();
            Chat.setText(s + "\n"+message);
        });
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            String s = Chat.getText().toString();
            Chat.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
        });
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}

    @Override
    public void onWebSocketError(Exception ex) {}
}