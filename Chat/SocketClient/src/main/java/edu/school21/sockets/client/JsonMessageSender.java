package edu.school21.sockets.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.PrintWriter;

public class JsonMessageSender {
    private final PrintWriter out;
    private final Gson gson;

    public JsonMessageSender(PrintWriter out) {
        this.out = out;
        this.gson = new Gson();
    }

    public void sendMessage(String message, String sender, Long roomId) {
        JsonObject jsonMessage = new JsonObject();
        jsonMessage.addProperty("message", message);
        jsonMessage.addProperty("sender", sender);
        jsonMessage.addProperty("roomId", roomId);

        String json = gson.toJson(jsonMessage);
        out.println(json);
    }
}