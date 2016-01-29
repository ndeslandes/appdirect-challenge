package org.example.appdirectchallenge.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {

    protected JdbcTemplate jdbc;

    @Autowired
    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public User getUser(long id) {
        return jdbc.queryForObject("SELECT * FROM sb_user WHERE id=?", userMapper, id);
    }

    public List<User> getUsers() {
        return jdbc.query("SELECT * FROM sb_user", userMapper);
    }

    private static final RowMapper<User> userMapper = new RowMapper<User>() {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getLong("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"), rs.getString("version"));
        }
    };

}