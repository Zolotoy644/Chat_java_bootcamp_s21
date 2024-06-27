package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository {
    Optional<Message> findById(Long id);

    List<Message> findByRoomId(Long roomId);

    void save(Message message);

    List<Message> findAll();

    void update(Message message);

    Long generateId();
}
