package org.example.appdirectchallenge.service;

import org.example.appdirectchallenge.domain.*;
import org.example.appdirectchallenge.domain.ErrorResponse.ErrorCode;
import org.example.appdirectchallenge.domain.appdirect.Account;
import org.example.appdirectchallenge.domain.appdirect.AppDirectUser;
import org.example.appdirectchallenge.domain.appdirect.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/notification/subscription")
public class NotificationService {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private SubscriptionRepository subscriptions;
    private UserRepository users;
    private ProtectedResourceDetails resource;

    @Autowired
    public NotificationService(SubscriptionRepository subscriptions, UserRepository users, ProtectedResourceDetails resource) {
        this.subscriptions = subscriptions;
        this.users = users;
        this.resource = resource;
    }

    @RequestMapping("create")
    public ResponseEntity<Response> create(@RequestParam("url") String url) {
        try {
            OAuthRestTemplate rest = new OAuthRestTemplate(resource);
            Notification notification = rest.getForObject(url, Notification.class);

            if("STATELESS".equals(notification.flag)) {
                return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
            }

            //TODO check unicity
            //if(users.read() != null) {
            //    return new ResponseEntity<>(new ErrorResponse("ACCOUNT_ALREADY_EXISTS", ""), HttpStatus.CONFLICT);
            //}

            AppDirectUser creator = notification.creator;
            Account account = notification.payload.account;

            Long subscriptionId = subscriptions.create(new Subscription(notification.payload.company.name, notification.payload.order.editionCode, account!=null?account.status:null));
            users.create(new User(creator.openId, creator.firstName, creator.lastName, creator.email, subscriptionId));

            return new ResponseEntity<>(new SuccessResponse(subscriptionId.toString()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.UNKNOWN_ERROR, ""), HttpStatus.OK);
        }
    }

    @RequestMapping({"change", "status"})
    public ResponseEntity<Response> change(@RequestParam("url") String url) {
        try {
            OAuthRestTemplate rest = new OAuthRestTemplate(resource);
            Notification notification = rest.getForObject(url, Notification.class);

            if("STATELESS".equals(notification.flag)) {
                return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
            }

            String accountIdentifier = notification.payload.account.accountIdentifier;
            if (subscriptions.update(new Subscription(Long.valueOf(accountIdentifier), notification.payload.company.name, notification.payload.order.editionCode, notification.payload.account.status))) {
                return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.ACCOUNT_NOT_FOUND, "The account " + accountIdentifier + " could not be found."), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.UNKNOWN_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @RequestMapping("cancel")
    public ResponseEntity<Response> cancel(@RequestParam("url") String url) {
        try {
            OAuthRestTemplate rest = new OAuthRestTemplate(resource);
            Notification notification = rest.getForObject(url, Notification.class);

            if("STATELESS".equals(notification.flag)) {
                return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
            }

            String accountIdentifier = notification.payload.account.accountIdentifier;
            if (subscriptions.delete(Long.valueOf(accountIdentifier))) {
                return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.ACCOUNT_NOT_FOUND, "The account " + accountIdentifier + " could not be found."), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.UNKNOWN_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

}