package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Owner4DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Owner4 and its DTO Owner4DTO.
 */
@Mapper(componentModel = "spring", uses = {Car4Mapper.class, })
public interface Owner4Mapper extends EntityMapper <Owner4DTO, Owner4> {

    @Mapping(source = "car4.id", target = "car4Id")
    @Mapping(source = "car4.name", target = "car4Name")
    Owner4DTO toDto(Owner4 owner4); 

    @Mapping(source = "car4Id", target = "car4")
    Owner4 toEntity(Owner4DTO owner4DTO); 
    default Owner4 fromId(Long id) {
        if (id == null) {
            return null;
        }
        Owner4 owner4 = new Owner4();
        owner4.setId(id);
        return owner4;
    }
}
