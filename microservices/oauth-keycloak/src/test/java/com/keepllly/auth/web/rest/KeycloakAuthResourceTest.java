package com.keepllly.auth.web.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import com.keepllly.auth.web.rest.vm.keycloak.AuthTokenRequest;
import com.keepllly.auth.web.rest.vm.keycloak.AuthTokenResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class KeycloakAuthResourceTest {

    @Autowired
    private UserRessource userRessource;

    @Test
    public void testGetAuthToken() {
        AuthTokenRequest authTokenRequest = new AuthTokenRequest("hamza", "12345678", "keeplly-app", "password");
        // call the method and get the response
        ResponseEntity<AuthTokenResponse> response = userRessource.authenticate("pactumcc", authTokenRequest);
        System.out.println(response);
        //assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
