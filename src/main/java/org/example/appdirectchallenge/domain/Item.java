package org.example.appdirectchallenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    public String quantity;
    public String unit;

    @JsonCreator
    public Item(@JsonProperty("quantity") String quantity,
                @JsonProperty("unit") String unit) {
        this.quantity = quantity;
        this.unit = unit;
    }

}