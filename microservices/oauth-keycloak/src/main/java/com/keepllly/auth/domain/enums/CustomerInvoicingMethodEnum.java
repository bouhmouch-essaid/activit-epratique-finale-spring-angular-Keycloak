package com.keepllly.auth.domain.enums;

import java.util.function.Supplier;

public enum CustomerInvoicingMethodEnum {
    PEN_PAPER("PEN_PAPER", () -> "PEN_PAPER."),
    SPREADSHEET("SPREADSHEET", () -> "SPREADSHEET."),
    DONT_USE_ANYTHING("DONT_USE_ANYTHING", () -> "DONT_USE_ANYTHING."),
    OTHER_ACCOUNTANT_SOFTWARE("OTHER_ACCOUNTANT_SOFTWARE", () -> "OTHER_ACCOUNTANT_SOFTWARE.");


    private final String description;
    private final Supplier<String> messageSupplier;

    CustomerInvoicingMethodEnum(String description, Supplier<String> messageSupplier) {
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
