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
     * @param subscriptionId the subscriptionId of the UserAccount
     * @return all the UserAccount for the given subscriptionId in the database
     */
    public List<UserAccount> listBySubscription(Long subscriptionId) {
        return jdbc.query("SELECT id, openid, firstname, lastname, email, subscription_id FROM user_account WHERE subscription_id = ?", mapper, subscriptionId);
    }

    /**
     * @param userAccount the UserAccount to insert in the database
     * @return the auto-generated id of the new UserAccount
     */
    public Long create(UserAccount userAccount) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(c -> {
            PreparedStatement ps = c.prepareStatement("INSERT INTO user_account(openid, firstname, lastname, email, subscription_id) VALUES (?, ?, ?, ?, ?)", new String[]{"id"});
            ps.setString(1, userAccount.openId);
            ps.setString(2, userAccount.firstname);
            ps.setString(3, userAccount.lastname);
            ps.setString(4, userAccount.email);
            ps.setLong(5, userAccount.subscriptionId);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    /**
     * @param openid the openid of the UserAccount
     * @return the Optional UserAccount corresponding to the given openid
     */
    public Optional<UserAccount> readByOpenid(String openid) {
        try {
            return Optional.of(jdbc.queryForObject("SELECT id, openid, firstname, lastname, email, subscription_id FROM user_account WHERE openid=?", mapper, openid));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * @param subscriptionId the subscription_id of the UserAccount we want to delete
     * @return false if no row was deleted, true otherwise
     */
    public boolean deleteBySubscriptionId(Long subscriptionId) {
        return jdbc.update("DELETE FROM user_account WHERE subscription_id = ?", subscriptionId) != 0;
    }

    private RowMapper<UserAccount> mapper =
            (rs, rowNum) -> new UserAccount.Builder()
                    .id(rs.getLong("id"))
                    .openId(rs.getString("openid"))
                    .name(rs.getString("firstname"), rs.getString("lastname"))
                    .email(rs.getString("email"))
                    .subscriptionId(rs.getLong("subscription_id")).build();

}