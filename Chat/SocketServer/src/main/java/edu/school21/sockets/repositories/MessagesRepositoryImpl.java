package edu.school21.sockets.repositories;



import edu.school21.sockets.models.Message;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class MessagesRepositoryImpl implements MessageRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MessagesRepositoryImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Message> findAll() {
        String sql = "SELECT * FROM chat.message;";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Message.class));
    }

    @Override
    public void update(Message message) {
        String sql = "UPDATE chat.message SET text = :text, sender = :sender, roomId = :roomId, time = :time WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", message.getId())
                .addValue("text", message.getText())
                .addValue("sender", message.getSender())
                .addValue("roomId", message.getRoomId())
                .addValue("time", message.getTime());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<Message> findById(Long id) {
        String sql = "SELECT * FROM chat.message WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        Message message = namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Message.class));
        return Optional.ofNullable(message);
    }

    @Override
    public List<Message> findByRoomId(Long roomId) {
        String sql = "SELECT * FROM chat.message WHERE roomId = :roomId";
        SqlParameterSource params = new MapSqlParameterSource("roomId", roomId);
        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Message.class));
    }

    @Override
    public void save(Message message) {
        String sql = "INSERT INTO chat.message (id, text, sender, roomId, time) VALUES (:id, :text, :sender, :roomId, :time)";
        SqlParameterSource params = new MapSqlParameterSource("id", message.getId())
                .addValue("text", message.getText())
                .addValue("sender", message.getSender())
                .addValue("roomId", message.getRoomId())
                .addValue("time", message.getTime());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Long generateId() {
        String sqlQuery = "SELECT MAX(id) FROM chat.message;";
        Integer maxId = namedParameterJdbcTemplate.queryForObject(sqlQuery, new MapSqlParameterSource(), Integer.class);
        Long id = (maxId == null) ? 1L : Integer.toUnsignedLong(maxId) + 1;
        return id;
    }
}

