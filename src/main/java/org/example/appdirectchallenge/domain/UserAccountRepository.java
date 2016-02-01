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
public class UserAccountRepository {

    protected JdbcTemplate jdbc;

    @Autowired
    public UserAccountRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * @param subscriptionId the subscription_id of the user_account
     * @return all the user_account for the given subscription_id in the database
     */
    public List<UserAccount> listBySubscription(Long subscriptionId) {
        return jdbc.query("SELECT id, openid, firstname, lastname, email, subscription_id FROM user_account WHERE subscription_id = ?", mapper, subscriptionId);
    }

    /**
     * @param user the user_account to insert in the database
     * @return the auto-generated id of the new user_account
     */
    public Long create(UserAccount user) {
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
     * @param openId the openid of the user_account
     * @return the optional user_account corresponding to the given openid
     */
    public Optional<UserAccount> readByOpenid(String openId) {
        try {
            return Optional.of(jdbc.queryForObject("SELECT id, openid, firstname, lastname, email, subscription_id FROM user_account WHERE openid=?", mapper, openId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * @param subscriptionId the subscription_id of the user_account we want to delete
     * @return false if no row was deleted, true otherwise
     */
    public boolean deleteBySubscriptionId(Long subscriptionId) {
        return jdbc.update("DELETE FROM user_account WHERE subscription_id = ?", subscriptionId) != 0;
    }

    /**
     * @param openId the openid of the user_account we want to delete
     * @return false if no row was deleted, true otherwise
     */
    public boolean deleteByOpenId(String openId) {
        return jdbc.update("DELETE FROM user_account WHERE openid = ?", openId) != 0;
    }

    private RowMapper<UserAccount> mapper =
            (rs, rowNum) -> new UserAccount.Builder()
                    .id(rs.getLong("id"))
                    .openId(rs.getString("openid"))
                    .name(rs.getString("firstname"), rs.getString("lastname"))
                    .email(rs.getString("email"))
                    .subscriptionId(rs.getLong("subscription_id")).build();

}