package org.example.appdirectchallenge.service.appdirect;

import org.example.appdirectchallenge.domain.appdirect.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.stereotype.Component;

@Component
public class AppDirectOAuthClient {

    @Autowired
    private ProtectedResourceDetails resource;

    public Notification getNotification(String url, Notification.Type type) {
        OAuthRestTemplate rest = new OAuthRestTemplate(resource);
        Notification notification = rest.getForObject(url, Notification.class);
        if (!type.equals(notification.type)) {
            throw new BadNotificationType(type, notification.type);
        }
        return notification;
    }

}
