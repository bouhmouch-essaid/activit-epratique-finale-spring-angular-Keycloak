package com.keepllly.auth.domain.enums;

import java.util.function.Supplier;

public enum PaymentMethodEnum {
    VISA("VISA", () -> "Payment by visa card."),
    MASTER("MASTER", () -> "Payment by master card."),
    PAYPAL("PAYPAL", () -> "Payment by paypal.");


    private final String description;
    private final Supplier<String> messageSupplier;

    PaymentMethodEnum(String description, Supplier<String> messageSupplier) {
        this.description = description;
        this.messageSupplier = messageSupplier;
    }

    public String getDescription() {
        return description;
    }

    public String getCustomMessage() {
        return messageSupplier.get();
    }
}
