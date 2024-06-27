package edu.school21.sockets.server;


import edu.school21.sockets.repositories.ChatRoomRepository;
import edu.school21.sockets.repositories.MessageRepository;
import edu.school21.sockets.services.UsersService;
import edu.school21.sockets.server.ClientHandler;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


@Component
public class Server {
    private final ServerSocket serverSocket;
    @Getter
    @Autowired
    private final UsersService usersService;
    @Getter
    @Autowired
    private final MessageRepository messageRepository;
    @Getter
    @Autowired
    private final ChatRoomRepository chatRoomRepository;
    private List<ClientHandler> clients = new ArrayList<>();

    public Server(int port, UsersService usersService, MessageRepository messageRepository, ChatRoomRepository chatRoomRepository) throws IOException {
        serverSocket = new ServerSocket(port);
        this.usersService = usersService;
        this.messageRepository = messageRepository;
        this.chatRoomRepository = chatRoomRepository;
    }

    public void start() throws IOException {
        System.out.println("Server started...");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected!");
            ClientHandler clientHandler = new ClientHandler(clientSocket, this);
            clients.add(clientHandler);
            new Thread(clientHandler).start();
        }

    }

    public void broadcastMessage(String message, Long roomId) {
        for (ClientHandler client : clients) {
            if (client.getRoomId().equals(roomId)) {
                client.sendMessage(message);
            }
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

}
