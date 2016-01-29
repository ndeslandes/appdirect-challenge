package org.example.appdirectchallenge.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {

    protected JdbcTemplate jdbc;

    @Autowired
    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<User> list() {
        return jdbc.query("SELECT app_user.uuid, app_user.email, app_user.firstName, app_user.lastName, app_user.userLanguage, app_user.openId, " +
                "app_address.firstName AS addr_firstName, app_address.lastName AS addr_lastName, app_address.fullName, app_address.street1, app_address.zip, app_address.city, app_address.state, app_address.country " +
                "FROM app_user, app_address " +
                "WHERE app_user.idAddress = app_address.id", userMapper);
    }

    public void create(User user) {
        Address address = user.address;

        KeyHolder keyHolder = null;
        if (address != null) {
            keyHolder = new GeneratedKeyHolder();
            jdbc.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement("INSERT INTO app_address(firstName, lastName, fullName, street1, zip, city, state, country) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", new String[]{"id"});
                        ps.setString(1, address.firstName);
                        ps.setString(1, address.lastName);
                        ps.setString(1, address.fullName);
                        ps.setString(1, address.street1);
                        ps.setString(1, address.zip);
                        ps.setString(1, address.city);
                        ps.setString(1, address.state);
                        ps.setString(1, address.country);
                        return ps;
                    },
                    keyHolder);
        }

        jdbc.update("INSERT INTO app_user(uuid, email, firstName, lastName, userLanguage, openId, idAddress) VALUES (?, ?, ?, ?, ?, ?, ?);", user.uuid, user.email, user.firstName, user.lastName, user.language, user.openId, keyHolder != null?keyHolder.getKey():null);
    }

    private static final RowMapper<User> userMapper = (rs, rowNum) -> new User(rs.getString("uuid"),
            rs.getString("email"),
            rs.getString("firstName"),
            rs.getString("lastName"),
            rs.getString("userLanguage"),
            rs.getString("openId"),
            new Address(rs.getString("addr_firstName"),
                    rs.getString("addr_lastName"),
                    rs.getString("fullName"),
                    rs.getString("street1"),
                    rs.getString("zip"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("country")));

}