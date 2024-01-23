package com.keepllly.auth.service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keepllly.auth.service.dto.jwt.User;
import java.util.Base64;

public class JwtDecode {

    private static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    public static User decodeJwtToken(String token) {
        ObjectMapper om = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        String[] parts = token.split("\\.");
        //String header = decode(parts[0]);
        //String signature = decode(parts[2]);
        try {
            User user = om.readValue(decode(parts[1]), User.class);
            return user;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
