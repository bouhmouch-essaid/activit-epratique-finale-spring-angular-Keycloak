package com.keepllly.auth.web.rest.vm.keycloak;

import javax.validation.constraints.NotBlank;

public class AuthTokenRequest {

    @NotBlank(message = "username must not be null")
    private String username;

    @NotBlank(message = "password must not be null")
    private String password;

    //@NotBlank(message = "client_id must not be null")
    private String client_id;

    //@NotBlank(message = "client_id must not be null")
    private String client_secret;

    private String grant_type;

    public AuthTokenRequest() {}

    public AuthTokenRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthTokenRequest(String username, String password, String client_id, String client_secret, String grant_type) {
        this.username = username;
        this.password = password;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.grant_type = grant_type;
    }

    public AuthTokenRequest(String username, String password, String client_id, String client_secret) {
        this.username = username;
        this.password = password;
        this.client_id = client_id;
        this.client_secret = client_secret;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public AuthTokenRequest client_id(String client_id) {
        this.setClient_id(client_id);
        return this;
    }

    public AuthTokenRequest client_secret(String client_secret) {
        this.setClient_secret(client_secret);
        return this;
    }

    public AuthTokenRequest grant_type(String grant_type) {
        this.setGrant_type(grant_type);
        return this;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    @Override
    public String toString() {
        return (
            "AuthTokenRequest{" +
            "username='" +
            username +
            '\'' +
            ", password='" +
            password +
            '\'' +
            ", client_id='" +
            client_id +
            '\'' +
            ", client_secret='" +
            client_secret +
            '\'' +
            ", grant_type='" +
            grant_type +
            '\'' +
            '}'
        );
    }
}
