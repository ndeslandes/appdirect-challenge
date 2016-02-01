package org.example.appdirectchallenge.service.appdirect;

import org.example.appdirectchallenge.domain.UserAccount;
import org.example.appdirectchallenge.domain.UserAccountRepository;
import org.example.appdirectchallenge.domain.appdirect.*;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotificationAccessServiceTest {

    private NotificationAccessService notificationAccessService;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private AppDirectOAuthClient oAuthClient;

    @Before
    public void setUp() {
        notificationAccessService = new NotificationAccessService(userAccountRepository, oAuthClient);
    }

    @Test
    public void notificationService_assign_withANonexisitingUser_returnTrueAndTheAccountIdentifier() {
        when(oAuthClient.getNotification("url", Notification.Type.USER_ASSIGNMENT)).thenReturn(buildNotification());
        when(userAccountRepository.readByOpenid("https://example.org/openid/id/openID")).thenReturn(Optional.empty());

        ResponseEntity<Response> response = notificationAccessService.assign("url");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().success, is("true"));

        verify(userAccountRepository).create(new UserAccount.Builder().email("first.last@example.org").name("First", "Last").openId("https://example.org/openid/id/openID").subscriptionId(1L).build());
    }

    @Test
    public void notificationService_assign_withAnAlreadyExistingUser_returnFalseAndUserAlreadyExistsAndHttpConflict() {
        when(oAuthClient.getNotification("url", Notification.Type.USER_ASSIGNMENT)).thenReturn(buildNotification());
        when(userAccountRepository.readByOpenid("https://example.org/openid/id/openID")).thenReturn(Optional.of(new UserAccount.Builder().openId("https://example.org/openid/id/openID").subscriptionId(1L).build()));

        ResponseEntity<Response> response = notificationAccessService.assign("url");
        assertThat(response.getStatusCode(), is(HttpStatus.CONFLICT));
        assertThat(response.getBody().success, is("false"));
        assertThat(((ErrorResponse) response.getBody()).errorCode, is(ErrorResponse.ErrorCode.USER_ALREADY_EXISTS));
    }

    @Test
    public void notificationService_assign_withABadNoticeType_returnFalseAnUnknownError() {
        when(oAuthClient.getNotification("url", Notification.Type.USER_UNASSIGNMENT)).thenThrow(new BadNotificationType(Notification.Type.USER_ASSIGNMENT, Notification.Type.USER_UNASSIGNMENT));

        ResponseEntity<Response> response = notificationAccessService.assign("url");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().success, is("false"));
        assertThat(((ErrorResponse) response.getBody()).errorCode, is(ErrorResponse.ErrorCode.UNKNOWN_ERROR));
    }

    @Test
    public void notificationService_unassign_withAnexisitingUser_returnTrue() {
        when(oAuthClient.getNotification("url", Notification.Type.USER_UNASSIGNMENT)).thenReturn(buildNotification());
        when(userAccountRepository.deleteByOpenId("https://example.org/openid/id/openID")).thenReturn(true);

        ResponseEntity<Response> response = notificationAccessService.unassign("url");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().success, is("true"));
    }

    @Test
    public void notificationService_unassign_withANonexisitingUser_returnFalseAndUserNotFound() {
        when(oAuthClient.getNotification("url", Notification.Type.USER_UNASSIGNMENT)).thenReturn(buildNotification());
        when(userAccountRepository.deleteByOpenId("https://example.org/openid/id/openID")).thenReturn(false);

        ResponseEntity<Response> response = notificationAccessService.unassign("url");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().success, is("false"));
        assertThat(((ErrorResponse) response.getBody()).errorCode, is(ErrorResponse.ErrorCode.USER_NOT_FOUND));
    }

    @Test
    public void notificationService_unassign_withAnException_returnFalseAnUnknownError() {
        when(oAuthClient.getNotification("url", Notification.Type.USER_UNASSIGNMENT)).thenThrow(new RuntimeException("FAIL!!!"));

        ResponseEntity<Response> response = notificationAccessService.unassign("url");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().success, is("false"));
        assertThat(((ErrorResponse) response.getBody()).errorCode, is(ErrorResponse.ErrorCode.UNKNOWN_ERROR));
    }

    private Notification buildNotification() {
        AppDirectUser user = new AppDirectUser("", "https://example.org/openid/id/openID", "first.last@example.org", "First", "Last", "");
        Company company = new Company("", "", "CompanyName", "", "");
        Order order = new Order("FREE", "");
        Payload payload = new Payload(user, new Account("1", "ACTIVE"), company, order);
        MarketPlace marketPlace = new MarketPlace("https://example.org", "");
        return new Notification(null, marketPlace, null, null, user, payload);
    }

}