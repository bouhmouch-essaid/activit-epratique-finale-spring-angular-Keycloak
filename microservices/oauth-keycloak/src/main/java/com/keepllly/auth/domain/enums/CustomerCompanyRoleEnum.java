package com.keepllly.auth.domain.enums;

import java.util.function.Supplier;

public enum CustomerCompanyRoleEnum {
    BUSINESS_OWNER("BUSINESS_OWNER", () -> "BUSINESS_OWNER."),
    SALES_MANAGER("SALES_MANAGER", () -> "SALES_MANAGER."),
    ACCOUNTANT("ACCOUNTANT", () -> "ACCOUNTANT."),
    FREELANCER("FREELANCER", () -> "FREELANCER."),
    CONTRACTOR("CONTRACTOR", () -> "CONTRACTOR."),
    SELF_EMPLOYED("SELF_EMPLOYED", () -> "SELF_EMPLOYED.");


    private final String description;
    private final Supplier<String> messageSupplier;

    CustomerCompanyRoleEnum(String description, Supplier<String> messageSupplier) {
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
