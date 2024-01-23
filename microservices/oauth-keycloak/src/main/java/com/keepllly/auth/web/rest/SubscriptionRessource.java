package com.keepllly.auth.web.rest;

import com.keepllly.auth.domain.Subscriber;
import com.keepllly.auth.domain.enums.SubscriptionTypeEnum;
import com.keepllly.auth.repository.SubscriberRepository;
import com.keepllly.auth.service.MailService;
import com.keepllly.auth.service.SubscriberService;
import com.keepllly.auth.service.UserService;
import com.keepllly.auth.service.dto.SubscriberBillingVM;
import com.keepllly.auth.service.dto.SubscriptionDTO;
import com.keepllly.auth.service.dto.enums.AccountStatusEnum;
import com.keepllly.auth.service.mapper.SubscriberMapper;
import com.keepllly.auth.utils.ExpirationUtils;
import com.keepllly.auth.utils.MdfUtils;
import com.keepllly.auth.web.rest.client.feign.keycloak.AdminClient;
import com.keepllly.auth.web.rest.errors.CustomException;
import com.keepllly.auth.web.rest.vm.CheckStatus;
import com.keepllly.auth.web.rest.vm.keycloak.AuthTokenResponse;
import com.keepllly.auth.web.rest.vm.keycloak.user.Credential;
import com.keepllly.auth.web.rest.vm.keycloak.user.UserVM;
import com.keeplly.utils.forms.PhoneNumber;
import com.keeplly.utils.securityUtils.Security;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class SubscriptionRessource {
    private static final Logger log = LoggerFactory.getLogger(SubscriptionRessource.class);
    private final SubscriberRepository subscriberRepository;
    private final SubscriberMapper subscriberMapper;
    private final MailService mailService;
    private final SubscriberService subscriberService;

    private final UserService userService;

    @Autowired
    Environment environment;

    public SubscriptionRessource(
        UserRessource userRessource,
        SubscriberRepository subscriberRepository,
        SubscriberMapper subscriberMapper, MailService mailService,
        SubscriberService subscriberService,
        AdminClient adminClient,
        UserService userService) {
        this.subscriberRepository = subscriberRepository;
        this.subscriberMapper = subscriberMapper;
        this.mailService = mailService;
        this.subscriberService = subscriberService;
        this.userService = userService;
    }

    /**
     * Endpoint to create a customer subscription and associated entities.
     *
     * @param subscriptionVM The subscription data for creating the customer.
     * @return The authentication token response for the created user.
     */
    @Operation(
        operationId = "createCustomer",
        summary = "Create a new customer subscription and associated entities.",
        description = "Creates a new customer subscription along with associated user, individual, and organization entities.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Authentication token response for the created user",
                content = @Content(schema = @Schema(implementation = AuthTokenResponse.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request if the creation process encounters an error"
            )
        }
    )
    @PostMapping("/customers/subscription")
    public AuthTokenResponse createCustomer(@RequestBody SubscriptionDTO subscriptionVM) {
        AuthTokenResponse user = createUser(subscriptionVM);
        try {
            subscriptionVM = subscriptionVM.fillUserInfo(user);


            subscriptionVM.setId(Objects.requireNonNull(user).getId());
            Subscriber subscriber = subscriberMapper.toSubscriber(subscriptionVM);
            subscriber.setSubscriptionType(SubscriptionTypeEnum.TRIAL);
            subscriber.setExpirationDate(ExpirationUtils.getExpirationDate(environment, subscriber.getCreatedAt(), SubscriptionTypeEnum.TRIAL));
            subscriber = subscriberRepository.save(subscriber);

            mailService.sendCreationEmail(subscriber);
            subscriberRepository.save(subscriber.verificationCode(MdfUtils.hashCode(subscriber.getVerificationCode())));

            return buildAuthTokenResponse(user, subscriber);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            userService.deleteUser(user.getTokenId(), "keeplly", user.getId());
        }

        return null;
    }


    /**
     * Endpoint to retrieve customer details by ID.
     *
     * @param id             The ID of the customer.
     * @param authentication The authentication details for the request.
     * @return The customer profile information.
     */
    @Operation(
        operationId = "getCustomer",
        summary = "Retrieve customer details by ID.",
        description = "Retrieves customer details based on the provided ID, including individual information.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful response with customer profile information."
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request if the retrieval process encounters an error"
            )
        }
    )
    @GetMapping("/profiles/id/{id}")
    public ResponseEntity<Subscriber> getCustomer(@PathVariable String id,
                                                  Authentication authentication) {
        Subscriber subscriber = subscriberRepository.findById(id)
            .orElseThrow(() -> new CustomException("User not found"));
        return ResponseEntity.ok(subscriber);
    }


    /**
     * Endpoint to retrieve customer details by UID.
     *
     * @param uid            The UID of the customer.
     * @param authentication The authentication details for the request.
     * @return The customer profile information.
     */
    @Operation(
        operationId = "getCustomerByUid",
        summary = "Retrieve customer details by UID.",
        description = "Retrieves customer details based on the provided UID, including individual information.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful response with customer profile information."
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request if the retrieval process encounters an error"
            )
        }
    )
    @GetMapping("/profiles/uid/{uid}")
    public ResponseEntity<Subscriber> getCustomerByUid(@PathVariable String uid,
                                                       Authentication authentication) {
        Subscriber subscriber = subscriberRepository.findByUid(uid);
        return ResponseEntity.ok(subscriber);
    }


    private AuthTokenResponse buildAuthTokenResponse(AuthTokenResponse user, Subscriber subscriber) {
        return new AuthTokenResponse(
            null,
            user.getId(),
            subscriber.getSubscriptionType(),
            subscriber.isVerified() ? AccountStatusEnum.ENABLED.name() : AccountStatusEnum.DISABLED.name(),
            false
        );
    }

    /**
     * Endpoint to update the username of a user.
     *
     * @param id             The ID of the user.
     * @param user           The user data containing the new username.
     * @param authentication The authentication information.
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    @Operation(
        operationId = "updateUsername",
        summary = "Update the username of a user.",
        description = "Updates the username of the specified user.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Username updated successfully"
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request if the new username is the same as the old one."
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error if failed to update username in Keycloak."
            )
        }
    )
    @PutMapping("/auth/{id}/username/update")
    public ResponseEntity<String> changeUsername(@PathVariable String id, @RequestBody UserVM user, Authentication authentication) {

        Subscriber subscriber = subscriberRepository.findById(id).orElseThrow(() -> new CustomException("User not found"));

        if (user.getUsername().equals(subscriber.getUsername())) {
            return ResponseEntity.badRequest().body("New username is the same as the old one.");
        }

        subscriber.setUsername(user.getUsername());
        ResponseEntity<String> keycloakResponse = userService.updateUser(Security.extractToken(authentication), id, user);
        log.info("keycloakResponse: {}", keycloakResponse);

        if (keycloakResponse.getStatusCode().is2xxSuccessful()) {
            subscriberRepository.save(subscriber);
            return ResponseEntity.ok("Username updated successfully");

        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update username in Keycloak");
        }

    }


    /**
     * Endpoint to update the password of a user.
     *
     * @param id             The ID of the user.
     * @param credential     The credential data containing the new password.
     * @param authentication The authentication information.
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    @Operation(
        operationId = "updatePassword",
        summary = "Update the password of a user.",
        description = "Updates the password of the specified user.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Password updated successfully"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error if failed to update password in Keycloak."
            )
        }
    )
    @PutMapping("/auth/{id}/password/update")
    public ResponseEntity<String> changeUserPassword(@PathVariable String id, @RequestBody Credential credential, Authentication authentication) {

        Subscriber subscriber = subscriberRepository.findById(id).orElseThrow(() -> new CustomException("User not found"));
        ResponseEntity<String> keycloakResponse = userService.updatePassword(Security.extractToken(authentication), subscriber.getId(), credential);

        if (keycloakResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update password in Keycloak");
        }

    }


    /**
     * Endpoint to check if a user with the provided phone number exists.
     *
     * @param phoneNumber The phone number to check.
     * @return The status of the user existence check.
     */
    @Operation(
        operationId = "checkUserExist",
        summary = "Check if a user with the provided phone number exists.",
        description = "Checks if a user with the provided phone number exists in the system.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "User existence status",
                content = @Content(schema = @Schema(implementation = CheckStatus.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request if the phone code or number is null"
            )
        }
    )
    @PostMapping("/customers/phone/validate")
    public CheckStatus checkUserExist(@RequestBody PhoneNumber phoneNumber) {
        Objects.requireNonNull(phoneNumber.getCode(), "phone code must not be null");
        Objects.requireNonNull(phoneNumber.getNumber(), "phone number must not be null");
        return new CheckStatus(subscriberService.checkSubscriberPhone(phoneNumber).name());
    }

    private AuthTokenResponse createUser(SubscriptionDTO subscriptionVM) {
        UserVM userVM = new UserVM(
            subscriptionVM.getUsername(),
            subscriptionVM.getFirstname(),
            subscriptionVM.getLastname(),
            subscriptionVM.getEmail(),
            subscriptionVM.getPassword());
        AuthTokenResponse user = userService.createUsers("keeplly", userVM);
        assert user != null;
        return user;
    }


    /**
     * Retrieves a {@code Subscriber} based on the provided UID.
     *
     * @param uid The unique identifier of the subscriber.
     * @return A {@code ResponseEntity} containing the {@code Subscriber} if found, or a 404 Not Found status.
     */
    @Operation(
        operationId = "findByUid",
        summary = "Find a subscriber by UID",
        description = "Retrieves a subscriber based on the provided UID.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Subscriber found",
                content = @Content(schema = @Schema(implementation = Subscriber.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Subscriber not found"
            )
        }
    )
    @GetMapping("/subscriber/uid/{uid}")
    public ResponseEntity<Subscriber> findByUid(@PathVariable String uid) {
        Subscriber subscriber = subscriberService.findByUid(uid);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(subscriber));
    }


    /**
     * Endpoint to find subscriber billing information by ID.
     *
     * @param id The ID of the subscriber.
     * @return The billing information for the subscriber.
     */
    @Operation(
        operationId = "findSubscriberBilling",
        summary = "Find subscriber billing information by ID.",
        description = "Retrieves billing information for a subscriber based on the provided ID.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Subscriber billing information found.",
                content = @Content(schema = @Schema(implementation = SubscriberBillingVM.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Subscriber not found."
            )
        }
    )
    @GetMapping("/subscriber-billing/id/{id}")
    public ResponseEntity<SubscriberBillingVM> findSubscriberBilling(@PathVariable String id) {
        Subscriber subscriber = subscriberService.findById(id).orElseThrow(() -> new CustomException("Subscriber with ID " + id + " does not exist"));
        return ResponseEntity.ok(subscriberMapper.toSubscriberBillingVM(subscriber));
    }


    /**
     * Update subscriber billing information by ID.
     *
     * @param id                  The ID of the subscriber for billing.
     * @param subscriberBillingVM The updated billing information for the subscriber.
     * @return ResponseEntity with the updated Subscriber if found, or not found if the subscriber does not exist.
     * @throws CustomException if the subscriber with the given ID does not exist.
     */
    @Operation(
        operationId = "updateSubscriberBilling",
        summary = "Update subscriber billing information by ID.",
        description = "Updates the billing information for a subscriber identified by the provided ID.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Updated subscriber billing information",
                content = @Content(schema = @Schema(implementation = Subscriber.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Subscriber not found"
            )
        }
    )
    @PutMapping("/subscriber-billing/{id}/id")
    public ResponseEntity<Subscriber> updateSubscriberBilling(
        @PathVariable String id,
        @RequestBody SubscriberBillingVM subscriberBillingVM) {
        return subscriberService.findById(id)
            .map(subscriber -> {
                subscriberMapper.updateSubscriberFromBillingVM(subscriber, subscriberBillingVM);
                subscriber.setUpdatedAt(Instant.now());
                return ResponseEntity.ok(subscriberRepository.save(subscriber));
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves subscriber billing information based on the provided UID.
     *
     * @param uid The unique identifier of the subscriber.
     * @return A {@code ResponseEntity} containing the {@code SubscriberBillingVM} if found, or a 404 Not Found status.
     */
    @Operation(
        operationId = "findSubscriberBillingByUid",
        summary = "Find subscriber billing by UID",
        description = "Retrieves subscriber billing information based on the provided UID.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Subscriber billing found",
                content = @Content(schema = @Schema(implementation = SubscriberBillingVM.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Subscriber not found"
            )
        }
    )
    @GetMapping("/subscriber-billing/uid/{uid}")
    public ResponseEntity<SubscriberBillingVM> findSubscriberBillingByUid(@PathVariable String uid) {
        Subscriber subscriber = subscriberService.findByUid(uid);
        if (subscriber != null) {
            return ResponseEntity.ok(subscriberMapper.toSubscriberBillingVM(subscriber));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Updates subscriber billing information based on the provided UID.
     *
     * @param uid                 The unique identifier of the subscriber.
     * @param subscriberBillingVM The updated billing information.
     * @return A {@code ResponseEntity} containing the updated {@code Subscriber} if found, or a 404 Not Found status.
     */
    @Operation(
        operationId = "updateSubscriberBillingByUid",
        summary = "Update subscriber billing by UID",
        description = "Updates subscriber billing information based on the provided UID.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Subscriber billing updated",
                content = @Content(schema = @Schema(implementation = Subscriber.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Subscriber not found"
            )
        }
    )
    @PutMapping("/subscriber-billing/{uid}/uid")
    public ResponseEntity<Subscriber> updateSubscriberBillingByUid(@PathVariable String uid, @RequestBody SubscriberBillingVM subscriberBillingVM) {
        Subscriber subscriber = subscriberService.findByUid(uid);
        if (subscriber != null) {
            subscriberMapper.updateSubscriberFromBillingVM(subscriber, subscriberBillingVM);
            subscriber.setUpdatedAt(Instant.now());
            return ResponseEntity.ok(subscriberRepository.save(subscriber));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Retrieves a map of {@code Subscriber} based on the provided UIDs.
     *
     * @param uids The set of unique identifiers of subscribers.
     * @return A {@code ResponseEntity} containing the map of {@code Subscriber} if found, or a 404 Not Found status.
     */
    @Operation(
        operationId = "findByUids",
        summary = "Find subscribers by UIDs",
        description = "Retrieves a map of subscribers based on the provided UIDs.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Subscribers found",
                content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Subscribers not found"
            )
        }
    )
    @PostMapping("/subscriber/mapping/by-uids")
    public ResponseEntity<Map<String, Subscriber>> findByUids(@RequestBody Set<String> uids) {
        Map<String, Subscriber> result = subscriberService.findSubscribersByUids(uids);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }


}
