package org.example.appdirectchallenge.service.appdirect;

import org.example.appdirectchallenge.domain.appdirect.Notification;

public class BadNotificationType extends RuntimeException {

    public BadNotificationType(Notification.Type expected, Notification.Type actual) {
        super(String.format("Bad Notification type. Expected %s, actual %s", expected, actual));
    }
}
