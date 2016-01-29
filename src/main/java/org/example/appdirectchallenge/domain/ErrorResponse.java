package org.example.appdirectchallenge.domain;

public class ErrorResponse extends Response {

    public String errorCode;
    public String message;

    public ErrorResponse(String errorCode, String message) {
        this.success = "false";
        this.errorCode = errorCode;
        this.message = message;
    }

}
