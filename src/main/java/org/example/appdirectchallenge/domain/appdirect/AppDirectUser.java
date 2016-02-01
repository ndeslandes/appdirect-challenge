package org.example.appdirectchallenge.domain.appdirect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppDirectUser {

    public String email;
    public String firstName;
    public String language;
    public String lastName;
    public String openId;
    public String uuid;

    @JsonCreator
    public AppDirectUser(@JsonProperty("uuid") String uuid,
                         @JsonProperty("openId") String openId,
                         @JsonProperty("email") String email,
                         @JsonProperty("firstName") String firstName,
                         @JsonProperty("lastName") String lastName,
                         @JsonProperty("language") String language) {
        this.uuid = uuid;
        this.email = email;
        this.firstName = firstName;
        this.language = language;
        this.lastName = lastName;
        this.openId = openId;
    }

}