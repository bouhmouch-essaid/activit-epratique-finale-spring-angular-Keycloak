package com.keepllly.auth.web.rest.vm.keycloak;

import com.keepllly.auth.domain.enums.SubscriptionTypeEnum;

public class AuthTokenResponse {

    private String tokenId;
    private String id;
    private SubscriptionTypeEnum subscriptionType;
    private String accountStatus;
    private boolean expired;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public AuthTokenResponse id(String userId) {
        this.setId(userId);
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public AuthTokenResponse tokenId(String tokenId) {
        this.setTokenId(tokenId);
        return this;
    }




    public SubscriptionTypeEnum getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionTypeEnum subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
    public AuthTokenResponse subscriptionType(SubscriptionTypeEnum subscriptionType) {
        this.setSubscriptionType(subscriptionType);
        return this;
    }

    public String isAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public AuthTokenResponse accountStatus(String accountStatus) {
        this.setAccountStatus(accountStatus);
        return this;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
    public AuthTokenResponse expired(boolean expired) {
        this.setExpired(expired);
        return this;
    }

    public AuthTokenResponse() {}

    public AuthTokenResponse(String tokenId, String id, String uid) {
        this.tokenId = tokenId;
        this.id = id;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public AuthTokenResponse(String tokenId, String id,SubscriptionTypeEnum subscriptionType, String accountStatus, boolean expired) {
        this.tokenId = tokenId;
        this.id = id;
        this.subscriptionType = subscriptionType;
        this.accountStatus = accountStatus;
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "AuthTokenResponse{" +
            "tokenId='" + tokenId + '\'' +
            ", id='" + id + '\'' +
            ", subscriptionType=" + subscriptionType +
            ", accountStatus='" + accountStatus + '\'' +
            ", expired=" + expired +
            '}';
    }
}
