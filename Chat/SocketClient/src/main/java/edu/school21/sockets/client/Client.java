package edu.school21.sockets.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private final Socket clientSocket;
    private final PrintWriter out;
    private final BufferedReader in;
    private final JsonMessageSender jsonMessageSender;
    private Long roomId;
    private String sender;


    public Client(String host, int port) throws IOException {
        clientSocket = new Socket(host, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        jsonMessageSender = new JsonMessageSender(out);
    }

    public void start() throws IOException {
        Scanner scanner = new Scanner(System.in);
        firstMenuRead();
        secondMenuRead();

        new Thread(() -> {
            try {
                while (true) {

                    String message = in.readLine();
                    System.out.println(message);

                }
            } catch (IOException e) {
                System.out.println("Connection lost!");
            }
        }).start();

        while (true) {
            String message = scanner.nextLine();
            if (message.equals("Exit")) {
                System.out.println("You have left the chat.");
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.exit(0);
            }
            jsonMessageSender.sendMessage(message, sender, roomId);
        }
    }

    public void firstMenuRead() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String input;

        for (int i = 0; i < 4; i++) {
            input = in.readLine();
            System.out.println(input);
        }
        while (true) {
            String command = scanner.nextLine();
            out.println(command);
            if (command.equals("2")) {
                input = in.readLine();
                System.out.println(input);
                String username = scanner.nextLine();
                out.println(username);
                input = in.readLine();
                System.out.println(input);
                String password = scanner.nextLine();
                out.println(password);
                sender = username;
                break;
            } else if (command.equals("1")) {
                input = in.readLine();
                System.out.println(input);
                String username = scanner.nextLine();
                out.println(username);
                input = in.readLine();
                System.out.println(input);
                String password = scanner.nextLine();
                out.println(password);
                sender = username;
                break;
            } else if (command.equals("3")) {
                out.println(command);
                System.out.println("Goodbye!");
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.exit(0);
            }
        }
    }

    private void secondMenuRead() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String input;
        for (int i = 0; i < 3; i++) {
            input = in.readLine();
            System.out.println(input);
        }

        while (true) {
            String command = scanner.nextLine();
            out.println(command);
            if (command.equals("1")) {
                input = in.readLine();
                System.out.println(input);
                String roomName = scanner.nextLine();
                out.println(roomName);
                input = in.readLine();
                System.out.println(input);
                input = in.readLine();
                roomId = Long.parseLong(input);
                break;
            } else if (command.equals("2")) {
                DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
                input = dataInputStream.readUTF();
                System.out.print(input);
                String roomNum = scanner.nextLine();
                out.println(roomNum);
                roomId = Long.parseLong(roomNum);
                input = in.readLine();
                System.out.println(input);
                break;
            } else if (command.equals("3")) {
                out.println(command);
                System.out.println("Goodbye!");
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.exit(0);
            }
        }
    }


}