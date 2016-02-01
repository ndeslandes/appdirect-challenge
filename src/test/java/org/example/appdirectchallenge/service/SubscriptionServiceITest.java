package org.example.appdirectchallenge.service;

import org.example.appdirectchallenge.Application;
import org.example.appdirectchallenge.domain.Subscription;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class SubscriptionServiceITest {

    @Value("${local.server.port}")
    private int port;

    private URL base;
    private RestTemplate template;

    @Before
    public void setUp() throws MalformedURLException {
        this.base = new URL("http://localhost:" + port + "/api");
        template = new TestRestTemplate();
    }

    @Test
    public void api_subscriptions_returnOneSubscription() {
        Subscription[] object = template.getForObject(base + "/subscriptions", Subscription[].class);
        assertThat(object[0].companyName, equalTo("Jacefoil inc"));
        assertThat(object[0].edition, equalTo("FREE"));
        assertThat(object[0].status, equalTo("INITIALIZED"));
        assertThat(object[0].marketPlaceBaseUrl, equalTo("https://example.org"));
        assertThat(object[0].users.get(0).openId, equalTo("https://example.org/openid/id/openID"));
        assertThat(object[0].users.get(0).email, equalTo("deslandes.nicolas@gmail.com"));
        assertThat(object[0].users.get(0).firstname, equalTo("Nicolas"));
        assertThat(object[0].users.get(0).lastname, equalTo("Deslandes"));
    }

}