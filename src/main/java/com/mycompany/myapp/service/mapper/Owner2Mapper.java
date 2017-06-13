package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Owner2DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Owner2 and its DTO Owner2DTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Owner2Mapper extends EntityMapper <Owner2DTO, Owner2> {
    
    @Mapping(target = "ownedCar2S", ignore = true)
    @Mapping(target = "drivedCar2S", ignore = true)
    Owner2 toEntity(Owner2DTO owner2DTO); 
    default Owner2 fromId(Long id) {
        if (id == null) {
            return null;
        }
        Owner2 owner2 = new Owner2();
        owner2.setId(id);
        return owner2;
    }
}
