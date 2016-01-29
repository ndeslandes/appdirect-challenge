package org.example.appdirectchallenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    public String city;
    public String country;
    public String firstName;
    public String fullName;
    public String lastName;
    public String state;
    public String street1;
    public String zip;

    @JsonCreator
    public Address(@JsonProperty("firstName") String firstName,
                   @JsonProperty("lastName") String lastName,
                   @JsonProperty("fullName") String fullName,
                   @JsonProperty("street1") String street1,
                   @JsonProperty("zip") String zip,
                   @JsonProperty("city") String city,
                   @JsonProperty("state") String state,
                   @JsonProperty("country") String country) {
        this.city = city;
        this.country = country;
        this.firstName = firstName;
        this.fullName = fullName;
        this.lastName = lastName;
        this.state = state;
        this.street1 = street1;
        this.zip = zip;
    }

}