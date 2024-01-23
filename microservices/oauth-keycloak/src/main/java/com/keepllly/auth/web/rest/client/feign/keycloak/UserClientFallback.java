package com.keepllly.auth.web.rest.client.feign.keycloak;

import com.keepllly.auth.web.rest.vm.keycloak.AuthTokenRequest;
import com.keepllly.auth.web.rest.vm.keycloak.GoogleAuthTokenRequest;
import com.keepllly.auth.web.rest.vm.keycloak.KeycloakAccessToken;
import org.springframework.http.ResponseEntity;

public class UserClientFallback implements UserClient{

    @Override
    public ResponseEntity<KeycloakAccessToken> getAuthToken(String realm_id, AuthTokenRequest authTokenRequest) {
        return null;
    }

    @Override
    public ResponseEntity<KeycloakAccessToken> getGoogleAuthToken(String realm_id, GoogleAuthTokenRequest googleAuthTokenRequest) {
        return null;
    }
}
