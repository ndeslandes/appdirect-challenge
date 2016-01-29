package org.example.appdirectchallenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload {

    public User user;
    public Account account;
    public Order order;

    @JsonCreator
    public Payload(@JsonProperty("account") Account account,
                   @JsonProperty("user") User user,
                   @JsonProperty("order") Order order) {
        this.account = account;
        this.user = user;
        this.order = order;
    }

}