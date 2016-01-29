package org.example.appdirectchallenge.domain;

public class ErrorResponse extends Response {

    public ErrorCode errorCode;
    public String message;

    public ErrorResponse(ErrorCode errorCode, String message) {
        this.success = "false";
        this.errorCode = errorCode;
        this.message = message;
    }

    public enum ErrorCode {
        USER_ALREADY_EXISTS, USER_NOT_FOUND, ACCOUNT_NOT_FOUND, MAX_USERS_REACHED, UNAUTHORIZED, OPERATION_CANCELED, CONFIGURATION_ERROR, INVALID_RESPONSE, PENDING, FORBIDDEN, BINDING_NOT_FOUND, TRANSPORT_ERROR, UNKNOWN_ERROR
    }
}
