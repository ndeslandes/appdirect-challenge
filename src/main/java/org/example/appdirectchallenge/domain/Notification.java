package org.example.appdirectchallenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {

    public String type;
    public MarketPlace marketPlace;
    public User creator;
    public Payload payload;

    @JsonCreator
    public Notification(@JsonProperty("type") String type,
                        @JsonProperty("marketPlace") MarketPlace marketPlace,
                        @JsonProperty("creator") User creator,
                        @JsonProperty("payload") Payload payload) {
        this.type = type;
        this.marketPlace = marketPlace;
        this.creator = creator;
        this.payload = payload;
    }

}