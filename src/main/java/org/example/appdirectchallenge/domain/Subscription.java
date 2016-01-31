package org.example.appdirectchallenge.domain;

import java.util.Collections;
import java.util.List;

public class Subscription {

    public Long id;
    public String companyName;
    public String edition;
    public String status;
    public List<User> users;


    public Subscription(Long id, String companyName, String edition, String status, List<User> users) {
        this.companyName = companyName;
        this.edition = edition;
        this.status = status;
        this.users = users;
        this.id = id;
    }

    public Subscription(String companyName, String edition, String status, List<User> users) {
        this(null, companyName, edition, status, users);
    }

    public Subscription(String companyName, String edition, String status) {
        this(null, companyName, edition, status, Collections.emptyList());
    }


    public Subscription(Long id, String companyName, String edition, String status) {
        this(id, companyName, edition, status, Collections.emptyList());
    }

}
