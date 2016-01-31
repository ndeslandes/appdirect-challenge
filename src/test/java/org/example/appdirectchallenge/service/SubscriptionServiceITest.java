package org.example.appdirectchallenge.service;

import org.example.appdirectchallenge.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        this.base = new URL("http://localhost:" + port);
        template = new TestRestTemplate();
    }

    @Test
    public void list() {
        ResponseEntity<String> response = template.getForEntity(base + "/api/subscriptions", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo("[{\"id\":1,\"companyName\":\"Jacefoil inc\",\"edition\":\"FREE\",\"status\":\"INITIALIZED\",\"users\":[{\"id\":1,\"openId\":\"open_ID\",\"firstname\":\"Nicolas\",\"lastname\":\"Deslandes\",\"email\":\"deslandes.nicolas@gmail.com\",\"subscriptionId\":1}]}]"));
    }
}