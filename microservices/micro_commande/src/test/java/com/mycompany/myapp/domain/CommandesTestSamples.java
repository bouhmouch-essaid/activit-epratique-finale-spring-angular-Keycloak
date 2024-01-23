package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CommandesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Commandes getCommandesSample1() {
        return new Commandes().id("id1").quantity(1L).customerRef("customerRef1").productRef("productRef1");
    }

    public static Commandes getCommandesSample2() {
        return new Commandes().id("id2").quantity(2L).customerRef("customerRef2").productRef("productRef2");
    }

    public static Commandes getCommandesRandomSampleGenerator() {
        return new Commandes()
            .id(UUID.randomUUID().toString())
            .quantity(longCount.incrementAndGet())
            .customerRef(UUID.randomUUID().toString())
            .productRef(UUID.randomUUID().toString());
    }
}
