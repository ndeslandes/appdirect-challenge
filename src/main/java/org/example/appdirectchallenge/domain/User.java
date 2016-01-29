package org.example.appdirectchallenge.domain;

public class User {

    public long id;
    public String firstname;
    public String lastname;
    public String email;
    public String version;

    public User(long id, String firstname, String lastname, String email, String version) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.version = version;
    }
}
