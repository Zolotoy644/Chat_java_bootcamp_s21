package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class UsersRepositoryImpl implements UsersRepository{
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UsersRepositoryImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public void save(User user) {
        String sql = "INSERT INTO chat.user (id, username, password) VALUES (:id, :username, :password);";
        MapSqlParameterSource params = new MapSqlParameterSource("id", user.getId());
        params.addValue("username", user.getUsername());
        params.addValue("password", user.getPassword());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM chat.user;";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM chat.user WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM chat.user WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id);

        User user = namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(User.class));
        return Optional.ofNullable(user);
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE chat.user SET username = :username, password = :password WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", user.getId())
                .addValue("username", user.getUsername())
                .addValue("password", user.getPassword());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM chat.user WHERE username = :username";
        SqlParameterSource params = new MapSqlParameterSource("username", username);
        User user = namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(User.class));
        return Optional.ofNullable(user);
    }

    @Override
    public Long generateId() {
        String sqlQuery = "SELECT MAX(id) FROM chat.user;";
        Integer maxId = namedParameterJdbcTemplate.queryForObject(sqlQuery, new MapSqlParameterSource(), Integer.class);
        Long id = (maxId == null) ? 1L : Integer.toUnsignedLong(maxId) + 1;
        return id;
    }
}
