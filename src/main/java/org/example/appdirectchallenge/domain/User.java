package org.example.appdirectchallenge.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    public Long id;
    public String openId;
    public String firstname;
    public String lastname;
    public String email;
    public Subscription subscription;

    public User(Long id, String openId, String firstname, String lastname, String email, Subscription subscription) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.openId = openId;
        this.email = email;
        this.subscription = subscription;
    }

    public User(String openId, String firstname, String lastname, String email, Subscription subscription) {
        this(null, openId, firstname, lastname, email, subscription);
    }

    public static String extractOpenId(String openIdUrl) {
        return openIdUrl.replaceFirst(".*/([^/?]+).*", "$1");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (openId != null ? !openId.equals(user.openId) : user.openId != null) return false;
        if (firstname != null ? !firstname.equals(user.firstname) : user.firstname != null) return false;
        if (lastname != null ? !lastname.equals(user.lastname) : user.lastname != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        return subscription != null ? subscription.equals(user.subscription) : user.subscription == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (openId != null ? openId.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (subscription != null ? subscription.hashCode() : 0);
        return result;
    }
}
