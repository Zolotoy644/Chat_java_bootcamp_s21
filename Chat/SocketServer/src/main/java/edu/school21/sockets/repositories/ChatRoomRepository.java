package edu.school21.sockets.repositories;

import edu.school21.sockets.models.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository {
    Optional<ChatRoom> findById(Long id);
    void save(ChatRoom chatRoom);

    List<ChatRoom> findAll();

    void update(ChatRoom chatRoom);

    Long generateId();
}
