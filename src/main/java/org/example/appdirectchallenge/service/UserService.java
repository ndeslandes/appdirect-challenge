package org.example.appdirectchallenge.service;

import org.example.appdirectchallenge.domain.User;
import org.example.appdirectchallenge.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class UserService {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private UserRepository users;

    @Autowired
    public UserService(UserRepository users) {
        this.users = users;
    }

    @RequestMapping("users")
    public List<User> list() {
        log.info("Get users");
        return users.list();
    }
}