package org.example.appdirectchallenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    public Address address;
    public String email;
    public String firstName;
    public String language;
    public String lastName;
    public String openId;
    public String uuid;

    @JsonCreator
    public User(String uuid, String email, String firstName, String lastName, String language, String openId, Address address) {
        this.uuid = uuid;
        this.email = email;
        this.firstName = firstName;
        this.language = language;
        this.lastName = lastName;
        this.openId = openId;
        this.address = address;
    }

}