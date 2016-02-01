package org.example.appdirectchallenge.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collections;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Subscription {

    public Long id;
    public String companyName;
    public String edition;
    public String status;
    public String marketPlaceBaseUrl;
    public List<User> users;


    public Subscription(Long id) {
        this.id = id;
    }

    public Subscription(Long id, String companyName, String edition, String status, String marketPlaceBaseUrl, List<User> users) {
        this.companyName = companyName;
        this.edition = edition;
        this.status = status;
        this.users = users;
        this.marketPlaceBaseUrl = marketPlaceBaseUrl;
        this.id = id;
    }

    public Subscription(String companyName, String edition, String status, String marketPlaceBaseUrl, List<User> users) {
        this(null, companyName, edition, status, marketPlaceBaseUrl, users);
    }

    public Subscription(String companyName, String edition, String status, String marketPlaceBaseUrl) {
        this(null, companyName, edition, status, marketPlaceBaseUrl, Collections.emptyList());
    }


    public Subscription(Long id, String companyName, String edition, String status, String marketPlaceBaseUrl) {
        this(id, companyName, edition, status, marketPlaceBaseUrl, Collections.emptyList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subscription that = (Subscription) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;
        if (edition != null ? !edition.equals(that.edition) : that.edition != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (marketPlaceBaseUrl != null ? !marketPlaceBaseUrl.equals(that.marketPlaceBaseUrl) : that.marketPlaceBaseUrl != null)
            return false;
        return users != null ? users.equals(that.users) : that.users == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (edition != null ? edition.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (marketPlaceBaseUrl != null ? marketPlaceBaseUrl.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }
}
