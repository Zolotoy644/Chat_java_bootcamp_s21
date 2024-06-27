package edu.school21.sockets.server;

import edu.school21.sockets.models.ChatRoom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import lombok.Getter;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {

    private Server server;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String name;
    @Getter
    private Long roomId;
    private final JsonMessageHandler jsonMessageHandler;

    public ClientHandler(Socket clientSocket, Server server) {
        this.server = server;
        this.clientSocket = clientSocket;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        jsonMessageHandler = new JsonMessageHandler(server);
    }

    @Override
    public void run() {
        try {
            firstMenu();
            secondMenu();
            sendLastMessages();

            while (true) {
                String message = in.readLine();
                jsonMessageHandler.handleMessage(message);

            }


        } catch (IOException e) {
            System.out.println("Client " + name + " disconnected!");
            server.removeClient(this);
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void firstMenu() throws IOException {
        out.println("Hello from Server!");
        out.println("1. signIn");
        out.println("2. SignUp");
        out.println("3. Exit");

        while (true) {
            String answer = in.readLine();
            if (answer.equals("2")) {
                out.println("Enter username: ");
                String username = in.readLine();
                out.println("Enter password: ");
                String password = in.readLine();
                server.getUsersService().signUp(username, password);
                User user = server.getUsersService().getUserByUsername(username);
                name = user.getUsername();
                break;
            } else if (answer.equals("1")) {
                out.println("Enter username: ");
                String username = in.readLine();
                out.println("Enter password: ");
                String password = in.readLine();
                server.getUsersService().signIn(username, password);
                User user = server.getUsersService().getUserByUsername(username);
                name = user.getUsername();
                System.out.println(name + " connected");
                break;
            } else if (answer.equals("3")) {
                System.out.println("Client " + name + " disconnected!");
                server.removeClient(this);
            } else {
                out.println("Invalid input!");
            }
        }
    }

    public void secondMenu() throws IOException {
        out.println("1. Create room\n" +
                "2. Choose room\n" +
                "3. Exit");

        while (roomId == null) {
            String answer = in.readLine();
            if (answer.equals("1")) {
                out.println("Enter room name: ");
                String roomName = in.readLine();
                ChatRoom chatRoom = new ChatRoom(server.getChatRoomRepository().generateId(), roomName, null);
                server.getChatRoomRepository().save(chatRoom);
                roomId = chatRoom.getId();
                out.println(roomName);
                out.println(roomId);
            } else if (answer.equals("2")) {
                List<ChatRoom> rooms = server.getChatRoomRepository().findAll();
                String roomsName = "";
                for (ChatRoom room : rooms) {
                    roomsName += room.toStringForMenu() + "\n";
                }
                DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                dataOutputStream.writeUTF(roomsName);
                String roomIdString = in.readLine();
                roomId = Long.parseLong(roomIdString);
                ChatRoom room = server.getChatRoomRepository().findById(roomId).get();
                out.println(room.getName());
            } else if (answer.equals("3")) {
                System.out.println("Client " + name + " disconnected!");
                server.removeClient(this);
            } else {
                out.println("Invalid input!");
            }
        }
    }

    public void sendLastMessages() throws IOException {
        ChatRoom room = server.getChatRoomRepository().findById(roomId).get();
        room.setMessages(server.getMessageRepository().findByRoomId(roomId));
        if (room.getMessages().size() < 31) {
            for (Message message : room.getMessages()) {
                server.broadcastMessage(message.getSender() + ": " + message.getText(), roomId);
            }
        } else {
            for (int i = room.getMessages().size() - 30; i < room.getMessages().size(); i++) {
                server.broadcastMessage(room.getMessages().get(i).getSender() + ": "
                        + room.getMessages().get(i).getText(), roomId);
            }
        }


    }

}
