package org.example.appdirectchallenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    public Address(String firstName, String lastName, String fullName, String street1, String zip, String city, String state, String country) {
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