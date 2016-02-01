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
     * @return all the subscription in the database
     */
    public List<Subscription> list() {
        return jdbc.query("SELECT id, company_name, edition, status, market_place_base_url FROM subscription", mapper);
    }

    /**
     * @param subscription the subscription to insert in the database
     * @return the auto-generated id of the new subscription
     */
    public Long create(Subscription subscription) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(c -> {
            PreparedStatement ps = c.prepareStatement("INSERT INTO subscription(company_name, edition, status, market_place_base_url) VALUES (?, ?, ?, ?)", new String[]{"id"});
            ps.setString(1, subscription.companyName);
            ps.setString(2, subscription.edition);
            ps.setString(3, subscription.status);
            ps.setString(4, subscription.marketPlaceBaseUrl);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    /**
     * @param id the id of the subscription
     * @return the subscription corresponding to the given id
     */
    public Subscription read(Long id) {
        return jdbc.queryForObject("SELECT id, company_name, edition, status, market_place_base_url FROM subscription WHERE id=?", mapper, id);
    }

    /**
     *
     * @param id the id of the subscription to update
     * @param edition the new edition
     * @return false if no row was updated, true otherwise
     */
    public boolean updateEdition(Long id, String edition) {
        return jdbc.update("UPDATE subscription SET edition = ? WHERE id = ?", edition, id) != 0;
    }

    /**
     * @param id the id of the subscription to update
     * @param status the new status
     * @return false if no row was updated, true otherwise
     */
    public boolean updateStatus(Long id, String status) {
        return jdbc.update("UPDATE subscription SET status = ? WHERE id = ?", status, id) != 0;
    }

    /**
     * @param id the id of the subscription to delete
     * @return false if no row was deleted, true otherwise
     */
    public boolean delete(Long id) {
        return jdbc.update("DELETE FROM subscription WHERE id = ?", id) != 0;
    }

    private RowMapper<Subscription> mapper =
            (rs, rowNum) -> new Subscription.Builder()
                    .id(rs.getLong("id"))
                    .companyName(rs.getString("company_name"))
                    .edition(rs.getString("edition"))
                    .status(rs.getString("status"))
                    .marketPlaceBaseUrl(rs.getString("market_place_base_url")).build();

}