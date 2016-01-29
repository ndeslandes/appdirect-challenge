package org.example.appdirectchallenge.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.example.appdirectchallenge.domain.*;
import org.example.appdirectchallenge.domain.ErrorResponse.ErrorCode;
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
@RequestMapping("api/notification/subscription")
public class NotificationService {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private SubscriptionRepository subscriptions;

    @Autowired
    public NotificationService(SubscriptionRepository subscriptions) {
        this.subscriptions = subscriptions;
    }

    @RequestMapping("create")
    public ResponseEntity<Response> create(@RequestParam("url") String url) {
        try {
            if (validateSignature()) {
                Notification notification = getNotification(url);

                //TODO check unicity
                //if(users.read() != null) {
                //    return new ResponseEntity<>(new ErrorResponse("ACCOUNT_ALREADY_EXISTS", ""), HttpStatus.CONFLICT);
                //}

                Long accountId = subscriptions.create(new Subscription(notification.creator.firstName, notification.creator.lastName, notification.payload.order.editionCode));
                return new ResponseEntity<>(new SuccessResponse(accountId.toString()), HttpStatus.OK);
            } else {
                log.warn("unauthorized");
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.UNAUTHORIZED, ""), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("error", e);
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.UNKNOWN_ERROR, ""), HttpStatus.OK);
        }
    }

    @RequestMapping("change")
    public ResponseEntity<Response> change(@RequestParam("url") String url) {
        try {
            if (validateSignature()) {
                Notification notification = getNotification(url);
                String accountIdentifier = notification.payload.account.accountIdentifier;
                if (subscriptions.update(new Subscription(Long.valueOf(accountIdentifier), notification.creator.firstName, notification.creator.lastName, notification.payload.order.editionCode))) {
                    return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ErrorResponse(ErrorCode.ACCOUNT_NOT_FOUND, "The account " + accountIdentifier + " could not be found."), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.UNAUTHORIZED, ""), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.UNKNOWN_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @RequestMapping("cancel")
    public ResponseEntity<Response> cancel(@RequestParam("url") String url) {
        try {
            if (validateSignature()) {
                Notification notification = getNotification(url);
                String accountIdentifier = notification.payload.account.accountIdentifier;
                if (subscriptions.delete(Long.valueOf(accountIdentifier))) {
                    return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ErrorResponse(ErrorCode.ACCOUNT_NOT_FOUND, "The account " + accountIdentifier + " could not be found."), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.UNAUTHORIZED, ""), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.UNKNOWN_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    private boolean validateSignature() {
        //TODO validate signature
        //This signs a return URL:
        //OAuthConsumer consumer = new DefaultOAuthConsumer("Dummy", "secret");
        //consumer.setSigningStrategy( new QueryStringSigningStrategy());
        //String url = "https://www.appdirect.com/AppDirect/finishorder?success=true&accountIdentifer=Alice";
        //String signedUrl = consumer.sign(url);
        return true;
    }

    private Notification getNotification(String url) throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        OAuthConsumer consumer = new DefaultOAuthConsumer("appdirect-challenge-77081", "hFVd5c6NCltoFaNB");
        HttpURLConnection request = (HttpURLConnection) new URL(url).openConnection();
        request.setRequestProperty("Accept", "application/json");
        consumer.sign(request);
        request.connect();
        String response = readInputStream(request.getInputStream());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, Notification.class);
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