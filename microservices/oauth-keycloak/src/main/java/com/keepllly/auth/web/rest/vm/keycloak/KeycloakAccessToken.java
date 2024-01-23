package com.keepllly.auth.web.rest.vm.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KeycloakAccessToken {

    @JsonProperty("access_token")
    private String accessToken;

    public KeycloakAccessToken() {}

    public KeycloakAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "KeycloakAccessToken{" + "accessToken='" + accessToken + '\'' + '}';
    }
}
