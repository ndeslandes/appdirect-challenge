package org.example.appdirectchallenge.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Object that represent a user_account from the database.
 * The JsonInclude(JsonInclude.Include.NON_NULL) annotation prevent from adding a null value in the json
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAccount {

    public Long id;
    public String openId;
    public String firstname;
    public String lastname;
    public String email;
    public Long subscriptionId;

    /**
     * Use UserAccount.Builder
     */
    private UserAccount() {
    }

    /**
     * Use UserAccount.Builder
     */
    private UserAccount(Builder builder) {
        this.id = builder.id;
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.openId = builder.openId;
        this.email = builder.email;
        this.subscriptionId = builder.subscriptionId;
    }

    @Override
    /**
     * Necessary for the Mockito matchers
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccount userAccount = (UserAccount) o;

        if (id != null ? !id.equals(userAccount.id) : userAccount.id != null) return false;
        if (openId != null ? !openId.equals(userAccount.openId) : userAccount.openId != null) return false;
        if (firstname != null ? !firstname.equals(userAccount.firstname) : userAccount.firstname != null) return false;
        if (lastname != null ? !lastname.equals(userAccount.lastname) : userAccount.lastname != null) return false;
        if (email != null ? !email.equals(userAccount.email) : userAccount.email != null) return false;
        return subscriptionId != null ? subscriptionId.equals(userAccount.subscriptionId) : userAccount.subscriptionId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (openId != null ? openId.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (subscriptionId != null ? subscriptionId.hashCode() : 0);
        return result;
    }

    /**
     * We use builder to construct object for better visibility and default value handling
     */
    public static class Builder {

        private Long id;
        private String openId;
        private String firstname;
        private String lastname;
        private String email;
        private Long subscriptionId;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder openId(String openId) {
            this.openId = openId;
            return this;
        }

        public Builder name(String firstname, String lastname) {
            this.firstname = firstname;
            this.lastname = lastname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder subscriptionId(Long subscriptionId) {
            this.subscriptionId = subscriptionId;
            return this;

        }

        public UserAccount build() {
            return new UserAccount(this);
        }

    }

}
