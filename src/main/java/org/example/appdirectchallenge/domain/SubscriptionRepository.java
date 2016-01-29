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
        return jdbc.query("SELECT accountId, creatorFirstName, creatorLastName, edition FROM subscription", subscriptionMapper);
    }

    public Long create(Subscription subscription) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO subscription(creatorFirstName, creatorLastName, edition) VALUES (?, ?, ?)", new String[]{"accountId"});
                    ps.setString(1, subscription.creatorFirstName);
                    ps.setString(2, subscription.creatorLastName);
                    ps.setString(3, subscription.edition);
                    return ps;
                },
                keyHolder);
        return keyHolder.getKey().longValue();
    }

    public boolean delete(Long accountId) {
        return jdbc.update("DELETE FROM subscription WHERE accountId = ?", accountId) != 0;
    }

    private static final RowMapper<Subscription> subscriptionMapper = (rs, rowNum) -> new Subscription(rs.getLong("accountId"),
            rs.getString("creatorFirstName"),
            rs.getString("creatorLastName"),
            rs.getString("edition"));

}