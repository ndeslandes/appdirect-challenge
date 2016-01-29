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
public class SubscriptionRepository {

    protected JdbcTemplate jdbc;

    @Autowired
    public SubscriptionRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Subscription> list() {
        return jdbc.query("SELECT account_id, creator_first_name, creator_last_name, edition, status FROM subscription", subscriptionMapper);
    }

    public Long create(Subscription subscription) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO subscription(creator_first_name, creator_last_name, edition, status) VALUES (?, ?, ?, ?)", new String[]{"account_id"});
                    ps.setString(1, subscription.creatorFirstName);
                    ps.setString(2, subscription.creatorLastName);
                    ps.setString(3, subscription.edition);
                    ps.setString(4, subscription.status);
                    return ps;
                },
                keyHolder);
        return keyHolder.getKey().longValue();
    }

    public boolean update(Subscription subscription) {
        return jdbc.update("UPDATE subscription SET edition = ?, status = ? WHERE account_id = ?", subscription.edition, subscription.status, subscription.accountId) != 0;
    }

    public boolean delete(Long accountId) {
        return jdbc.update("DELETE FROM subscription WHERE account_id = ?", accountId) != 0;
    }

    private static final RowMapper<Subscription> subscriptionMapper = (rs, rowNum) -> new Subscription(rs.getLong("account_id"),
            rs.getString("creator_first_name"),
            rs.getString("creator_last_name"),
            rs.getString("edition"),
            rs.getString("status"));

}