package org.example.appdirectchallenge.service;

import org.example.appdirectchallenge.domain.Subscription;
import org.example.appdirectchallenge.domain.SubscriptionRepository;
import org.example.appdirectchallenge.domain.User;
import org.example.appdirectchallenge.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionServiceTest {

    private SubscriptionService subscriptionService;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        subscriptionService = new SubscriptionService(subscriptionRepository, userRepository);
    }

    @Test
    public void subscriptionService_list_withOneSubscriptionAndOneUser_returnOneSubscription() {
        User user = new User(1L, "openID", "Tony", "Stark", "tony.stark@starkindustries.com", 1L);
        Subscription subscription = new Subscription(1L, "S.H.I.E.L.D.", "FREE", "ACTIVE", "https://example.org/", Collections.singletonList(user));

        when(subscriptionRepository.list()).thenReturn(Collections.singletonList(subscription));
        when(userRepository.list(1L)).thenReturn(Collections.singletonList(user));

        assertThat(subscriptionService.list(), contains(subscription));
    }

}