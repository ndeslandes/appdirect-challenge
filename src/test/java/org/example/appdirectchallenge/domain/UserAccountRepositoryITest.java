package org.example.appdirectchallenge.domain;

import org.example.appdirectchallenge.Application;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UserAccountRepositoryITest {

    @Autowired
    public UserAccountRepository userAccountRepository;

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Test
    public void userAccountRepository_create() {
        UserAccount user = new UserAccount.Builder().openId("https://example.org/openid/id/firstLastOpenID").name("First", "Last").email("first.last@example.org").subscriptionId(99L).build();
        userAccountRepository.create(user);
    }

    @Test
    public void userAccountRepository_listBySubscription() {
        UserAccount user1 = new UserAccount.Builder().openId("https://example.org/openid/id/firstLastOpenID").name("First", "Last").email("first.last@example.org").subscriptionId(99L).build();
        UserAccount user2 = new UserAccount.Builder().openId("https://example.org/openid/id/firstLastOpenID").name("First", "Last").email("first.last@example.org").subscriptionId(99L).build();
        userAccountRepository.create(user1);
        userAccountRepository.create(user2);
        assertThat(userAccountRepository.listBySubscription(99L), hasSize(2));
    }

    @Test
    public void userAccountRepository_readByOpenId_returnAnUserAccount() {
        UserAccount user = new UserAccount.Builder().openId("https://example.org/openid/id/firstLastOpenID").name("First", "Last").email("first.last@example.org").subscriptionId(99L).build();
        userAccountRepository.create(user);
        assertTrue(userAccountRepository.readByOpenId("https://example.org/openid/id/firstLastOpenID").isPresent());
    }

    @Test
    public void userAccountRepository_readByOpenId_returnNothing() {
        assertFalse(userAccountRepository.readByOpenId("https://example.org/openid/id/firstLastOpenID").isPresent());
    }

    @Test
    public void userAccountRepository_deleteByOpenId() {
        UserAccount user = new UserAccount.Builder().openId("https://example.org/openid/id/firstLastOpenID").name("First", "Last").email("first.last@example.org").subscriptionId(99L).build();
        userAccountRepository.create(user);
        assertTrue(userAccountRepository.deleteByOpenId("https://example.org/openid/id/firstLastOpenID"));
    }

    @After
    public void tearDown() {
        userAccountRepository.deleteBySubscriptionId(99L);
    }

}
