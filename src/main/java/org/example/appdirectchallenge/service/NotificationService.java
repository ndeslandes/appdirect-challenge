package org.example.appdirectchallenge.service;


import org.example.appdirectchallenge.domain.*;
import org.example.appdirectchallenge.domain.ErrorResponse.ErrorCode;
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

    private ProtectedResourceDetails resource;

    @Autowired
    public NotificationService(SubscriptionRepository subscriptions, ProtectedResourceDetails resource) {
        this.subscriptions = subscriptions;
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

            Long accountId = subscriptions.create(new Subscription(notification.creator.firstName, notification.creator.lastName, notification.payload.order.editionCode, notification.payload.account.status));
            return new ResponseEntity<>(new SuccessResponse(accountId.toString()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("error", e);
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
            if (subscriptions.update(new Subscription(Long.valueOf(accountIdentifier), notification.creator.firstName, notification.creator.lastName, notification.payload.order.editionCode, notification.payload.account.status))) {
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
