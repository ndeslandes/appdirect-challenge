package org.example.appdirectchallenge.service;

import org.example.appdirectchallenge.domain.User;
import org.example.appdirectchallenge.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/user/current")
    public ResponseEntity<User> currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication instanceof OpenIDAuthenticationToken && authentication.getPrincipal() instanceof UserDetails) {
            OpenIDAuthenticationToken openIDAuthenticationToken = (OpenIDAuthenticationToken) authentication;
            logger.info(openIDAuthenticationToken.getAttributes().toString());
            logger.info(openIDAuthenticationToken.getAttributes().stream().map(o -> o.getName() + " -> [" + o.getValues().stream().collect(Collectors.joining(", ")) + "]").collect(Collectors.joining(", ")));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.readByOpenid(userDetails.getUsername());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
