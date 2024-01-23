package com.keepllly.auth.web.rest.client.feign.keycloak;

import com.keepllly.auth.config.CustomLoadBalancerConfiguration;
import com.keepllly.auth.web.rest.vm.keycloak.user.Credential;
import com.keepllly.auth.web.rest.vm.keycloak.user.UserInfo;
import com.keepllly.auth.web.rest.vm.keycloak.user.UserVM;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(
    name = "keycloakClient",
    url = "http://localhost:8080",
    path = "admin/realms/",
    configuration = CustomLoadBalancerConfiguration.class,
    fallback = AdminClientFallback.class
)
public interface AdminClient {
    @GetMapping(value = "{realm_id}/users", consumes = "application/x-www-form-urlencoded")
    ResponseEntity<List<UserInfo>> getUserInfo(
        @RequestHeader("Authorization") String bearerToken,
        @PathVariable("realm_id") String realm_id,
        @RequestParam("username") String username
    );

    @GetMapping(value = "{realm_id}/users", consumes = "application/x-www-form-urlencoded")
    ResponseEntity<List<UserInfo>> getUserInfoByEmail(
        @RequestHeader("Authorization") String bearerToken,
        @PathVariable("realm_id") String realm_id,
        @RequestParam("email") String email
    );

    @PutMapping(value = "{realm_id}/users/{user_id}/reset-password", consumes = "application/json")
    ResponseEntity<String> changePassword(
        @RequestHeader("Authorization") String bearerToken,
        @PathVariable("realm_id") String realm_id,
        @PathVariable("user_id") String user_id,
        @RequestBody Credential credential
    );

    @PutMapping(value = "{realm_id}/users/{user_id}", consumes = "application/json")
    ResponseEntity<String> updateUser(
        @RequestHeader("Authorization") String bearerToken,
        @PathVariable("realm_id") String realm_id,
        @PathVariable("user_id") String user_id,
        @RequestBody UserVM userVM
    );

    @DeleteMapping(value = "{realm_id}/users/{user_id}", consumes = "application/json")
    ResponseEntity<String> deleteUser(
        @RequestHeader("Authorization") String bearerToken,
        @PathVariable("realm_id") String realm_id,
        @PathVariable("user_id") String user_id
    );

    @PostMapping(value = "{realm_id}/users", consumes = "application/json")
    void createUser(
        @RequestHeader("Authorization") String bearerToken,
        @PathVariable("realm_id") String realm_id,
        @RequestBody UserVM userVM
    );

}
