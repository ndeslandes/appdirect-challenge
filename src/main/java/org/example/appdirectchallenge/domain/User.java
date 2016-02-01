package org.example.appdirectchallenge.domain;

public class User {

    public Long id;
    public String openId;
    public String firstname;
    public String lastname;
    public String email;
    public Subscription subscription;

    public User(Long id, String openId, String firstname, String lastname, String email, Subscription subscription) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.openId = openId;
        this.email = email;
        this.subscription = subscription;
    }

    public User(String openId, String firstname, String lastname, String email, Subscription subscription) {
        this(null, openId, firstname, lastname, email, subscription);
    }

    public static String extractOpenId(String openIdUrl) {
        return openIdUrl.replaceFirst(".*/([^/?]+).*", "$1");
    }
}
