package com.keepllly.auth.web.rest.vm.keycloak;

import javax.validation.constraints.NotBlank;

public class GoogleAuthTokenRequest {

    private String subject_token_type;

    @NotBlank(message = "token must not be null")
    private String subject_token;

    private String client_id;

    private String client_secret;

    private String subject_issuer;
    private String grant_type;

    public GoogleAuthTokenRequest() {}

    public GoogleAuthTokenRequest(
        String subject_token_type,
        String subject_token,
        String client_id,
        String client_secret,
        String subject_issuer,
        String grant_type
    ) {
        this.subject_token_type = subject_token_type;
        this.subject_token = subject_token;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.subject_issuer = subject_issuer;
        this.grant_type = grant_type;
    }

    public String getSubject_token_type() {
        return subject_token_type;
    }

    public void setSubject_token_type(String subject_token_type) {
        this.subject_token_type = subject_token_type;
    }

    public String getSubject_token() {
        return subject_token;
    }

    public void setSubject_token(String subject_token) {
        this.subject_token = subject_token;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getSubject_issuer() {
        return subject_issuer;
    }

    public void setSubject_issuer(String subject_issuer) {
        this.subject_issuer = subject_issuer;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
