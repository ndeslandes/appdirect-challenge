package org.example.appdirectchallenge.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import org.example.appdirectchallenge.domain.Notification;
import org.example.appdirectchallenge.domain.Response;
import org.example.appdirectchallenge.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping("create")
    public ResponseEntity<Response> create(@RequestParam("url") String url) throws Exception {
        OAuthConsumer consumer = new DefaultOAuthConsumer("challenge-77055", "m9zNfX64sSXU");
        //TODO validate request signature
        HttpURLConnection request = (HttpURLConnection) new URL(url).openConnection();
        request.setRequestProperty("Accept", "application/json");
        consumer.sign(request);
        request.connect();

        //if (request.getResponseCode() == 200) {
        String response = readInputStream(request.getInputStream());
        ObjectMapper mapper = new ObjectMapper();
        Notification notification = mapper.readValue(response, Notification.class);

        //This signs a return URL:
        //OAuthConsumer consumer = new DefaultOAuthConsumer("Dummy", "secret");
        //consumer.setSigningStrategy( new QueryStringSigningStrategy());
        //String url = "https://www.appdirect.com/AppDirect/finishorder?success=true&accountIdentifer=Alice";
        //String signedUrl = consumer.sign(url);

        //TODO you can do better than that!
        notification.creator.edition = notification.payload.order.editionCode;
        Long userId = users.create(notification.creator);
        return new ResponseEntity<>(new Response("true", userId.toString()), HttpStatus.OK);
        //}
        //return new ResponseEntity<>(new Response("false", ""), HttpStatus.OK);
    }

    @RequestMapping("cancel")
    public ResponseEntity<Response> cancel(@RequestParam("url") String url) throws Exception {
        OAuthConsumer consumer = new DefaultOAuthConsumer("challenge-77055", "m9zNfX64sSXU");
        //TODO validate request signature
        HttpURLConnection request = (HttpURLConnection) new URL(url).openConnection();
        request.setRequestProperty("Accept", "application/json");
        consumer.sign(request);
        request.connect();

        //if (request.getResponseCode() == 200) {
        String response = readInputStream(request.getInputStream());
        ObjectMapper mapper = new ObjectMapper();
        Notification notification = mapper.readValue(response, Notification.class);

        //This signs a return URL:
        //OAuthConsumer consumer = new DefaultOAuthConsumer("Dummy", "secret");
        //consumer.setSigningStrategy( new QueryStringSigningStrategy());
        //String url = "https://www.appdirect.com/AppDirect/finishorder?success=true&accountIdentifer=Alice";
        //String signedUrl = consumer.sign(url);

        //TODO you can do better than that!
        users.delete(notification.payload.account.accountIdentifier);
        return new ResponseEntity<>(new Response("true", ""), HttpStatus.OK);
        //}
        //return new ResponseEntity<>(new Response("false", ""), HttpStatus.OK);
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