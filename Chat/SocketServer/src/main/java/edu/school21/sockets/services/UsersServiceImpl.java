package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(String username, String password) {
        Long id = usersRepository.generateId();
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setId(id);
        usersRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> user = usersRepository.findByUsername(username);
        return user.orElse(null);
    }

    @Override
    public boolean signIn(String username, String password) {
        Optional<User> user = usersRepository.findByUsername(username);
        if (user.isPresent()) {
            String hashedPassword = passwordEncoder.encode(password);
            return user.get().getPassword().equals(hashedPassword);
        } else {
            return false;
        }
    }
}
