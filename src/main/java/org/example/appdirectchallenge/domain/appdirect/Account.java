package org.example.appdirectchallenge.domain.appdirect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

    public String accountIdentifier;
    public String status;

    @JsonCreator
    public Account(@JsonProperty("accountIdentifier") String accountIdentifier,
                   @JsonProperty("status") String status) {
        this.accountIdentifier = accountIdentifier;
        this.status = status;
    }

}