package org.example.appdirectchallenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.apache.xpath.internal.operations.String;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketPlace {

    public String baseUrl;
    public String partner;

    @JsonCreator
    public MarketPlace(@JsonProperty("baseUrl") String baseUrl,
                       @JsonProperty("partner") String partner) {
        this.baseUrl = baseUrl;
        this.partner = partner;
    }
}