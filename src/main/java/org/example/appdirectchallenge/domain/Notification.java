package org.example.appdirectchallenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {

    public String type;
    public Marketplace marketplace;
    public User creator;
    public Payload payload;

    @JsonCreator
    public Notification(@JsonProperty("type") String type,
                        @JsonProperty("marketplace") Marketplace marketplace,
                        @JsonProperty("creator") User creator,
                        @JsonProperty("payload") Payload payload) {
        this.type = type;
        this.marketplace = marketplace;
        this.creator = creator;
        this.payload = payload;
    }

}