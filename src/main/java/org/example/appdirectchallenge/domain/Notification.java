package org.example.appdirectchallenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {

    public String type;
    public Marketplace marketplace;
    public User creator;
    public Payload payload;

    @JsonCreator
    public Notification(@JsonProperty("type") String type,
                        @JsonProperty("marketplace") Marketplace marketplace,
                        @JsonProperty("creator") User creator,
                        @JsonProperty("payload") Payload payload) {
        this.type = type;
        this.marketplace = marketplace;
        this.creator = creator;
        this.payload = payload;
    }

    public class Marketplace {

        public String baseUrl;
        public String partner;

        @JsonCreator
        public Marketplace(@JsonProperty("baseUrl") String baseUrl,
                           @JsonProperty("partner") String partner) {
            this.baseUrl = baseUrl;
            this.partner = partner;
        }

    }

    public class Payload {

        public Account account;
        public Order order;

        @JsonCreator
        public Payload(@JsonProperty("account") Account account,
                       @JsonProperty("order") Order order) {
            this.account = account;
            this.order = order;
        }

    }

    public class Account {

        public String accountIdentifier;
        public String status;

        @JsonCreator
        public Account(@JsonProperty("accountIdentifier") String accountIdentifier,
                       @JsonProperty("status") String status) {
            this.accountIdentifier = accountIdentifier;
            this.status = status;
        }

    }

    public class Order {

        public String editionCode;
        public String pricingDuration;
        public Item item;

        @JsonCreator
        public Order(@JsonProperty("editionCode") String editionCode,
                     @JsonProperty("pricingDuration") String pricingDuration,
                     @JsonProperty("item") Item item) {
            this.editionCode = editionCode;
            this.pricingDuration = pricingDuration;
            this.item = item;
        }

    }

    public class Item {

        public String quantity;
        public String unit;

        @JsonCreator
        public Item(@JsonProperty("quantity") String quantity,
                    @JsonProperty("unit") String unit) {
            this.quantity = quantity;
            this.unit = unit;
        }

    }

}