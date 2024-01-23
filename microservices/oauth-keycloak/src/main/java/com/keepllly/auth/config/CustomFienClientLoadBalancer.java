package com.keepllly.auth.config;

import feign.Feign;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;

@LoadBalancerClients(
    value = { @LoadBalancerClient(value = "barcodegenerator") },
    defaultConfiguration = CustomLoadBalancerConfiguration.class
)
public class CustomFienClientLoadBalancer {

    @LoadBalanced
    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }
}
