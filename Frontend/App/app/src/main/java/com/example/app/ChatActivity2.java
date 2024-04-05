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

/**
 * This activity is responsible for clan chat functionality.
 *
 * @author Nicholas Lynch
 */
public class ChatActivity2 extends AppCompatActivity implements WebSocketListener{

    //private Button Back;
    private TextView Chat;

    private EditText Message;

    private Button SendMessage;

    private int userID;

    /**
     * On the creation of this activity, this method initialized TextViews and Buttons.
     * It also gets any extras (userID) and assigns it to userID.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
        }

        //Back = findViewById(R.id.Back);

        Chat = findViewById(R.id.tx2);

        SendMessage = findViewById(R.id.sendBtn2);
        
        Message = findViewById(R.id.msgEdt2);

        // Establish WebSocket connection and set listener
        WebSocketManager2.getInstance().setWebSocketListener(ChatActivity2.this);


        /* send button listener */
        SendMessage.setOnClickListener(v -> {
            try {
                // send message
                WebSocketManager2.getInstance().sendMessage(Message.getText().toString());
            } catch (Exception e) {
                Log.d("ExceptionSendMessage:", e.getMessage().toString());
            }
        });
    }

    /**
     * This method is responsible for handling WebSocket messages.
     * When a message is received, it concatenates it to the current displayed text.
     *
     * @param message The received WebSocket message.
     */
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

    /**
     * This method is responsible for handling WebSocket close events.
     *
     * @param code   The status code indicating the reason for closure.
     * @param reason A human-readable explanation for the closure.
     * @param remote Indicates whether the closure was initiated by the remote endpoint.
     */
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closedBy = remote ? "server" : "local";
        runOnUiThread(() -> {
            String s = Chat.getText().toString();
            Chat.setText(s + "---\nconnection closed by " + closedBy + "\nreason: " + reason);
        });
    }

    /**
     * This method is responsible for handling WebSocket open events
     *
     * @param handshakedata Information about the server handshake.
     */
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {}

    /**
     * This method is responsible for handling WebSocket error events
     *
     * @param ex The exception that describes the error.
     */
    @Override
    public void onWebSocketError(Exception ex) {}
}