package org.example.appdirectchallenge;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserDetailsServiceImpl implements UserDetailsService, AuthenticationUserDetailsService {

    @Override
    public UserDetails loadUserByUsername(final String openId) {
        return new User(openId, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Override
    public UserDetails loadUserDetails(final Authentication authentication) {
        return loadUserByUsername((String) authentication.getPrincipal());
    }

}