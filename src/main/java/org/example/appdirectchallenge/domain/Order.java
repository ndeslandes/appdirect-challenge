package org.example.appdirectchallenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    public String editionCode;
    public String pricingDuration;

    @JsonCreator
    public Order(@JsonProperty("editionCode") String editionCode,
                 @JsonProperty("pricingDuration") String pricingDuration) {
        this.editionCode = editionCode;
        this.pricingDuration = pricingDuration;
    }

}