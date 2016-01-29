package org.example.appdirectchallenge.service;

import org.example.appdirectchallenge.domain.Subscription;
import org.example.appdirectchallenge.domain.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class SubscriptionService {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private SubscriptionRepository subscriptions;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptions) {
        this.subscriptions = subscriptions;
    }

    @RequestMapping("subscriptions")
    public List<Subscription> list() {
        return subscriptions.list();
    }
}