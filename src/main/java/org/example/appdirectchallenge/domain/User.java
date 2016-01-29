package org.example.appdirectchallenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    public Long id;
    public String email;
    public String firstName;
    public String language;
    public String lastName;
    public String openId;
    public String uuid;

    @JsonCreator
    public User(@JsonProperty("id") Long id,
                @JsonProperty("uuid") String uuid,
                @JsonProperty("email") String email,
                @JsonProperty("firstName") String firstName,
                @JsonProperty("lastName") String lastName,
                @JsonProperty("language") String language,
                @JsonProperty("openId") String openId) {
        this.id = id;
        this.uuid = uuid;
        this.email = email;
        this.firstName = firstName;
        this.language = language;
        this.lastName = lastName;
        this.openId = openId;
    }

}