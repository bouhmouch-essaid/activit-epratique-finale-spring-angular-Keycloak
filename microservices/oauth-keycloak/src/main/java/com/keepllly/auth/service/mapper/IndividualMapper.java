package com.keepllly.auth.service.mapper;

import com.keepllly.auth.service.dto.SubscriptionDTO;
import com.keeplly.utils.data.dto.IndividualDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for  DTO {@link IndividualDTO}.
 */
@Mapper(componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface IndividualMapper {

    IndividualMapper INSTANCE = Mappers.getMapper(IndividualMapper.class);

    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "firstname", source = "firstname")
    @Mapping(target = "lastname", source = "lastname")
    @Mapping(target = "jobPosition", source = "jobPosition")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "country", source = "ownerCountry")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "ownerRef", source = "ownerRef")
    IndividualDTO toIndividualDTO(SubscriptionDTO subscriptionDTO);




}
