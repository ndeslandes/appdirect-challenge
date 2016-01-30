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
        return jdbc.query("SELECT id, company_name, edition, status FROM subscription", subscriptionMapper);
    }

    public Long create(Subscription subscription) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO subscription(company_name, edition, status) VALUES (?, ?, ?)", new String[]{"id"});
                    ps.setString(1, subscription.companyName);
                    ps.setString(2, subscription.edition);
                    ps.setString(3, subscription.status);
                    return ps;
                },
                keyHolder);
        return keyHolder.getKey().longValue();
    }

    public boolean update(Subscription subscription) {
        return jdbc.update("UPDATE subscription SET edition = ?, status = ? WHERE id = ?", subscription.edition, subscription.status, subscription.id) != 0;
    }

    public boolean delete(Long id) {
        return jdbc.update("DELETE FROM subscription WHERE id = ?", id) != 0;
    }

    private static final RowMapper<Subscription> subscriptionMapper = (rs, rowNum) -> new Subscription(
            rs.getLong("id"),
            rs.getString("company_name"),
            rs.getString("edition"),
            rs.getString("status"));

}