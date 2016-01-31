package org.example.appdirectchallenge.service;

import org.example.appdirectchallenge.domain.Subscription;
import org.example.appdirectchallenge.domain.SubscriptionRepository;
import org.example.appdirectchallenge.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class SubscriptionService {

    private SubscriptionRepository subscriptionRepository;

    private UserRepository userRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping("subscriptions")
    public List<Subscription> list() {
        List<Subscription> subscriptions = subscriptionRepository.list();
        return subscriptions.stream().map(subscription -> {
            subscription.users = userRepository.list(subscription.id);
            return subscription;
        }).collect(Collectors.toList());
    }
}