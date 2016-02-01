package org.example.appdirectchallenge.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collections;
import java.util.List;

/**
 * Object that represent a subscription from the database.
 * The JsonInclude(JsonInclude.Include.NON_NULL) annotation prevent from adding a null value in the json
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Subscription {

    public Long id;
    public String companyName;
    public String edition;
    public String status;
    public String marketPlaceBaseUrl;
    public List<UserAccount> userAccounts;

    /**
     * Use Subscription.Builder
     */
    private Subscription() {
    }

    /**
     * Use UserAccount.Builder
     */
    private Subscription(Builder builder) {
        this.id = builder.id;
        this.companyName = builder.companyName;
        this.edition = builder.edition;
        this.status = builder.status;
        this.marketPlaceBaseUrl = builder.marketPlaceBaseUrl;
        this.userAccounts = builder.userAccounts;
    }

    @Override
    /**
     * Necessary for the Mockito matchers
     */
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
        return userAccounts != null ? userAccounts.equals(that.userAccounts) : that.userAccounts == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (edition != null ? edition.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (marketPlaceBaseUrl != null ? marketPlaceBaseUrl.hashCode() : 0);
        result = 31 * result + (userAccounts != null ? userAccounts.hashCode() : 0);
        return result;
    }

    /**
     * We use builder to construct object for better visibility and default value handling
     */
    public static class Builder {

        public Long id;
        public String companyName;
        public String edition;
        public String status;
        public String marketPlaceBaseUrl;
        public List<UserAccount> userAccounts = Collections.emptyList();

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public Builder edition(String edition) {
            this.edition = edition;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder marketPlaceBaseUrl(String marketPlaceBaseUrl) {
            this.marketPlaceBaseUrl = marketPlaceBaseUrl;
            return this;
        }

        public Builder userAccounts(List<UserAccount> userAccounts) {
            this.userAccounts = userAccounts;
            return this;
        }

        public Subscription build() {
            return new Subscription(this);
        }

    }

}
