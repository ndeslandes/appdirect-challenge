package org.example.appdirectchallenge.service;

import org.example.appdirectchallenge.domain.Subscription;
import org.example.appdirectchallenge.domain.SubscriptionRepository;
import org.example.appdirectchallenge.domain.User;
import org.example.appdirectchallenge.domain.UserRepository;
import org.example.appdirectchallenge.domain.appdirect.*;
import org.example.appdirectchallenge.domain.appdirect.ErrorResponse.ErrorCode;
import org.example.appdirectchallenge.domain.appdirect.Notification.Flag;
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

import java.util.Optional;

@RestController
@RequestMapping("api/notification/subscription")
public class NotificationService {

    private Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private SubscriptionRepository subscriptionRepository;

    private UserRepository userRepository;

    private ProtectedResourceDetails resource;

    @Autowired
    public NotificationService(SubscriptionRepository subscriptionRepository, UserRepository userRepository, ProtectedResourceDetails resource) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.resource = resource;
    }

    @RequestMapping("create")
    public ResponseEntity<Response> create(@RequestParam("url") String url) {
        try {
            OAuthRestTemplate rest = new OAuthRestTemplate(resource);
            Notification notification = rest.getForObject(url, Notification.class);

            if(Flag.STATELESS.equals(notification.flag)) {
                return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
            }

            AppDirectUser creator = notification.creator;
            Optional<Account> account = notification.payload.account;
            Company company = notification.payload.company;
            Order order = notification.payload.order;

            if(userRepository.readByOpenid(creator.openId).isPresent()) {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.USER_ALREADY_EXISTS, ""), HttpStatus.CONFLICT);
            }

            Long subscriptionId = subscriptionRepository.create(new Subscription(company.name, order.editionCode, account.map(a -> a.status).orElse(null)));
            userRepository.create(new User(User.extractOpenId(creator.openId), creator.firstName, creator.lastName, creator.email, subscriptionId));

            return new ResponseEntity<>(new SuccessResponse(subscriptionId.toString()), HttpStatus.OK);
        } catch (Exception e) {
            String message = String.format("Exception thrown %s", e.getMessage());
            logger.error(message, e);
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.UNKNOWN_ERROR, message), HttpStatus.OK);
        }
    }

    @RequestMapping({"change", "status"})
    public ResponseEntity<Response> change(@RequestParam("url") String url) {
        try {
            OAuthRestTemplate rest = new OAuthRestTemplate(resource);
            Notification notification = rest.getForObject(url, Notification.class);

            if(Flag.STATELESS.equals(notification.flag)) {
                return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
            }

            Account account = notification.payload.account.get();
            Company company = notification.payload.company;
            Order order = notification.payload.order;

            if (subscriptionRepository.update(new Subscription(Long.valueOf(account.accountIdentifier), company.name, order.editionCode, account.status))) {
                return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.ACCOUNT_NOT_FOUND, String.format("The account %s could not be found.", account.accountIdentifier)), HttpStatus.OK);
            }
        } catch (Exception e) {
            String message = String.format("Exception thrown %s", e.getMessage());
            logger.error(message, e);
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.UNKNOWN_ERROR, message), HttpStatus.OK);
        }
    }

    @RequestMapping("cancel")
    public ResponseEntity<Response> cancel(@RequestParam("url") String url) {
        try {
            OAuthRestTemplate rest = new OAuthRestTemplate(resource);
            Notification notification = rest.getForObject(url, Notification.class);

            if(Flag.STATELESS.equals(notification.flag)) {
                return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
            }

            Long accountIdentifier = Long.valueOf(notification.payload.account.get().accountIdentifier);
            userRepository.deleteBySubscriptionId(accountIdentifier);
            if (subscriptionRepository.delete(accountIdentifier)) {
                return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.ACCOUNT_NOT_FOUND, "The account " + accountIdentifier + " could not be found."), HttpStatus.OK);
            }
        } catch (Exception e) {
            String message = String.format("Exception thrown %s", e.getMessage());
            logger.error(message, e);
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.UNKNOWN_ERROR, message), HttpStatus.OK);
        }
    }

}