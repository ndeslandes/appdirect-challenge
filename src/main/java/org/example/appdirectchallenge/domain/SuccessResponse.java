package org.example.appdirectchallenge.domain;

public class SuccessResponse extends Response {

    public String accountIdentifier;

    public SuccessResponse() {
        this.success = "true";
    }
    public SuccessResponse(String accountIdentifier) {
        this.success = "true";
        this.accountIdentifier = accountIdentifier;
    }
}