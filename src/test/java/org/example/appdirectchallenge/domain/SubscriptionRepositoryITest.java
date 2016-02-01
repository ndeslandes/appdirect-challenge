package org.example.appdirectchallenge.domain;

import org.example.appdirectchallenge.Application;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class SubscriptionRepositoryITest {

    @Autowired
    public SubscriptionRepository subscriptionRepository;

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Test
    public void subscriptionRepository_create() {
        Subscription subscription = new Subscription.Builder().companyName("Company Name").edition("FREE").marketPlaceBaseUrl("http://example.org").status("ACTIVE").build();
        subscriptionRepository.create(subscription);
    }

    @Test
    public void subscriptionRepository_list() {
        Subscription subscription1 = new Subscription.Builder().companyName("Company Name").edition("FREE").marketPlaceBaseUrl("http://example.org").status("ACTIVE").build();
        Subscription subscription2 = new Subscription.Builder().companyName("Company Name").edition("FREE").marketPlaceBaseUrl("http://example.org").status("ACTIVE").build();
        subscriptionRepository.create(subscription1);
        subscriptionRepository.create(subscription2);
        assertThat(subscriptionRepository.list(), hasSize(2));
    }

    @Test
    public void subscriptionRepository_read() {
        Subscription subscription = new Subscription.Builder().companyName("Company Name").edition("FREE").marketPlaceBaseUrl("http://example.org").status("ACTIVE").build();
        Long id = subscriptionRepository.create(subscription);
        assertThat(subscriptionRepository.read(id).id, equalTo(id));
    }

    @Test
    public void subscriptionRepository_updateEdition() {
        Subscription subscription = new Subscription.Builder().companyName("Company Name").edition("FREE").marketPlaceBaseUrl("http://example.org").status("ACTIVE").build();
        Long id = subscriptionRepository.create(subscription);
        assertTrue(subscriptionRepository.updateEdition(id, "NOT_FREE"));
        assertThat(subscriptionRepository.read(id).edition, equalTo("NOT_FREE"));
    }

    @Test
    public void subscriptionRepository_updateStatus() {
        Subscription subscription = new Subscription.Builder().companyName("Company Name").edition("FREE").marketPlaceBaseUrl("http://example.org").status("ACTIVE").build();
        Long id = subscriptionRepository.create(subscription);
        assertTrue(subscriptionRepository.updateStatus(id, "INACTIVE"));
        assertThat(subscriptionRepository.read(id).status, equalTo("INACTIVE"));
    }

    @After
    public void tearDown() {
        subscriptionRepository.list().stream().forEach(s -> subscriptionRepository.delete(s.id));
    }

}
