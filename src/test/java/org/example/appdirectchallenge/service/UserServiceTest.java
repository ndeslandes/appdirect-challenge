package org.example.appdirectchallenge.service;

import org.example.appdirectchallenge.domain.User;
import org.example.appdirectchallenge.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    public void userService_currentUser_withoutSecurityContext_returnNothingAndHttpNotFound() {
        SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
        assertThat(userService.currentUser(), is(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @Test
    public void userService_currentUser_withecurityContext_returnUserAndHttpOk() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        org.springframework.security.core.userdetails.User openID = new org.springframework.security.core.userdetails.User("https://jacefoil-test.byappdirect.com/openid/id/openID", "", emptyList());
        securityContext.setAuthentication(new OpenIDAuthenticationToken(openID, emptyList(), "", emptyList()));
        SecurityContextHolder.setContext(securityContext);

        User user = new User(1L, "openID", "Tony", "Stark", "tony.stark@starkindustries.com", 1L);
        when(userRepository.readByOpenid("openID")).thenReturn(Optional.of(user));
        assertThat(userService.currentUser(), is(new ResponseEntity<>(user, HttpStatus.OK)));
    }
}