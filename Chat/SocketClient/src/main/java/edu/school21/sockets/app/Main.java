package edu.school21.sockets.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import edu.school21.sockets.client.Client;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ArgCommander commander = new ArgCommander();
        try {
            JCommander.newBuilder().addObject(commander).build().parse(args);
            Client client = new Client("localhost", commander.serverPort);
            client.start();
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
        }

    }
}
