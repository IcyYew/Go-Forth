package Websockets;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@ServerEndpoint(value = "chat/globalchat/{username}")
public class GlobalChatSocket
{
    private static GlobalChatRepository chatRepository;

    @Autowired
    public void setGlobalChatRepository(GlobalChatRepository chatRepo)
    {
        chatRepository = chatRepo;
    }

    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();

    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(GlobalChatSocket.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username)
            throws IOException {

        logger.info("Entered into Open");

        // store connecting user information
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

        //Send chat history to the newly connected user
        sendMessageToPArticularUser(username, getChatHistory());

        // broadcast that new user joined
        /*String message = "User:" + username + " has Joined the Chat";
        broadcast(message);*/
    }
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {

        // Handle new messages
        logger.info("Entered into Message: Got Message:" + message);
        String username = sessionUsernameMap.get(session);

        broadcast(username + ": " + message);


        // Saving chat history to repository
        chatRepository.save(new GlobalChatMessage(username, message));
    }


    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");

        // remove the user connection information
        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        // broadcase that the user disconnected
        String message = username + " disconnected";
        broadcast(message);
    }


    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error");
        throwable.printStackTrace();
    }


    private void sendMessageToPArticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        }
        catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }


    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            }
            catch (IOException e) {
                logger.info("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }

        });

    }

    private String getChatHistory() {
        List<GlobalChatMessage> messages = chatRepository.findAll();

        // convert the list to a string
        StringBuilder sb = new StringBuilder();
        if(messages != null && messages.size() != 0) {
            for (GlobalChatMessage message : messages) {
                sb.append(message.getUsername() + ": " + message.getContent() + "\n");
            }
        }
        return sb.toString();
    }

}
