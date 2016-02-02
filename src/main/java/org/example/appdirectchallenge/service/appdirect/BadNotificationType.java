package org.example.appdirectchallenge.service.appdirect;

import org.example.appdirectchallenge.domain.appdirect.Notification.Type;

public class BadNotificationType extends RuntimeException {

    public BadNotificationType(Type expected, Type actual) {
        super(String.format("Bad Notification type. Expected %s, actual %s", expected, actual));
    }

}