package org.example.appdirectchallenge.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceTest {

    private UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();

    @Test
    public void userDetailsService_loadUserDetails() {
        String openIdUrl = "https://example.org/openid/id/openID";
        OpenIDAuthenticationToken authentication = new OpenIDAuthenticationToken(openIdUrl, Collections.emptyList(), "", Collections.emptyList());
        assertThat(userDetailsService.loadUserDetails(authentication), equalTo(new User(openIdUrl, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))));
    }

    @Test
    public void userDetailsService_loadUserByUsername() {
        String openIdUrl = "https://example.org/openid/id/openID";
        assertThat(userDetailsService.loadUserByUsername(openIdUrl), equalTo(new User(openIdUrl, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))));
    }

}