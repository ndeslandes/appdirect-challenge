package org.example.appdirectchallenge.service.appdirect;

import org.example.appdirectchallenge.domain.UserAccount;
import org.example.appdirectchallenge.domain.UserAccountRepository;
import org.example.appdirectchallenge.domain.appdirect.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/notification/access")
public class NotificationAccessService {

    private Logger logger = LoggerFactory.getLogger(NotificationSubscriptionService.class);

    private UserAccountRepository userAccountRepository;

    private AppDirectOAuthClient oAuthClient;


    @Autowired
    public NotificationAccessService(UserAccountRepository userAccountRepository, AppDirectOAuthClient oAuthClient) {
        this.userAccountRepository = userAccountRepository;
        this.oAuthClient = oAuthClient;
    }

    @RequestMapping("assign")
    public ResponseEntity<Response> assign(@RequestParam("url") String url) {
        try {
            Notification notification = oAuthClient.getNotification(url, Notification.Type.USER_ASSIGNMENT);

            AppDirectUser user = notification.payload.user;

            if (Notification.Flag.STATELESS.equals(notification.flag)) {
                return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
            }

            Long subscriptionId = Long.valueOf(notification.payload.account.accountIdentifier);

            if (userAccountRepository.readByOpenid(user.openId).isPresent()) {
                return new ResponseEntity<>(new ErrorResponse(ErrorResponse.ErrorCode.USER_ALREADY_EXISTS, ""), HttpStatus.CONFLICT);
            }

            userAccountRepository.create(new UserAccount.Builder().openId(user.openId).name(user.firstName, user.lastName).email(user.email).subscriptionId(subscriptionId).build());

            return new ResponseEntity<>(new SuccessResponse(subscriptionId.toString()), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception thrown", e);
            return new ResponseEntity<>(new ErrorResponse(ErrorResponse.ErrorCode.UNKNOWN_ERROR, String.format("Exception thrown %s", e.getMessage())), HttpStatus.OK);
        }
    }

    @RequestMapping("unassign")
    public ResponseEntity<Response> unassign(@RequestParam("url") String url) {
        try {
            Notification notification = oAuthClient.getNotification(url, Notification.Type.USER_UNASSIGNMENT);

            AppDirectUser user = notification.payload.user;

            if (Notification.Flag.STATELESS.equals(notification.flag)) {
                return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
            }

            Long subscriptionId = Long.valueOf(notification.payload.account.accountIdentifier);

            if (userAccountRepository.deleteByOpenId(user.openId)) {
                return new ResponseEntity<>(new SuccessResponse(subscriptionId.toString()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ErrorResponse(ErrorResponse.ErrorCode.USER_NOT_FOUND, ""), HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Exception thrown", e);
            return new ResponseEntity<>(new ErrorResponse(ErrorResponse.ErrorCode.UNKNOWN_ERROR, String.format("Exception thrown %s", e.getMessage())), HttpStatus.OK);
        }
    }

}