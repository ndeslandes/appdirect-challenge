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
        return jdbc.query("SELECT id, openid, firstname, lastname, email, subscription_id FROM user_account", userMapper);
    }

    public Long create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO user_account(openid, firstname, lastname, email, subscription_id) VALUES (?, ?, ?, ?, ?)", new String[]{"id"});
                    ps.setString(1, user.openId);
                    ps.setString(2, user.firstname);
                    ps.setString(3, user.lastname);
                    ps.setString(4, user.email);
                    ps.setLong(5, user.subscriptionId);
                    return ps;
                },
                keyHolder);
        return keyHolder.getKey().longValue();
    }

    public User read(String openid) {
        return jdbc.queryForObject("SELECT id, openid, firstname, lastname, email, subscription_id FROM user_account WHERE openid=?", userMapper, openid);
    }

    public boolean delete(Long id) {
        return jdbc.update("DELETE FROM user_account WHERE id = ?", id) != 0;
    }

    private static final RowMapper<User> userMapper = (rs, rowNum) -> new User(
            rs.getLong("id"),
            rs.getString("openid"),
            rs.getString("firstname"),
            rs.getString("lastname"),
            rs.getString("email"),
            rs.getLong("account_id"));

}