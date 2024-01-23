package com.keepllly.auth.domain.enums;
import java.util.function.Supplier;

public  enum SubscriptionTypeEnum {
    TRIAL("TRIAL", () -> "Trial subscription."),
    ESSENTIAL("ESSENTIAL", () -> "Essential subscription."),
    PROFESSIONAL("PROFESSIONAL", () -> "Professional subscription."),
    ENTREPRISE("ENTREPRISE", () -> "Entreprise edition subscription.");


    private final String description;
    private final Supplier<String> messageSupplier;

    SubscriptionTypeEnum(String description, Supplier<String> messageSupplier) {
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
