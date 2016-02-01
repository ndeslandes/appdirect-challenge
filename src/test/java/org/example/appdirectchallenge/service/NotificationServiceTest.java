package org.example.appdirectchallenge.service;

import org.example.appdirectchallenge.domain.Subscription;
import org.example.appdirectchallenge.domain.SubscriptionRepository;
import org.example.appdirectchallenge.domain.User;
import org.example.appdirectchallenge.domain.UserRepository;
import org.example.appdirectchallenge.domain.appdirect.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceTest {

    private NotificationService notificationService;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AppDirectOAuthClient oAuthClient;

    @Before
    public void setUp() {
        notificationService = new NotificationService(subscriptionRepository, userRepository, oAuthClient);
    }

    @After
    public void tearDown() {
        reset(subscriptionRepository, userRepository, oAuthClient);
    }

    @Test
    public void notificationService_create_withANonexisitingUser_returnTrueAndTheAccountIdentifier() {
        when(oAuthClient.getNotification("url")).thenReturn(buildNotification());
        when(userRepository.readByOpenid("openid")).thenReturn(Optional.empty());
        when(subscriptionRepository.create(new Subscription("CompanyName", "FREE", null, "http://example.org"))).thenReturn(1L);

        ResponseEntity<Response> response = notificationService.create("url");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().success, is("true"));
        assertThat(((SuccessResponse) response.getBody()).accountIdentifier, is("1"));

        verify(userRepository).create(new User("openid", "", "", "", new Subscription(1L)));
    }

    @Test
    public void notificationService_create_withAnAlreadyExistingUser_returnFalseAndUserAlreadyExistsAndHttpConflict() {
        when(oAuthClient.getNotification("url")).thenReturn(buildNotification());
        when(userRepository.readByOpenid("openid")).thenReturn(Optional.of(new User(1L, "openid", "", "" ,"", null)));

        ResponseEntity<Response> response = notificationService.create("url");
        assertThat(response.getStatusCode(), is(HttpStatus.CONFLICT));
        assertThat(response.getBody().success, is("false"));
        assertThat(((ErrorResponse) response.getBody()).errorCode, is(ErrorResponse.ErrorCode.USER_ALREADY_EXISTS));
    }

    @Test
    public void notificationService_create_withAnException_returnFalseAnUnknownError() {
        when(oAuthClient.getNotification("url")).thenThrow(new RuntimeException("FAIL!!!"));

        ResponseEntity<Response> response = notificationService.create("url");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().success, is("false"));
        assertThat(((ErrorResponse) response.getBody()).errorCode, is(ErrorResponse.ErrorCode.UNKNOWN_ERROR));
    }

    @Test
    public void notificationService_change_somethingWasChanged_returnTrue() {
        when(oAuthClient.getNotification("url")).thenReturn(buildNotification());
        when(subscriptionRepository.update(new Subscription(1L, "CompanyName", "FREE", "ACTIVE", null))).thenReturn(true);

        ResponseEntity<Response> response = notificationService.change("url");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().success, is("true"));
    }

    @Test
    public void notificationService_change_nothingWasChanged_returnFalseAndAccountNotFound() {
        when(oAuthClient.getNotification("url")).thenReturn(buildNotification());
        when(subscriptionRepository.update(new Subscription(1L, "CompanyName", "FREE", "ACTIVE", null))).thenReturn(false);

        ResponseEntity<Response> response = notificationService.change("url");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().success, is("false"));
        assertThat(((ErrorResponse)response.getBody()).errorCode, is(ErrorResponse.ErrorCode.ACCOUNT_NOT_FOUND));
    }

    @Test
    public void notificationService_change_withAnException_returnFalseAnUnknownError() {
        when(oAuthClient.getNotification("url")).thenThrow(new RuntimeException("FAIL!!!"));

        ResponseEntity<Response> response = notificationService.change("url");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().success, is("false"));
        assertThat(((ErrorResponse) response.getBody()).errorCode, is(ErrorResponse.ErrorCode.UNKNOWN_ERROR));
    }

    @Test
    public void notificationService_cancel_somethingWasDeleted_returnTrue() {
        when(oAuthClient.getNotification("url")).thenReturn(buildNotification());
        when(userRepository.deleteBySubscriptionId(1L)).thenReturn(true);
        when(subscriptionRepository.delete(1L)).thenReturn(true);

        ResponseEntity<Response> response = notificationService.cancel("url");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().success, is("true"));
    }

    @Test
    public void notificationService_cancel_nothingWasDeleted_returnFalseAndAccountNotFound() {
        when(oAuthClient.getNotification("url")).thenReturn(buildNotification());
        when(userRepository.deleteBySubscriptionId(1L)).thenReturn(true);
        when(subscriptionRepository.delete(1L)).thenReturn(false);

        ResponseEntity<Response> response = notificationService.cancel("url");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().success, is("false"));
        assertThat(((ErrorResponse)response.getBody()).errorCode, is(ErrorResponse.ErrorCode.ACCOUNT_NOT_FOUND));

    }

    @Test
    public void notificationService_cancel_withAnException_returnFalseAnUnknownError() {
        when(oAuthClient.getNotification("url")).thenThrow(new RuntimeException("FAIL!!!"));

        ResponseEntity<Response> response = notificationService.cancel("url");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().success, is("false"));
        assertThat(((ErrorResponse) response.getBody()).errorCode, is(ErrorResponse.ErrorCode.UNKNOWN_ERROR));
    }

    private Notification buildNotification() {
        AppDirectUser user = new AppDirectUser("", "http://example.orf/openid/id/openid", "", "", "", "");
        Company company = new Company("", "", "CompanyName", "", "");
        Order order = new Order("FREE", "");
        Payload payload = new Payload(user, new Account("1", "ACTIVE"), company, order);
        MarketPlace marketPlace = new MarketPlace("http://example.org", "");
        return new Notification(null, marketPlace, null, null, user, payload);
    }

}