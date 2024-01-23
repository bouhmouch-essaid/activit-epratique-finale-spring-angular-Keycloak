package com.keepllly.auth.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class Resilience4jConfig {

    @Autowired
    Environment env;

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> breakerFactoryCustomizer() {
        return factory ->
            factory.configureDefault(id ->
                new Resilience4JConfigBuilder(id)
                    //.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(10)).build())
                    .circuitBreakerConfig(
                        CircuitBreakerConfig
                            .custom()
                            .slidingWindowSize(10)
                            .minimumNumberOfCalls(4)
                            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED)
                            .permittedNumberOfCallsInHalfOpenState(2)
                            .waitDurationInOpenState(Duration.ofSeconds(30))
                            .failureRateThreshold(70)
                            .minimumNumberOfCalls(4)
                            .automaticTransitionFromOpenToHalfOpenEnabled(true)
                            .build()
                    )
                    .build()
            );
    }
}
