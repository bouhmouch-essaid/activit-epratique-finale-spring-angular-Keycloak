package com.keepllly.auth.utils;

import com.keepllly.auth.web.rest.vm.keycloak.AuthTokenRequest;
import org.springframework.core.env.Environment;

public class AuthUtil {

    public AuthUtil() {}

    public static AuthTokenRequest buildAuthTokenRequest(
        Environment environment,
        AuthTokenRequest authTokenRequest,
        String realm,
        String grant_Type
    ) {
        return authTokenRequest
            .client_id(environment.getProperty("application.realms." + realm + ".client-id"))
            .client_secret(environment.getProperty("application.realms." + realm + ".client-secret"))
            .grant_type(grant_Type);
    }
}
