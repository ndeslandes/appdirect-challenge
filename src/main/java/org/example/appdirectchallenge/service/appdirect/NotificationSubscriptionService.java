package org.example.appdirectchallenge.service.appdirect;

import org.example.appdirectchallenge.domain.Subscription;
import org.example.appdirectchallenge.domain.SubscriptionRepository;
import org.example.appdirectchallenge.domain.UserAccount;
import org.example.appdirectchallenge.domain.UserAccountRepository;
import org.example.appdirectchallenge.domain.appdirect.*;
import org.example.appdirectchallenge.domain.appdirect.ErrorResponse.ErrorCode;
import org.example.appdirectchallenge.domain.appdirect.Notification.Flag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/notification/subscription")
public class NotificationSubscriptionService {

    private Logger logger = LoggerFactory.getLogger(NotificationSubscriptionService.class);

    private SubscriptionRepository subscriptionRepository;

    private UserAccountRepository userAccountRepository;

    private AppDirectOAuthClient oAuthClient;


    @Autowired
    public NotificationSubscriptionService(SubscriptionRepository subscriptionRepository, UserAccountRepository userAccountRepository, AppDirectOAuthClient oAuthClient) {
        this.subscriptionRepository = subscriptionRepository;
        this.userAccountRepository = userAccountRepository;
        this.oAuthClient = oAuthClient;
    }

    @RequestMapping("create")
    public ResponseEntity<Response> create(@RequestParam("url") String url) {
        try {
            Notification notification = oAuthClient.getNotification(url, Notification.Type.SUBSCRIPTION_ORDER);

            AppDirectUser creator = notification.creator;
            Company company = notification.payload.company;
            Order order = notification.payload.order;

            if (Flag.STATELESS.equals(notification.flag)) {
                return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
            }

            if (userAccountRepository.readByOpenid(creator.openId).isPresent()) {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.USER_ALREADY_EXISTS, ""), HttpStatus.CONFLICT);
            }

            Long subscriptionId = subscriptionRepository.create(new Subscription.Builder().companyName(company.name).edition(order.editionCode).marketPlaceBaseUrl(notification.marketplace.baseUrl).build());
            userAccountRepository.create(new UserAccount.Builder().openId(creator.openId).name(creator.firstName, creator.lastName).email(creator.email).subscriptionId(subscriptionId).build());

            return new ResponseEntity<>(new SuccessResponse(subscriptionId.toString()), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception thrown", e);
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.UNKNOWN_ERROR, String.format("Exception thrown %s", e.getMessage())), HttpStatus.OK);
        }
    }

    @RequestMapping("change")
    public ResponseEntity<Response> change(@RequestParam("url") String url) {
        try {
            Notification notification = oAuthClient.getNotification(url, Notification.Type.SUBSCRIPTION_CHANGE);
            Account account = notification.payload.account;
            Order order = notification.payload.order;
            Long subscriptionId = Long.valueOf(account.accountIdentifier);

            if (Flag.STATELESS.equals(notification.flag)) {
                return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
            }

            if (subscriptionRepository.updateEdition(subscriptionId, order.editionCode)) {
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

    @RequestMapping("status")
    public ResponseEntity<Response> status(@RequestParam("url") String url) {
        try {
            Notification notification = oAuthClient.getNotification(url, Notification.Type.SUBSCRIPTION_NOTICE);
            Account account = notification.payload.account;

            if (Flag.STATELESS.equals(notification.flag)) {
                return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
            }

            Long subscriptionId = Long.valueOf(account.accountIdentifier);

            if (subscriptionRepository.updateStatus(subscriptionId, account.status)) {
                return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.ACCOUNT_NOT_FOUND, String.format("The account %s could not be found.", account.accountIdentifier)), HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Exception thrown", e);
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.UNKNOWN_ERROR, String.format("Exception thrown %s", e.getMessage())), HttpStatus.OK);
        }
    }

    @RequestMapping("cancel")
    public ResponseEntity<Response> cancel(@RequestParam("url") String url) {
        try {
            Notification notification = oAuthClient.getNotification(url, Notification.Type.SUBSCRIPTION_CANCEL);

            if (Flag.STATELESS.equals(notification.flag)) {
                return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
            }

            Long subscriptionId = Long.valueOf(notification.payload.account.accountIdentifier);

            userAccountRepository.deleteBySubscriptionId(subscriptionId);
            if (subscriptionRepository.delete(subscriptionId)) {
                return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ErrorResponse(ErrorCode.ACCOUNT_NOT_FOUND, "The account " + subscriptionId + " could not be found."), HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Exception thrown", e);
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.UNKNOWN_ERROR, String.format("Exception thrown %s", e.getMessage())), HttpStatus.OK);
        }
    }

}