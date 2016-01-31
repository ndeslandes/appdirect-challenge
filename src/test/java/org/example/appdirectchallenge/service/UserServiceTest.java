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
import org.springframework.security.openid.OpenIDAuthenticationToken;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Before
    public void setUp() {
        userService = new UserService(userRepository, securityContext);
    }

    @Test
    public void userService_currentUser_withoutSecurityContext_returnNothingAndHttpNotFound() {
        assertThat(userService.currentUser(), is(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @Test
    public void userService_currentUser_withecurityContext_returnUserAndHttpOk() {
        User user = new User(1L, "openID", "Tony", "Stark", "tony.stark@starkindustries.com", 1L);
        when(securityContext.getAuthentication()).thenReturn(new OpenIDAuthenticationToken(new org.springframework.security.core.userdetails.User("openID", "", Collections.emptyList()), null, null, Collections.emptyList()));
        when(userRepository.readByOpenid("openID")).thenReturn(user);
        assertThat(userService.currentUser(), is(new ResponseEntity<>(user, HttpStatus.OK)));
    }

}