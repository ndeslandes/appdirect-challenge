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

    /**
     * @return all the Subscription in the database
     */
    public List<Subscription> list() {
        return jdbc.query("SELECT id, company_name, edition, status, market_place_base_url FROM subscription", mapper);
    }

    /**
     * @param subscription the Subscription to insert in the database
     * @return the auto-generated id of the new subscription
     */
    public Long create(Subscription subscription) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(c -> {
            PreparedStatement ps = c.prepareStatement("INSERT INTO subscription(company_name, edition, status, subscription, market_place_base_url) VALUES (?, ?, ?, ?)", new String[]{"id"});
            ps.setString(1, subscription.companyName);
            ps.setString(2, subscription.edition);
            ps.setString(3, subscription.status);
            ps.setString(4, subscription.marketPlaceBaseUrl);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    /**
     * @param id the id of the Subscription
     * @return the Subscription corresponding to the given id
     */
    public Subscription read(Long id) {
        return jdbc.queryForObject("SELECT id, company_name, edition, status FROM subscription WHERE id=?", mapper, id);
    }

    /**
     * @param subscription the Subscription to update in the database. Only the edition AND the status will be update for the Subscription with the correct id.
     * @return false if no row was updated, true otherwise
     */
    public boolean update(Subscription subscription) {
        return jdbc.update("UPDATE subscription SET edition = ?, status = ? WHERE id = ?", subscription.edition, subscription.status, subscription.id) != 0;
    }

    /**
     * @param id the id of the Subscription we want to delete
     * @return false if no row was deleted, true otherwise
     */
    public boolean delete(Long id) {
        return jdbc.update("DELETE FROM subscription WHERE id = ?", id) != 0;
    }

    private RowMapper<Subscription> mapper =
            (rs, rowNum) -> new Subscription(
                    rs.getLong("id"),
                    rs.getString("company_name"),
                    rs.getString("edition"),
                    rs.getString("status"),
                    rs.getString("market_place_base_url"));

}