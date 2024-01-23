package com.keepllly.auth.domain.enums;

import java.util.function.Supplier;

public enum BillingServiceCompletionTimeEnum {
    REALTIME("REALTIME", () -> "REALTIME."),
    LESS_THAN_WEEK("LESS_THAN_WEEK", () -> "LESS_THAN_WEEK."),
    LESS_THAN_MONTH("LESS_THAN_MONTH", () -> "LESS_THAN_MONTH."),
    MORE_THAN_MONTH("MORE_THAN_MONTH", () -> "MORE_THAN_MONTH.");


    private final String description;
    private final Supplier<String> messageSupplier;

    BillingServiceCompletionTimeEnum(String description, Supplier<String> messageSupplier) {
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
