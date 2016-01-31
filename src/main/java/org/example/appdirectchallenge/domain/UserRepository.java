package org.example.appdirectchallenge.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    protected JdbcTemplate jdbc;

    @Autowired
    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * @return all the User in the database
     */
    public List<User> list() {
        return jdbc.query("SELECT id, openid, firstname, lastname, email, subscription_id FROM user_account", mapper);
    }

    /**
     * @param subscriptionId the subscriptionId of the User
     * @return all the User in the database
     */
    public List<User> list(Long subscriptionId) {
        return jdbc.query("SELECT id, openid, firstname, lastname, email, subscription_id FROM user_account WHERE subscription_id = ?", mapper, subscriptionId);
    }

    /**
     * @param user the User to insert in the database
     * @return the auto-generated id of the new User
     */
    public Long create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(c -> {
            PreparedStatement ps = c.prepareStatement("INSERT INTO user_account(openid, firstname, lastname, email, subscription_id) VALUES (?, ?, ?, ?, ?)", new String[]{"id"});
            ps.setString(1, user.openId);
            ps.setString(2, user.firstname);
            ps.setString(3, user.lastname);
            ps.setString(4, user.email);
            ps.setLong(5, user.subscriptionId);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    /**
     * @param email the email of the User
     * @return the Optional User corresponding to the given email
     */
    public Optional<User> readByEmail(String email) {
        try {
            return Optional.of(jdbc.queryForObject("SELECT id, openid, firstname, lastname, email, subscription_id FROM user_account WHERE email=?", mapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * @param subscriptionId the subscription_id of the User we want to delete
     * @return false if no row was deleted, true otherwise
     */
    public boolean deleteBySubscriptionId(Long subscriptionId) {
        return jdbc.update("DELETE FROM user_account WHERE subscription_id = ?", subscriptionId) != 0;
    }

    private RowMapper<User> mapper =
            (rs, rowNum) -> new User(
                    rs.getLong("id"),
                    rs.getString("openid"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("email"),
                    rs.getLong("subscription_id"));

}