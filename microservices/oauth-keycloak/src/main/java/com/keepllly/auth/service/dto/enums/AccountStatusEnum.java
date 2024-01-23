package com.keepllly.auth.service.dto.enums;

import java.util.function.Supplier;

public enum AccountStatusEnum {
    ENABLED("ENABLED", () -> "ENABLED."),
    DISABLED("DISABLED", () -> "DISABLED.");


    private final String description;
    private final Supplier<String> messageSupplier;

    AccountStatusEnum(String description, Supplier<String> messageSupplier) {
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
