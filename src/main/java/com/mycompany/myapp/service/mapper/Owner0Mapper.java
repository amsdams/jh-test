package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Owner0DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Owner0 and its DTO Owner0DTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Owner0Mapper extends EntityMapper <Owner0DTO, Owner0> {
    
    @Mapping(target = "car0S", ignore = true)
    Owner0 toEntity(Owner0DTO owner0DTO); 
    default Owner0 fromId(Long id) {
        if (id == null) {
            return null;
        }
        Owner0 owner0 = new Owner0();
        owner0.setId(id);
        return owner0;
    }
}
