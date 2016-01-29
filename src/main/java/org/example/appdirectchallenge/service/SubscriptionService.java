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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

        //TODO be asynchronous

        OAuthConsumer consumer = new DefaultOAuthConsumer("challenge-77055", "wgUqWZjxYW7J4Cs1");
        HttpURLConnection request = (HttpURLConnection) new URL(url).openConnection();
        request.setRequestProperty("Accept", "application/json");
        consumer.sign(request);
        request.connect();
        if (request.getResponseCode() == 200) {
            String response = readInputStream(request.getInputStream());
            log.info(response);

            ObjectMapper mapper = new ObjectMapper();
            Notification notification = mapper.readValue(response, Notification.class);

            //This signs a return URL:
            //OAuthConsumer consumer = new DefaultOAuthConsumer("Dummy", "secret");
            //consumer.setSigningStrategy( new QueryStringSigningStrategy());
            //String url = "https://www.appdirect.com/AppDirect/finishorder?success=true&accountIdentifer=Alice";
            //String signedUrl = consumer.sign(url);

            users.create(notification.creator);
        }
    }

    private String readInputStream(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        return sb.toString();
    }
}