package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Owner3DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Owner3 and its DTO Owner3DTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Owner3Mapper extends EntityMapper <Owner3DTO, Owner3> {
    
    @Mapping(target = "car3S", ignore = true)
    Owner3 toEntity(Owner3DTO owner3DTO); 
    default Owner3 fromId(Long id) {
        if (id == null) {
            return null;
        }
        Owner3 owner3 = new Owner3();
        owner3.setId(id);
        return owner3;
    }
}
