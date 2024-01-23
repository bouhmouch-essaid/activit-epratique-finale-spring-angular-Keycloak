package com.keepllly.auth.web.rest.client.feign.keycloak;

import com.keepllly.auth.config.CustomLoadBalancerConfiguration;
import com.keepllly.auth.web.rest.vm.keycloak.AuthTokenRequest;
import com.keepllly.auth.web.rest.vm.keycloak.GoogleAuthTokenRequest;
import com.keepllly.auth.web.rest.vm.keycloak.KeycloakAccessToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(
    name = "keycloakClient",
    url = "http://localhost:8080",
    path = "/realms/",
    configuration = CustomLoadBalancerConfiguration.class,
    fallback = UserClientFallback.class
)
public interface UserClient {
    @PostMapping(value = "{realm_id}/protocol/openid-connect/token", consumes = "application/x-www-form-urlencoded")
    ResponseEntity<KeycloakAccessToken> getAuthToken(@PathVariable("realm_id") String realm_id, @RequestBody AuthTokenRequest authTokenRequest);

    @PostMapping(value = "{realm_id}/protocol/openid-connect/token", consumes = "application/x-www-form-urlencoded")
    ResponseEntity<KeycloakAccessToken> getGoogleAuthToken(
        @PathVariable("realm_id") String realm_id,
        @RequestBody GoogleAuthTokenRequest googleAuthTokenRequest
    );


}
