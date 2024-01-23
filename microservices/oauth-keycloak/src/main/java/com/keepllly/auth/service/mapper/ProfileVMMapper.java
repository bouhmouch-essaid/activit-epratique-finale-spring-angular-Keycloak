package com.keepllly.auth.service.mapper;

import com.keepllly.auth.domain.Subscriber;
import com.keepllly.auth.web.rest.vm.keycloak.user.UserVM;
import com.keeplly.utils.data.dto.IndividualDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 */
@Mapper(componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileVMMapper {

    ProfileVMMapper INSTANCE = Mappers.getMapper(ProfileVMMapper.class);


}
