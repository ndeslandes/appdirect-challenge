package org.example.appdirectchallenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    public String editionCode;
    public String pricingDuration;
    public Item item;

    @JsonCreator
    public Order(@JsonProperty("editionCode") String editionCode,
                 @JsonProperty("pricingDuration") String pricingDuration,
                 @JsonProperty("item") Item item) {
        this.editionCode = editionCode;
        this.pricingDuration = pricingDuration;
        this.item = item;
    }

}