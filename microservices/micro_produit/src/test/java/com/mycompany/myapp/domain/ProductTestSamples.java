package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Product getProductSample1() {
        return new Product().id("id1").name("name1").price(1L);
    }

    public static Product getProductSample2() {
        return new Product().id("id2").name("name2").price(2L);
    }

    public static Product getProductRandomSampleGenerator() {
        return new Product().id(UUID.randomUUID().toString()).name(UUID.randomUUID().toString()).price(longCount.incrementAndGet());
    }
}
