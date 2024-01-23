package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Commandes;
import com.mycompany.myapp.service.dto.CommandesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commandes} and its DTO {@link CommandesDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommandesMapper extends EntityMapper<CommandesDTO, Commandes> {}
