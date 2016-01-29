package org.example.appdirectchallenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    public String email;
    public String firstName;
    public String language;
    public String lastName;
    public String openId;
    public String uuid;
    public Address address;

    @JsonCreator
    public User(@JsonProperty("uuid") String uuid,
                @JsonProperty("openId") String openId,
                @JsonProperty("email") String email,
                @JsonProperty("firstName") String firstName,
                @JsonProperty("lastName") String lastName,
                @JsonProperty("language") String language,
                @JsonProperty("address") Address address) {
        this.uuid = uuid;
        this.email = email;
        this.firstName = firstName;
        this.language = language;
        this.lastName = lastName;
        this.openId = openId;
        this.address = address;
    }

}