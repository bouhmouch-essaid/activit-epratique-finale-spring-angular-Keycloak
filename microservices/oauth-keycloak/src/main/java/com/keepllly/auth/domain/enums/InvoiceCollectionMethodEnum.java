package com.keepllly.auth.domain.enums;

import java.util.function.Supplier;

public enum InvoiceCollectionMethodEnum {
    
    PHONE_CALL_EMAIL("PHONE_CALL_EMAIL", () -> "PHONE_CALL_EMAIL."),
    BY_AGENCY("BY_AGENCY", () -> "BY_AGENCY.");


    private final String description;
    private final Supplier<String> messageSupplier;

    InvoiceCollectionMethodEnum(String description, Supplier<String> messageSupplier) {
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
