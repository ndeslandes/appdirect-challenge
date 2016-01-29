package org.example.appdirectchallenge.domain;

public class Response {

    public String success;
    public String accountIdentifier;

    public Response(String success, String accountIdentifier) {
        this.success = success;
        this.accountIdentifier = accountIdentifier;
    }
}