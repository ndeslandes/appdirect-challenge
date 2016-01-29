package org.example.appdirectchallenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    public String city;
    public String country;
    public String state;
    public String street1;
    public String street2;
    public String zip;

    @JsonCreator
    public Address(@JsonProperty("city") String city,
                   @JsonProperty("country") String country,
                   @JsonProperty("state") String state,
                   @JsonProperty("street1") String street1,
                   @JsonProperty("street2") String street2,
                   @JsonProperty("zip") String zip) {
        this.city = city;
        this.country = country;
        this.state = state;
        this.street1 = street1;
        this.street2 = street2;
        this.zip = zip;
    }

}