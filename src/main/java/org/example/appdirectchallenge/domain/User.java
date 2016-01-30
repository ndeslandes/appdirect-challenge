package org.example.appdirectchallenge.domain;

public class User {

    public Long id;
    public String openId;
    public String firstname;
    public String lastname;
    public String email;
    public Long subscriptionId;

    public User(String email) {
        this.email = email;
    }

    public User(String openId, String firstname, String lastname, String email, Long subscriptionId) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.openId = openId;
        this.email = email;
        this.subscriptionId = subscriptionId;
    }

    public User(Long id, String openId, String firstname, String lastname, String email, Long subscriptionId) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.openId = openId;
        this.email = email;
        this.subscriptionId = subscriptionId;
    }
}
