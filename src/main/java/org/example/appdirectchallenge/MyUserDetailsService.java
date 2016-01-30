package org.example.appdirectchallenge;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class MyUserDetailsService implements UserDetailsService, AuthenticationUserDetailsService {

    @Override
    public UserDetails loadUserByUsername(final String openId) throws UsernameNotFoundException, DataAccessException {
        return new User(openId, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Override
    public UserDetails loadUserDetails(final Authentication authentication) throws UsernameNotFoundException {
        return loadUserByUsername((String) authentication.getPrincipal());
    }

}