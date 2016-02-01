package org.example.appdirectchallenge.service;

import org.example.appdirectchallenge.domain.Subscription;
import org.example.appdirectchallenge.domain.SubscriptionRepository;
import org.example.appdirectchallenge.domain.UserAccount;
import org.example.appdirectchallenge.domain.UserAccountRepository;
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
    private UserAccountRepository userAccountRepository;

    @Before
    public void setUp() {
        subscriptionService = new SubscriptionService(subscriptionRepository, userAccountRepository);
    }

    @Test
    public void subscriptionService_list_withOneSubscriptionAndOneUser_returnOneSubscription() {
        UserAccount userAccount = new UserAccount.Builder().id(1L).openId("openID").name("Tony", "Stark").email("tony.stark@starkindustries.com").subscriptionId(1L).build();
        Subscription subscription = new Subscription.Builder().id(1L).companyName("S.H.I.E.L.D.").edition("FREE").status("ACTIVE").marketPlaceBaseUrl("https://example.org/").userAccounts(Collections.singletonList(userAccount)).build();

        when(subscriptionRepository.list()).thenReturn(Collections.singletonList(subscription));
        when(userAccountRepository.listBySubscription(1L)).thenReturn(Collections.singletonList(userAccount));

        assertThat(subscriptionService.list(), contains(subscription));
    }

}