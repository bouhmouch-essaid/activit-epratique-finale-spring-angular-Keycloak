package com.keepllly.auth.service.mapper;

import com.keepllly.auth.domain.Subscriber;
import com.keepllly.auth.service.dto.SubscriberBillingVM;
import com.keepllly.auth.service.dto.SubscriptionDTO;
import com.keeplly.utils.data.dto.IndividualDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for  DTO {@link IndividualDTO}.
 */
@Mapper(componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubscriberMapper {

    SubscriberMapper INSTANCE = Mappers.getMapper(SubscriberMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "firstname", source = "firstname")
    @Mapping(target = "lastname", source = "lastname")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "invoiceCollectionMethod", source = "invoiceCollectionMethod")
    @Mapping(target = "subscriptionType", constant = "TRIAL")
    @Mapping(target = "customerCompanyRole", source = "customerCompanyRole")
    @Mapping(target = "customerInvoicingMethod", source = "customerInvoicingMethod")
    @Mapping(target = "billingServiceCompletionTime", source = "billingServiceCompletionTime")
    @Mapping(target = "hasCollectionTemplateScript", source = "hasCollectionTemplateScript")
    @Mapping(target = "uid", source = "ownerRef")
    @Mapping(target = "verificationCode", expression = "java(com.keeplly.utils.NumberUtils.generateRandomLong())")
    @Mapping(target = "ownerRef", source = "ownerRef")
    @Mapping(target = "createdBy", source = "ownerRef")
    @Mapping(target = "verificationDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "verified", constant = "false")
    Subscriber toSubscriber(SubscriptionDTO subscriptionDTO);



    @Mapping(target = "billingInformation", source = "billingInformation")
    @Mapping(target = "creditCard", source = "creditCard")
    @Mapping(target = "paymentMethod", source = "paymentMethod")
    SubscriberBillingVM toSubscriberBillingVM(Subscriber subscriber);


    void updateSubscriberFromBillingVM(@MappingTarget Subscriber subscriber, SubscriberBillingVM subscriberBillingVM);

}
