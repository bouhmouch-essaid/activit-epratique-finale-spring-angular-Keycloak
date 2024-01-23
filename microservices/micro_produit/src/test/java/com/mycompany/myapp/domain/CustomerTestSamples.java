package com.mycompany.myapp.domain;

import java.util.UUID;

public class CustomerTestSamples {

    public static Customer getCustomerSample1() {
        return new Customer().id("id1").name("name1").email("email1");
    }

    public static Customer getCustomerSample2() {
        return new Customer().id("id2").name("name2").email("email2");
    }

    public static Customer getCustomerRandomSampleGenerator() {
        return new Customer().id(UUID.randomUUID().toString()).name(UUID.randomUUID().toString()).email(UUID.randomUUID().toString());
    }
}
