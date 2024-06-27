package edu.school21.sockets.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import edu.school21.sockets.models.Message;

import java.time.LocalDateTime;

public class JsonMessageHandler {
    private final Gson gson;
    private final Server server;

    public JsonMessageHandler(Server server) {
        this.server = server;
        this.gson = new Gson();
    }

    public void handleMessage(String jsonMessage) {
        JsonObject jsonObject = gson.fromJson(jsonMessage, JsonObject.class);

        String message = jsonObject.get("message").getAsString();
        String sender = jsonObject.get("sender").getAsString();
        Long roomId = jsonObject.get("roomId").getAsLong();

        server.broadcastMessage(sender + ": " + message, roomId);
        Message newMessage = new Message(server.getMessageRepository().generateId(), message, sender, roomId, LocalDateTime.now());
        server.getMessageRepository().save(newMessage);

        System.out.println("Received message: " + message);
        System.out.println("Sender: " + sender);
        System.out.println("Room ID: " + roomId);
    }
}

