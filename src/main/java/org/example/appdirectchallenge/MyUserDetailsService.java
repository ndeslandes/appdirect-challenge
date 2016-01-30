package org.example.appdirectchallenge;

import org.example.appdirectchallenge.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class MyUserDetailsService implements UserDetailsService, AuthenticationUserDetailsService {

    private UserRepository users;

    @Autowired
    public MyUserDetailsService(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(final String openId) throws UsernameNotFoundException, DataAccessException {
        return new User(users.read(openId).email, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Override
    public UserDetails loadUserDetails(final Authentication authentication) throws UsernameNotFoundException {
        return loadUserByUsername((String) authentication.getPrincipal());
    }

}