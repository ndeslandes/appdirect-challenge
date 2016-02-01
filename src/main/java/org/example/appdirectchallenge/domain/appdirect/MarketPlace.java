package org.example.appdirectchallenge.domain.appdirect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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