package com.keepllly.auth.service;

import com.keepllly.auth.config.Constants;
import com.keepllly.auth.domain.Subscriber;
import com.keepllly.auth.service.dto.enums.AccountStatusEnum;
import com.keepllly.auth.utils.AuthUtil;
import com.keepllly.auth.web.rest.client.feign.keycloak.AdminClient;
import com.keepllly.auth.web.rest.client.feign.keycloak.UserClient;
import com.keepllly.auth.web.rest.errors.BadRequestAlertException;
import com.keepllly.auth.web.rest.errors.CustomException;
import com.keepllly.auth.web.rest.vm.keycloak.AuthTokenRequest;
import com.keepllly.auth.web.rest.vm.keycloak.AuthTokenResponse;
import com.keepllly.auth.web.rest.vm.keycloak.KeycloakAccessToken;
import com.keepllly.auth.web.rest.vm.keycloak.user.Credential;
import com.keepllly.auth.web.rest.vm.keycloak.user.UserInfo;
import com.keepllly.auth.web.rest.vm.keycloak.user.UserVM;
import com.keeplly.utils.data.dto.IndividualDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

/**
 * Service class for managing users.
 */
@Service
public class UserService {
    private static final String CLIENT_CREDENTIAL = "client_credentials";
    private static final String BEARER = "bearer ";
    @Autowired
    AdminClient adminClient;


    @Autowired
    Environment environment;
    @Autowired
    UserClient userClient;

    @Autowired
    SubscriberService subscriberService;
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    /**
     * Creates a new user in the specified Keycloak realm using the provided user details.
     *
     * @param realmId The ID of the Keycloak realm.
     * @param userVM  The {@link UserVM} object containing user details.
     * @return An {@link AuthTokenResponse} with user information and access token.
     * @throws BadRequestAlertException If user creation fails or user information retrieval fails.
     * @throws CustomException          If user information is not found after creation.
     */
    public AuthTokenResponse createUsers(String realmId, UserVM userVM) {
        String realmName = checkRealm(realmId);

        KeycloakAccessToken clientCredentials = userClient.getAuthToken(
            realmName,
            AuthUtil.buildAuthTokenRequest(
                environment,
                new AuthTokenRequest(),
                realmId.replace("realm-", ""),
                CLIENT_CREDENTIAL
            )
        ).getBody();

        try {
            adminClient.createUser(BEARER + clientCredentials.getAccessToken(), realmName, userVM);

        } catch (Exception e) {
            throw new BadRequestAlertException("User creation failed " + e.getLocalizedMessage(), "User", "Not found");
        }
        return adminClient
            .getUserInfo(BEARER + clientCredentials.getAccessToken(), realmName, userVM.getUsername())
            .getBody()
            .stream()
            .findFirst()
            .map(userInfo -> new AuthTokenResponse()
                .id(userInfo.getId())
                .tokenId(clientCredentials.getAccessToken())
                )
            .orElseThrow(() -> new CustomException("User Info not found"));
    }


    /**
     * Processes user information based on the provided access token, token request, and realm name.
     *
     * @param authToken        The {@link KeycloakAccessToken} containing the access token.
     * @param authTokenRequest The {@link AuthTokenRequest} object containing token request details.
     * @param realmName        The name of the Keycloak realm.
     * @return An {@link AuthTokenResponse} with processed user information.
     * @throws CustomException If user information or subscriber information is not found, or if the customer account is not verified.
     */
    public AuthTokenResponse processUserInfo(KeycloakAccessToken authToken, AuthTokenRequest authTokenRequest, String realmName) {
        UserInfo userInfo = adminClient
            .getUserInfo(BEARER + authToken.getAccessToken(), realmName, authTokenRequest.getUsername())
            .getBody()
            .stream()
            .findFirst()
            .orElseThrow(() -> new CustomException("User Info not found"));

        Subscriber subscriber = subscriberService.findById(userInfo.getId())
            .orElseThrow(() -> new CustomException("Subscriber not found"));
        if (!subscriber.isVerified()) throw new CustomException("customer account is not verified");

        boolean isExpired = subscriber.getExpirationDate().isBefore(Instant.now());
        boolean isVerified = subscriber.isVerified();

        AuthTokenResponse response = new AuthTokenResponse()
            .id(userInfo.getId())
            .tokenId((isExpired || !isVerified) ? null : authToken.getAccessToken())
            .subscriptionType(subscriber.getSubscriptionType())
            .accountStatus(AccountStatusEnum.ENABLED.name())
            .expired(isExpired);


        return response;
    }

    /**
     * Checks and adjusts the format of the Keycloak realm ID.
     *
     * @param realmId The ID of the Keycloak realm to check.
     * @return The adjusted Keycloak realm ID.
     */
    private String checkRealm(String realmId) {
        return realmId.startsWith(Constants.REALM) ? realmId : Constants.REALM + realmId;
    }

    /**
     * Deletes a user from the specified Keycloak realm using the provided access token, realm ID, and user ID.
     *
     * @param token   The access token for authorization.
     * @param realmId The ID of the Keycloak realm.
     * @param id      The ID of the user to be deleted.
     * @return A {@link ResponseEntity} containing the result of the user deletion operation.
     */
    public ResponseEntity<String> deleteUser(String token, String realmId, String id) {
        return adminClient.deleteUser("bearer " + token, checkRealm(realmId), id);
    }


    /**
     * Updates the password of a user in Keycloak.
     *
     * @param token      The authentication token.
     * @param userId     The ID of the user.
     * @param credential The credential data containing the new password.
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    public ResponseEntity<String> updatePassword(String token, String userId, Credential credential) {
        String realmName = checkRealm("keeplly");
        return adminClient.changePassword("bearer " + token, realmName, userId, credential);
    }

    /**
     * Updates the information of a user in Keycloak.
     *
     * @param token  The authentication token.
     * @param userId The ID of the user.
     * @param user   The user data containing the updated information.
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    public ResponseEntity<String> updateUser(String token, String userId, UserVM user) {
        String realmName = checkRealm("keeplly");
        return adminClient.updateUser("bearer " + token, realmName, userId, user);
    }


}
