package org.example.appdirectchallenge.domain;

public class Subscription {

    public Long accountId;
    public String creatorFirstName;
    public String creatorLastName;
    public String edition;

    public Subscription(String creatorFirstName, String creatorLastName, String edition) {
        this.creatorFirstName = creatorFirstName;
        this.creatorLastName = creatorLastName;
        this.edition = edition;
    }

    public Subscription(Long accountId, String creatorFirstName, String creatorLastName, String edition) {
        this.accountId = accountId;
        this.creatorFirstName = creatorFirstName;
        this.creatorLastName = creatorLastName;
        this.edition = edition;
    }

}
