package com.keepllly.auth.domain;

import com.keepllly.auth.domain.embedded.BillingInformation;
import com.keepllly.auth.domain.embedded.CreditCard;
import com.keepllly.auth.domain.enums.*;
import com.keeplly.utils.data.domain.AbstractEntity;
import com.keeplly.utils.forms.PhoneNumber;
import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;


@Document("customers")
@QueryEntity
public class Subscriber extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Transient
    public static String SEQUENCE_NAME = "customerSequence";

    @Transient
    public static String prefixUID = "cus_";

    @Id
    private String id;

    @Field("username")
    private String username;

    @Field("email")
    private String email;

    @Field("firstname")
    private String firstname;

    @Field("lastname")
    private String lastname;

    @Field("billingInformation")
    private BillingInformation billingInformation;

    @Field("verificationCode")
    private String verificationCode;

    @Field("verificationDate")
    private Instant verificationDate;
    private Instant expirationDate;

    @Field("verified")
    private boolean verified;

    @Field("phone")
    private PhoneNumber phone;

    @Field("subscriptionType")
    private SubscriptionTypeEnum subscriptionType;

    @Field("paymentMethod")
    private PaymentMethodEnum paymentMethod;

    @Field("saveCreditCardDetails")
    private boolean saveCreditCardDetails;

    @Field("creditCard")
    private CreditCard creditCard;

    @Field("customerCompanyRole")
    private CustomerCompanyRoleEnum customerCompanyRole;

    @Field("billingServiceCompletionTime")
    private BillingServiceCompletionTimeEnum billingServiceCompletionTime;

    @Field("customerInvoicingMethod")
    private CustomerInvoicingMethodEnum customerInvoicingMethod;

    @Field("invoiceCollectionMethod")
    private InvoiceCollectionMethodEnum invoiceCollectionMethod;

    @Field("hasCollectionTemplateScript")
    private boolean hasCollectionTemplateScript;

    public Subscriber() {
        this.setPrefixUID(prefixUID);
    }

    public Subscriber createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public Subscriber createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public Subscriber ownerRef(String ownerRef) {
        this.setOwnerRef(ownerRef);
        return this;
    }

    public Subscriber uid(String UID) {
        this.setUid(UID);
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Subscriber id(String id) {
        this.setId(id);
        return this;
    }

    public Instant getVerificationDate() {
        return verificationDate;
    }

    public void setVerificationDate(Instant verificationDate) {
        this.verificationDate = verificationDate;
    }

    public Subscriber verificationDate(Instant verificationDate) {
        this.setVerificationDate(verificationDate);
        return this;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Subscriber username(String username) {
        this.setUsername(username);
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Subscriber email(String email) {
        this.setEmail(email);
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Subscriber firstname(String firstname) {
        this.setFirstname(firstname);
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Subscriber lastname(String lastname) {
        this.setLastname(lastname);
        return this;
    }

    public BillingInformation getBillingInformation() {
        return billingInformation;
    }

    public void setBillingInformation(BillingInformation billingInformation) {
        this.billingInformation = billingInformation;
    }

    public Subscriber billingInformation(BillingInformation billingInformation) {
        this.setBillingInformation(billingInformation);
        return this;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Subscriber verificationCode(String verificationCode) {
        this.setVerificationCode(verificationCode);
        return this;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Subscriber verified(boolean verified) {
        this.setVerified(verified);
        return this;
    }

    public SubscriptionTypeEnum getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionTypeEnum subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Subscriber subscriptionType(SubscriptionTypeEnum subscriptionType) {
        this.setSubscriptionType(subscriptionType);
        return this;
    }

    public PaymentMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Subscriber paymentMethod(PaymentMethodEnum paymentMethod) {
        this.setPaymentMethod(paymentMethod);
        return this;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Subscriber creditCard(CreditCard creditCard) {
        this.setCreditCard(creditCard);
        return this;
    }

    public CustomerCompanyRoleEnum getCustomerCompanyRole() {
        return customerCompanyRole;
    }

    public void setCustomerCompanyRole(CustomerCompanyRoleEnum customerCompanyRole) {
        this.customerCompanyRole = customerCompanyRole;
    }

    public Subscriber customerCompanyRole(CustomerCompanyRoleEnum customerCompanyRole) {
        this.setCustomerCompanyRole(customerCompanyRole);
        return this;
    }

    public BillingServiceCompletionTimeEnum getBillingServiceCompletionTime() {
        return billingServiceCompletionTime;
    }

    public void setBillingServiceCompletionTime(BillingServiceCompletionTimeEnum billingServiceCompletionTime) {
        this.billingServiceCompletionTime = billingServiceCompletionTime;
    }

    public Subscriber billingServiceCompletionTime(BillingServiceCompletionTimeEnum billingServiceCompletionTime) {
        this.setBillingServiceCompletionTime(billingServiceCompletionTime);
        return this;
    }

    public CustomerInvoicingMethodEnum getCustomerInvoicingMethod() {
        return customerInvoicingMethod;
    }

    public void setCustomerInvoicingMethod(CustomerInvoicingMethodEnum customerInvoicingMethod) {
        this.customerInvoicingMethod = customerInvoicingMethod;
    }

    public Subscriber customerInvoicingMethod(CustomerInvoicingMethodEnum customerInvoicingMethod) {
        this.setCustomerInvoicingMethod(customerInvoicingMethod);
        return this;
    }

    public InvoiceCollectionMethodEnum getInvoiceCollectionMethod() {
        return invoiceCollectionMethod;
    }

    public void setInvoiceCollectionMethod(InvoiceCollectionMethodEnum invoiceCollectionMethod) {
        this.invoiceCollectionMethod = invoiceCollectionMethod;
    }

    public Subscriber invoiceCollectionMethod(InvoiceCollectionMethodEnum invoiceCollectionMethod) {
        this.setInvoiceCollectionMethod(invoiceCollectionMethod);
        return this;
    }

    public boolean isHasCollectionTemplateScript() {
        return hasCollectionTemplateScript;
    }

    public void setHasCollectionTemplateScript(boolean hasCollectionTemplateScript) {
        this.hasCollectionTemplateScript = hasCollectionTemplateScript;
    }

    public Subscriber hasCollectionTemplateScript(boolean hasCollectionTemplateScript) {
        this.setHasCollectionTemplateScript(hasCollectionTemplateScript);
        return this;
    }

    public boolean isSaveCreditCardDetails() {
        return saveCreditCardDetails;
    }

    public void setSaveCreditCardDetails(boolean saveCreditCardDetails) {
        this.saveCreditCardDetails = saveCreditCardDetails;
    }

    public Subscriber saveCreditCardDetails(boolean saveCreditCardDetails) {
        this.setSaveCreditCardDetails(saveCreditCardDetails);
        return this;
    }

    public PhoneNumber getPhone() {
        return phone;
    }

    public void setPhone(PhoneNumber phone) {
        this.phone = phone;
    }

    public Subscriber phone(PhoneNumber phone) {
        this.setPhone(phone);
        return this;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Subscriber expirationDate(Instant expirationDate) {
        this.setExpirationDate(expirationDate);
        return this;
    }

    @Override
    public String toString() {
        return (
            "Subscriber{" +
                "id='" +
                id +
                '\'' +
                ", username='" +
                username +
                '\'' +
                ", email='" +
                email +
                '\'' +
                ", firstname='" +
                firstname +
                '\'' +
                ", lastname='" +
                lastname +
                '\'' +
                ", billingInformation=" +
                billingInformation +
                ", verificationCode='" +
                verificationCode +
                '\'' +
                ", verified=" +
                verified +
                '\'' +
                ", phone=" +
                phone +
                ", subscriptionType=" +
                subscriptionType +
                ", paymentMethod=" +
                paymentMethod +
                ", saveCreditCardDetails=" +
                saveCreditCardDetails +
                ", creditCard=" +
                creditCard +
                ", customerCompanyRole=" +
                customerCompanyRole +
                ", billingServiceCompletionTime=" +
                billingServiceCompletionTime +
                ", customerInvoicingMethod=" +
                customerInvoicingMethod +
                ", invoiceCollectionMethod=" +
                invoiceCollectionMethod +
                ", hasCollectionTemplateScript=" +
                hasCollectionTemplateScript +
                "} " +
                super.toString()
        );
    }
}
