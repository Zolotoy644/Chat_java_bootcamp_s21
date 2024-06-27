package edu.school21.sockets.repositories;

import edu.school21.sockets.models.ChatRoom;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class ChatRoomRepositoryImpl implements ChatRoomRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ChatRoomRepositoryImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<ChatRoom> findById(Long id) {
        String sql = "SELECT * FROM chat.chatroom WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        ChatRoom chatRoom = namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(ChatRoom.class));
        return Optional.ofNullable(chatRoom);
    }


    @Override
    public void save(ChatRoom chatRoom) {
        String sql = "INSERT INTO chat.chatroom (id, name) VALUES (:id, :name)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", chatRoom.getId())
                .addValue("name", chatRoom.getName());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public List<ChatRoom> findAll() {
        String sql = "SELECT * FROM chat.chatroom;";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ChatRoom.class));
    }

    @Override
    public void update(ChatRoom chatRoom) {
        String sql = "UPDATE chat.chatroom SET name = :name WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", chatRoom.getId())
                .addValue("name", chatRoom.getName());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Long generateId() {
        String sqlQuery = "SELECT MAX(id) FROM chat.chatroom;";
        Integer maxId = namedParameterJdbcTemplate.queryForObject(sqlQuery, new MapSqlParameterSource(), Integer.class);
        Long id = (maxId == null) ? 1L : Integer.toUnsignedLong(maxId) + 1;
        return id;
    }
}
