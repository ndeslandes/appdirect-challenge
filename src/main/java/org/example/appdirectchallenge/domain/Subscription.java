package org.example.appdirectchallenge.domain;

public class Subscription {

    public Long accountId;
    public String creatorFirstName;
    public String creatorLastName;
    public String edition;
    public String status;

    public Subscription(String creatorFirstName, String creatorLastName, String edition, String status) {
        this.creatorFirstName = creatorFirstName;
        this.creatorLastName = creatorLastName;
        this.edition = edition;
        this.status = status;
    }

    public Subscription(Long accountId, String creatorFirstName, String creatorLastName, String edition, String status) {
        this.accountId = accountId;
        this.creatorFirstName = creatorFirstName;
        this.creatorLastName = creatorLastName;
        this.edition = edition;
        this.status = status;
    }

}
