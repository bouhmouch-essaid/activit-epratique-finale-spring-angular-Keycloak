package com.keepllly.auth.web.rest;

import com.keepllly.auth.config.Constants;
import com.keepllly.auth.domain.enums.CheckEnum;
import com.keepllly.auth.domain.Subscriber;
import com.keepllly.auth.security.AuthoritiesConstants;
import com.keepllly.auth.service.MailService;
import com.keepllly.auth.service.SubscriberService;
import com.keepllly.auth.service.UserService;
import com.keepllly.auth.utils.AuthUtil;
import com.keepllly.auth.utils.RegexValidator;
import com.keepllly.auth.web.rest.client.feign.keycloak.AdminClient;
import com.keepllly.auth.web.rest.client.feign.keycloak.UserClient;
import com.keepllly.auth.web.rest.errors.BadRequestAlertException;
import com.keepllly.auth.web.rest.vm.CheckStatus;
import com.keepllly.auth.web.rest.vm.keycloak.AuthTokenRequest;
import com.keepllly.auth.web.rest.vm.keycloak.AuthTokenResponse;
import com.keepllly.auth.web.rest.vm.keycloak.KeycloakAccessToken;
import com.keepllly.auth.web.rest.vm.keycloak.user.UserInfo;
import com.keepllly.auth.web.rest.vm.keycloak.user.UserVM;

import java.util.List;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserRessource {

    private static final String CLIENT_CREDENTIAL = "client_credentials";
    private static final String BEARER = "bearer ";
    private Logger log = LoggerFactory.getLogger(UserRessource.class);

    @Autowired
    UserClient userClient;

    @Autowired
    AdminClient adminClient;

    @Autowired
    Environment environment;

    @Autowired
    SubscriberService subscriberService;

    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    /**
     * Authenticates a user with the provided credentials.
     *
     * @param realmId          The ID of the Keycloak realm.
     * @param authTokenRequest The {@link AuthTokenRequest} object containing authentication details.
     * @return A {@link ResponseEntity} containing the {@link AuthTokenResponse} if authentication is successful.
     */
    @Operation(
        operationId = "authenticate",
        summary = "Authenticate a user",
        description = "Authenticates a user with the provided credentials.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Authentication successful",
                content = @Content(schema = @Schema(implementation = AuthTokenResponse.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "User not found"
            )
        }
    )
    @PostMapping("{realmId}/authenticate")
    ResponseEntity<AuthTokenResponse> authenticate(
        @PathVariable("realmId") String realmId,
        @RequestBody @Valid AuthTokenRequest authTokenRequest
    ) {
        String realmName = checkRealm(realmId);
        AuthTokenRequest authTokenRequest1 = AuthUtil.buildAuthTokenRequest(
            environment,
            authTokenRequest,
            realmId.replace(Constants.REALM, ""),
            environment.getProperty("application.realms.keeplly.grant-type")
        );
        KeycloakAccessToken authToken = null;
        try {
            authToken = userClient.getAuthToken(realmName, authTokenRequest1).getBody();
        } catch (Exception e) {
           return  null;
        }

        if (authToken != null) return ResponseEntity.ok(
            userService.processUserInfo(authToken, authTokenRequest, realmName));
        return ResponseEntity.notFound().build();
    }

    private AuthTokenResponse processUserInfo(UserInfo userInfo, String authToken) {
        return new AuthTokenResponse().id(userInfo.getId()).tokenId(authToken);
    }


    /**
     * Creates a new user in the specified Keycloak realm.
     *
     * @param realmId The ID of the Keycloak realm.
     * @param userVM  The {@link UserVM} object containing user details.
     * @return A {@link ResponseEntity} containing the {@link AuthTokenResponse} for the newly created user.
     */
    @Operation(
        operationId = "createUsers",
        summary = "Create a new user",
        description = "Creates a new user in the specified Keycloak realm.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "User creation successful",
                content = @Content(schema = @Schema(implementation = AuthTokenResponse.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request if user creation fails"
            )
        }
    )
    @PostMapping("/{realmId}/register")
    public ResponseEntity<AuthTokenResponse> createUsers(@PathVariable("realmId") String realmId, @RequestBody @Valid UserVM userVM) {
        String realmName = checkRealm(realmId);

        KeycloakAccessToken clientCredentials = userClient.getAuthToken(
            realmName,
            AuthUtil.buildAuthTokenRequest(environment, new AuthTokenRequest(), realmId.replace("realm-", ""), CLIENT_CREDENTIAL)
        ).getBody();

        try {
            adminClient.createUser(BEARER + clientCredentials.getAccessToken(), realmName, userVM);
            return ResponseEntity.ok(
                userService.processUserInfo(
                    clientCredentials,
                    new AuthTokenRequest(userVM.getUsername(), userVM.getCredentials().get(0).getValue()),
                    realmName
                )
            );
        } catch (Exception e) {
            throw new BadRequestAlertException("User creation failed " + e.getLocalizedMessage(), "User", "Not found");
        }
    }

    /**
     * Retrieves all public users in the specified Keycloak realm.
     *
     * @param realmId The ID of the Keycloak realm.
     * @param uuid    The username to filter users (optional).
     * @return A {@link ResponseEntity} containing a list of {@link UserInfo}.
     */
    @Operation(
        operationId = "getAllPublicUsers",
        summary = "Get all public users",
        description = "Retrieves all public users in the specified Keycloak realm.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved public users",
                content = @Content(schema = @Schema(implementation = UserInfo.class))
            )
        }
    )
    @GetMapping("/{realmId}/users")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<UserInfo>> getAllPublicUsers(
        @PathVariable("realmId") String realmId,
        @RequestParam(value = "username", required = false) String uuid
    ) {
        String realmName = checkRealm(realmId);

        KeycloakAccessToken credentials = userClient.getAuthToken(
            realmName,
            AuthUtil.buildAuthTokenRequest(environment, new AuthTokenRequest(), realmId, CLIENT_CREDENTIAL)
        ).getBody();
        return adminClient.getUserInfo(BEARER + credentials.getAccessToken(), realmName, uuid);
    }

    /**
     * Checks the existence of a user based on the provided username or email.
     *
     * @param realmId  The ID of the Keycloak realm.
     * @param username The username to check (optional).
     * @param email    The email to check (optional).
     * @return A {@link CheckStatus} indicating the existence status of the user.
     */
    @Operation(
        operationId = "checkUserExist",
        summary = "Check if a user exists",
        description = "Checks the existence of a user based on the provided username or email.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "User existence status",
                content = @Content(schema = @Schema(implementation = CheckStatus.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "User not found"
            )
        }
    )
    @GetMapping("/{realmId}/users/check")
    public CheckStatus checkUserExist(
        @PathVariable("realmId") String realmId,
        @RequestParam(value = "username", required = false) String username,
        @RequestParam(value = "email", required = false) String email
    ) {
        String realmName = checkRealm(realmId);
        KeycloakAccessToken clientCredentials = userClient.getAuthToken(
            realmName,
            AuthUtil.buildAuthTokenRequest(environment, new AuthTokenRequest(), realmId, CLIENT_CREDENTIAL)
        ).getBody();

        if (username != null) {
            return checkUserExistByField(adminClient.getUserInfo(BEARER + clientCredentials.getAccessToken(), realmName, username).getBody());
        } else if (email != null && RegexValidator.validate(email, RegexValidator.VALID_EMAIL_ADDRESS_REGEX)) {
            return checkUserExistByField(adminClient.getUserInfoByEmail(BEARER + clientCredentials.getAccessToken(), realmName, email).getBody());
        } else {
            return new CheckStatus(CheckEnum.NOT_FOUND.name());
        }
    }


    /**
     * Activates a user account using the provided activation code.
     *
     * @param code The activation code.
     * @return A {@link ResponseEntity} containing the activated {@link Subscriber}.
     */
    @Operation(
        operationId = "activateUser",
        summary = "Activate a user account",
        description = "Activates a user account using the provided activation code.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "User account activated",
                content = @Content(schema = @Schema(implementation = Subscriber.class))
            )
        }
    )
    @GetMapping("/user/activate/{code}")
    public ResponseEntity<Subscriber> activateUser(@PathVariable("code") String code) {
        return ResponseEntity.ok(subscriberService.activateCustomerCode(code));
    }


    private CheckStatus checkUserExistByField(List<UserInfo> userInfo) {
        if (userInfo != null && !userInfo.isEmpty()) {
            return new CheckStatus(CheckEnum.EXIST.name());
        }
        return new CheckStatus(CheckEnum.NOT_EXIST.name());
    }

    private String checkRealm(String realmId) {
        return realmId.startsWith(Constants.REALM) ? realmId : Constants.REALM + realmId;
    }
}
