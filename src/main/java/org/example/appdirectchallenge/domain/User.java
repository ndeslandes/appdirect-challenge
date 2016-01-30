package org.example.appdirectchallenge.domain;

import java.util.List;

public class User {

    public String username;
    public List<String> authorities;

    public User(String username, List<String> authorities) {
        this.username = username;
        this.authorities = authorities;
    }
}
