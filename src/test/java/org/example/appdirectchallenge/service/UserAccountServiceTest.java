package org.example.appdirectchallenge.service;

import org.example.appdirectchallenge.domain.UserAccount;
import org.example.appdirectchallenge.domain.UserAccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountServiceTest {

    private UserService userService;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Before
    public void setUp() {
        userService = new UserService(userAccountRepository);
    }

    @Test
    public void userService_currentUser_withoutSecurityContext_returnNothingAndHttpNotFound() {
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        assertThat(userService.currentUser(), is(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @Test
    public void userService_currentUser_withecurityContext_returnUserAndHttpOk() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        User openID = new User("https://example.org/openid/id/openID", "", Collections.emptyList());
        securityContext.setAuthentication(new OpenIDAuthenticationToken(openID, Collections.emptyList(), "", Collections.emptyList()));
        SecurityContextHolder.setContext(securityContext);

        UserAccount user = new UserAccount.Builder().id(1L).openId("https://example.org/openid/id/openID").name("Tony", "Stark").email("tony.stark@starkindustries.com").subscriptionId(1L).build();
        when(userAccountRepository.readByOpenid("https://example.org/openid/id/openID")).thenReturn(Optional.of(user));
        assertThat(userService.currentUser(), is(new ResponseEntity<>(user, HttpStatus.OK)));
    }

}