package com.keepllly.auth.web.rest;

import com.keepllly.auth.service.utils.JwtDecode;
import com.keepllly.auth.web.rest.client.feign.keycloak.AdminClient;
import com.keepllly.auth.web.rest.client.feign.keycloak.UserClient;
import com.keepllly.auth.web.rest.vm.keycloak.AuthTokenResponse;
import com.keepllly.auth.web.rest.vm.keycloak.GoogleAuthTokenRequest;
import com.keepllly.auth.web.rest.vm.keycloak.KeycloakAccessToken;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/google/")
public class GoogleAuthRessource {

    private final Logger log = LoggerFactory.getLogger(GoogleAuthRessource.class);

    @Autowired
    UserClient userClient;

    @Autowired
    AdminClient adminClient;

    @Autowired
    Environment environment;

    /**
     * Retrieves a Google Auth Token for the specified realm and validates it to obtain user information.
     *
     * @param realm_id The ID of the Keycloak realm.
     * @param googleAuthTokenRequest The {@link GoogleAuthTokenRequest} object containing Google Auth Token details.
     * @return A {@link Mono} emitting a {@link ResponseEntity} containing the validated {@link AuthTokenResponse}.
     */
    @Operation(
        operationId = "getGoogleAuthToken",
        summary = "Retrieves a Google Auth Token and validates it to obtain user information.",
        description = "Retrieves a Google Auth Token for the specified realm and validates it to obtain user information.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved and validated Google Auth Token",
                content = @Content(schema = @Schema(implementation = AuthTokenResponse.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request if the phone code or number is null"
            )
        }
    )
    @PostMapping("user/{realm_id}/auth")
    public Mono<ResponseEntity<AuthTokenResponse>> getGoogleAuthToken(
        @PathVariable("realm_id") String realm_id,
        @RequestBody @Valid GoogleAuthTokenRequest googleAuthTokenRequest
    ) {
        googleAuthTokenRequest.setGrant_type(environment.getProperty("application.realms.keeplly.grant-type"));
        googleAuthTokenRequest.setSubject_issuer(environment.getProperty("application.realms.keeplly.google.subject-issuer"));
        googleAuthTokenRequest.setSubject_token_type(environment.getProperty("application.realms.google.subject-token-type"));
        KeycloakAccessToken googleAuthToken = userClient.getGoogleAuthToken(realm_id, googleAuthTokenRequest).getBody();
        return Mono.just(
            ResponseEntity.ok(
                adminClient
                    .getUserInfo(
                        "bearer " + googleAuthToken.getAccessToken(),
                        realm_id,
                        JwtDecode.decodeJwtToken(googleAuthToken.getAccessToken()).email
                    )
                    .getBody()
                    .stream()
                    .findFirst()
                    .map(usetInfo -> new AuthTokenResponse().id(usetInfo.getId()).tokenId(googleAuthToken.getAccessToken()))
                    .orElse(null)
            )
        );
    }
}
