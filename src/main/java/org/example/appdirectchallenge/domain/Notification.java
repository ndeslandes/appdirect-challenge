package org.example.appdirectchallenge.domain;

public class Notification {

    public Type type;
    public Marketplace marketplace;
    public User creator;
    public Payload payload;

    public Notification(Type type, Marketplace marketplace, User creator, Payload payload) {
        this.type = type;
        this.marketplace = marketplace;
        this.creator = creator;
        this.payload = payload;
    }

    public class Marketplace {

        public String baseUrl;
        public String partner;

        public Marketplace(String baseUrl, String partner) {
            this.baseUrl = baseUrl;
            this.partner = partner;
        }

    }

    public class Payload {

        public Account account;
        public Order order;

        public Payload(Account account, Order order) {
            this.account = account;
            this.order = order;
        }

    }

    public class Account {

        public String accountIdentifier;
        public String status;

        public Account(String accountIdentifier, String status) {
            this.accountIdentifier = accountIdentifier;
            this.status = status;
        }

    }

    public class Order {

        public String editionCode;
        public String pricingDuration;
        public Item item;

        public Order(String editionCode, String pricingDuration, Item item) {
            this.editionCode = editionCode;
            this.pricingDuration = pricingDuration;
            this.item = item;
        }

    }

    public class Item {

        public String quantity;
        public String unit;

        public Item(String quantity, String unit) {
            this.quantity = quantity;
            this.unit = unit;
        }

    }

    public enum Type {
        SUBSCRIPTION_ORDER
    }
}