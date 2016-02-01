package org.example.appdirectchallenge.service;

import org.example.appdirectchallenge.domain.SubscriptionRepository;
import org.example.appdirectchallenge.domain.User;
import org.example.appdirectchallenge.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api")
public class UserService {

    private UserRepository userRepository;

    private SubscriptionRepository subscriptionRepository;

    @Autowired
    public UserService(UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @RequestMapping("/user/current")
    public ResponseEntity<User> currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> user = userRepository.readByOpenid(User.extractOpenId(userDetails.getUsername()))
                    .map(u -> {
                        u.subscription = subscriptionRepository.read(u.subscription.id);
                        return u;
                    });
            return user.map(u -> new ResponseEntity<>(user.get(), HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
