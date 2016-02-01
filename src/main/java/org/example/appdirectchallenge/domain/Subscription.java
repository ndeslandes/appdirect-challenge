package org.example.appdirectchallenge.domain;

import java.util.Collections;
import java.util.List;

public class Subscription {

    public Long id;
    public String companyName;
    public String edition;
    public String status;
    public String marketPlaceBaseUrl;
    public List<User> users;


    public Subscription(Long id) {
        this.id = id;
    }

    public Subscription(Long id, String companyName, String edition, String status, String marketPlaceBaseUrl, List<User> users) {
        this.companyName = companyName;
        this.edition = edition;
        this.status = status;
        this.users = users;
        this.marketPlaceBaseUrl = marketPlaceBaseUrl;
        this.id = id;
    }

    public Subscription(String companyName, String edition, String status, String marketPlaceBaseUrl, List<User> users) {
        this(null, companyName, edition, status, marketPlaceBaseUrl, users);
    }

    public Subscription(String companyName, String edition, String status, String marketPlaceBaseUrl) {
        this(null, companyName, edition, status, marketPlaceBaseUrl, Collections.emptyList());
    }


    public Subscription(Long id, String companyName, String edition, String status, String marketPlaceBaseUrl) {
        this(id, companyName, edition, status, marketPlaceBaseUrl, Collections.emptyList());
    }

}
