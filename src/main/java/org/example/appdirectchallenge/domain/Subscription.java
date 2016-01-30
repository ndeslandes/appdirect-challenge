package org.example.appdirectchallenge.domain;

public class Subscription {

    public Long id;
    public String companyName;
    public String edition;
    public String status;

    public Subscription(String companyName, String edition, String status) {
        this.companyName = companyName;
        this.edition = edition;
        this.status = status;
    }

    public Subscription(Long id, String companyName, String edition, String status) {
        this.id = id;
        this.companyName = companyName;
        this.edition = edition;
        this.status = status;
    }

}
