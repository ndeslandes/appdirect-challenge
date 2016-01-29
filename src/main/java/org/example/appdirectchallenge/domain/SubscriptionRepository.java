package org.example.appdirectchallenge.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SubscriptionRepository {

    protected JdbcTemplate jdbc;

    @Autowired
    public SubscriptionRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

}