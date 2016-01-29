package org.example.appdirectchallenge.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class UserRepository {

    protected JdbcTemplate jdbc;

    @Autowired
    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<User> list() {
        return jdbc.query("SELECT id, uuid, email, firstName, lastName, userLanguage, openId, edition FROM app_user", userMapper);
    }

    public Long create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO app_user(uuid, email, firstName, lastName, userLanguage, openId, edition) VALUES (?, ?, ?, ?, ?, ?, ?)", new String[]{"id"});
                    ps.setString(1, user.uuid);
                    ps.setString(2, user.email);
                    ps.setString(3, user.firstName);
                    ps.setString(4, user.lastName);
                    ps.setString(5, user.language);
                    ps.setString(6, user.openId);
                    ps.setString(7, user.edition);
                    return ps;
                },
                keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void delete(Long id) {
        jdbc.update("DELETE FROM app_user WHERE id = ?", id);
    }

    private static final RowMapper<User> userMapper = (rs, rowNum) -> new User(rs.getLong("id"),
            rs.getString("uuid"),
            rs.getString("email"),
            rs.getString("firstName"),
            rs.getString("lastName"),
            rs.getString("userLanguage"),
            rs.getString("openId"),
            rs.getString("edition"));

}