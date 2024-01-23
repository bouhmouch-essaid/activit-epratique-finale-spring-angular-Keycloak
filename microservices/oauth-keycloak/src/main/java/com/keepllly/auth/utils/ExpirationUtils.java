package com.keepllly.auth.utils;

import com.keepllly.auth.domain.enums.SubscriptionTypeEnum;
import org.springframework.core.env.Environment;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class ExpirationUtils {

    public static Instant getExpirationDate(Environment environment,Instant instant, SubscriptionTypeEnum subscriptionTypeEnum)
    {
        return instant.plus(
            Long.parseLong(
                Objects.requireNonNull(
                    environment.getProperty("application.expirations-time."+subscriptionTypeEnum.name().toLowerCase()))), ChronoUnit.DAYS);
    }
}
