package org.example.appdirectchallenge.domain.appdirect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload {

    public AppDirectUser user;
    public Account account;
    public Company company;
    public Order order;

    @JsonCreator
    public Payload(@JsonProperty("user") AppDirectUser user,
                   @JsonProperty("account") Account account,
                   @JsonProperty("company") Company company,
                   @JsonProperty("order") Order order) {
        this.account = account;
        this.company = company;
        this.user = user;
        this.order = order;
    }

}