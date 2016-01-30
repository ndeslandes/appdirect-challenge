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
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/user/current")
    public ResponseEntity<User> currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {

            logger.info(authentication.toString());

            if (authentication instanceof OpenIDAuthenticationToken) {
                OpenIDAuthenticationToken token = (OpenIDAuthenticationToken) authentication;
                for(OpenIDAttribute attribute : token.getAttributes()) {
                    logger.info(attribute.getName() + " -> [" + attribute.getValues().stream().collect(Collectors.joining(", ")) + "]");
                }
            }

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            User user = userRepository.readByOpenid(userDetails.getUsername());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}