package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Car0DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Car0 and its DTO Car0DTO.
 */
@Mapper(componentModel = "spring", uses = {Owner0Mapper.class, })
public interface Car0Mapper extends EntityMapper <Car0DTO, Car0> {

    @Mapping(source = "owner0.id", target = "owner0Id")
    @Mapping(source = "owner0.name", target = "owner0Name")
    Car0DTO toDto(Car0 car0); 

    @Mapping(source = "owner0Id", target = "owner0")
    Car0 toEntity(Car0DTO car0DTO); 
    default Car0 fromId(Long id) {
        if (id == null) {
            return null;
        }
        Car0 car0 = new Car0();
        car0.setId(id);
        return car0;
    }
}
