package org.example.appdirectchallenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.apache.xpath.internal.operations.String;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {

    public String type;
    public MarketPlace marketplace;
    public String applicationUuid;
    public String flag;
    public String returnUrl;
    public User creator;
    public Payload payload;

    @JsonCreator
    public Notification(@JsonProperty("type") String type,
                        @JsonProperty("marketplace") MarketPlace marketplace,
                        @JsonProperty("applicationUuid") String applicationUuid,
                        @JsonProperty("flag") String flag,
                        @JsonProperty("returnUrl") String returnUrl,
                        @JsonProperty("creator") User creator,
                        @JsonProperty("payload") Payload payload) {
        this.type = type;
        this.marketplace = marketplace;
        this.applicationUuid = applicationUuid;
        this.flag = flag;
        this.returnUrl = returnUrl;
        this.creator = creator;
        this.payload = payload;
    }

    public enum Type {
        SUBSCRIPTION_ORDER, SUBSCRIPTION_CHANGE, SUBSCRIPTION_CANCEL, SUBSCRIPTION_NOTICE, USER_ASSIGNMENT, USER_UNASSIGNMENT, USER_UPDATED
    }

}