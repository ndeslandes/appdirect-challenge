package org.example.appdirectchallenge.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import org.example.appdirectchallenge.domain.Notification;
import org.example.appdirectchallenge.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;
import java.net.URL;

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
    public void subscriptionCreated(@RequestParam("url") String url) throws Exception {
        log.info("subscriptionCreated url=" + url);
        //TODO validate request signature

        OAuthConsumer consumer = new DefaultOAuthConsumer("challenge-77055", "wgUqWZjxYW7J4Cs1");
        HttpURLConnection request = (HttpURLConnection) new URL(url).openConnection();
        consumer.sign(request);
        request.connect();
        String responseMessage = request.getResponseMessage();
        log.info(responseMessage);

        ObjectMapper mapper = new ObjectMapper();
        Notification notification = mapper.readValue(responseMessage, Notification.class);

        //This signs a return URL:
        //OAuthConsumer consumer = new DefaultOAuthConsumer("Dummy", "secret");
        //consumer.setSigningStrategy( new QueryStringSigningStrategy());
        //String url = "https://www.appdirect.com/AppDirect/finishorder?success=true&accountIdentifer=Alice";
        //String signedUrl = consumer.sign(url);

        users.create(notification.creator);
    }
}