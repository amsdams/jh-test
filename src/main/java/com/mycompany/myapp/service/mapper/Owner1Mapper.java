package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Owner1DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Owner1 and its DTO Owner1DTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Owner1Mapper extends EntityMapper <Owner1DTO, Owner1> {
    
    
    default Owner1 fromId(Long id) {
        if (id == null) {
            return null;
        }
        Owner1 owner1 = new Owner1();
        owner1.setId(id);
        return owner1;
    }
}
