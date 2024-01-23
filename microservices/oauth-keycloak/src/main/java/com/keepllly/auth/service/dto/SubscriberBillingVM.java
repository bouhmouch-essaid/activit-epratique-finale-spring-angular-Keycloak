package com.keepllly.auth.service.dto;

import com.keepllly.auth.domain.embedded.BillingInformation;
import com.keepllly.auth.domain.embedded.CreditCard;
import com.keepllly.auth.domain.enums.PaymentMethodEnum;

import java.util.Objects;

public class SubscriberBillingVM {

    private BillingInformation billingInformation;

    private CreditCard creditCard;
    private PaymentMethodEnum paymentMethod;

    public BillingInformation getBillingInformation() {
        return billingInformation;
    }

    public void setBillingInformation(BillingInformation billingInformation) {
        this.billingInformation = billingInformation;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public PaymentMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubscriberBillingVM that = (SubscriberBillingVM) o;

        if (!Objects.equals(billingInformation, that.billingInformation))
            return false;
        if (!Objects.equals(creditCard, that.creditCard)) return false;
        return paymentMethod == that.paymentMethod;
    }

    @Override
    public int hashCode() {
        int result = billingInformation != null ? billingInformation.hashCode() : 0;
        result = 31 * result + (creditCard != null ? creditCard.hashCode() : 0);
        result = 31 * result + (paymentMethod != null ? paymentMethod.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SubscriberBillingVM{" +
            "billingInformation=" + billingInformation +
            ", creditCard=" + creditCard +
            ", paymentMethod=" + paymentMethod +
            '}';
    }
}
