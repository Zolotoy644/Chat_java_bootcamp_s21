package edu.school21.sockets.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.sockets.repositories.ChatRoomRepository;
import edu.school21.sockets.repositories.MessageRepository;
import edu.school21.sockets.server.Server;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        ArgCommander commander = new ArgCommander();
        try {
            JCommander.newBuilder().addObject(commander).build().parse(args);
            ApplicationContext context = new AnnotationConfigApplicationContext(edu.school21.sockets.config.SocketsApplicationConfig.class);
            updateData("schema.sql", context);
            updateData("data.sql", context);
            //createTable(context);
            UsersService usersService = context.getBean("usersService",UsersService.class);
            MessageRepository messageRepository = context.getBean("messageRepository", MessageRepository.class);
            ChatRoomRepository chatRoomRepository = context.getBean("chatRoomRepository", ChatRoomRepository.class);
            Server server = new Server(commander.port, usersService, messageRepository, chatRoomRepository);
            server.start();
        } catch (ParameterException e) {
            System.err.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

/*    private static void createTable(ApplicationContext context) {
        DataSource dataSource = context.getBean("hikariDataSource", HikariDataSource.class);
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.execute("DROP SCHEMA IF EXISTS chat CASCADE;");
            stmt.execute("CREATE SCHEMA IF NOT EXISTS chat;");
            stmt.execute("CREATE TABLE IF NOT EXISTS chat.user" +
                    "(id INTEGER NOT NULL, username VARCHAR(50) NOT NULL, password VARCHAR(70) NOT NULL);");
            stmt.execute("CREATE TABLE IF NOT EXISTS chat.message" +
                    "(id INTEGER NOT NULL, text text, sender VARCHAR(50) NOT NULL, " +
                    "time timestamp DEFAULT CURRENT_TIMESTAMP);");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/

    private static void updateData(String fileName, ApplicationContext context) throws SQLException {
        DataSource dataSource = context.getBean("hikariDataSource", HikariDataSource.class);
        try (Connection con = dataSource.getConnection(); Statement stmt = con.createStatement()) {
            InputStreamReader inStream = new InputStreamReader(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream(fileName)));
            Scanner scanner = new Scanner(inStream).useDelimiter(";");

            while (scanner.hasNext()) {
                stmt.executeUpdate(scanner.next().trim());
            }
        }
    }
}
