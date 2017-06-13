package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Owner5DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Owner5 and its DTO Owner5DTO.
 */
@Mapper(componentModel = "spring", uses = {Car5Mapper.class, })
public interface Owner5Mapper extends EntityMapper <Owner5DTO, Owner5> {
    
    
    default Owner5 fromId(Long id) {
        if (id == null) {
            return null;
        }
        Owner5 owner5 = new Owner5();
        owner5.setId(id);
        return owner5;
    }
}
