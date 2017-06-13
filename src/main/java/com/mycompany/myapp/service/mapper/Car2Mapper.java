package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Car2DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Car2 and its DTO Car2DTO.
 */
@Mapper(componentModel = "spring", uses = {Owner2Mapper.class, })
public interface Car2Mapper extends EntityMapper <Car2DTO, Car2> {

    @Mapping(source = "owner2.id", target = "owner2Id")

    @Mapping(source = "driver2.id", target = "driver2Id")
    Car2DTO toDto(Car2 car2); 

    @Mapping(source = "owner2Id", target = "owner2")

    @Mapping(source = "driver2Id", target = "driver2")
    Car2 toEntity(Car2DTO car2DTO); 
    default Car2 fromId(Long id) {
        if (id == null) {
            return null;
        }
        Car2 car2 = new Car2();
        car2.setId(id);
        return car2;
    }
}
