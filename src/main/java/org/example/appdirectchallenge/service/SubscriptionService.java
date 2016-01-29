package org.example.appdirectchallenge.service;


import org.example.appdirectchallenge.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("api/subscription")
public class SubscriptionService {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private UserRepository users;

    @Autowired
    public SubscriptionService(UserRepository users) {
        this.users = users;
    }

    @RequestMapping("notification/create")
    public void subscriptionCreated(@RequestParam("url") String url) {
        log.info("subscriptionCreated url=" + url);
        RestTemplate restTemplate = new RestTemplate();
        Notification notification = restTemplate.getForObject(url, Notification.class);
        users.create(notification.creator);
    }
}