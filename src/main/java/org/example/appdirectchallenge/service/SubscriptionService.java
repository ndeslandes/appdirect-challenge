package org.example.appdirectchallenge.service;

import org.example.appdirectchallenge.domain.Subscription;
import org.example.appdirectchallenge.domain.SubscriptionRepository;
import org.example.appdirectchallenge.domain.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserAccountRepository userAccountRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @RequestMapping("subscriptions")
    public List<Subscription> list() {
        final List<Subscription> subscriptions = subscriptionRepository.list();
        return subscriptions.stream().map(s -> new Subscription.Builder(s)
                .users(userAccountRepository.listBySubscription(s.id))
                .build()).collect(Collectors.toList());
    }

}