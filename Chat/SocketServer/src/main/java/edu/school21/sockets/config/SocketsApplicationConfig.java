package edu.school21.sockets.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.sockets.repositories.*;
import edu.school21.sockets.services.UsersService;
import edu.school21.sockets.services.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@PropertySources({@PropertySource("classpath:db.properties")})
public class SocketsApplicationConfig {
    @Autowired
    private Environment environment;

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(environment.getRequiredProperty("db.driver.name"));
        hikariConfig.setJdbcUrl(environment.getRequiredProperty("db.url"));
        hikariConfig.setUsername(environment.getRequiredProperty("db.user"));
        hikariConfig.setPassword(environment.getRequiredProperty("db.password"));
        return hikariConfig;
    }

    @Bean
    @Autowired
    public HikariDataSource hikariDataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    @Autowired
    public UsersRepositoryImpl usersRepositoryJdbc(HikariDataSource dataSource) {
        return new UsersRepositoryImpl(dataSource);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Autowired
    @Qualifier("usersRepositoryJdbc")
    public UsersService usersService(UsersRepository usersRepository) {
        return new UsersServiceImpl(usersRepository, encoder());
    }

    @Bean
    public MessageRepository messageRepository() {
        return new MessagesRepositoryImpl(hikariDataSource(hikariConfig()));
    }

    @Bean
    public ChatRoomRepository chatRoomRepository() {
        return new ChatRoomRepositoryImpl(hikariDataSource(hikariConfig()));
    }

/*
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
*/


}
