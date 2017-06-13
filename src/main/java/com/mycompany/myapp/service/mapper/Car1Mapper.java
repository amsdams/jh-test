package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Car1DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Car1 and its DTO Car1DTO.
 */
@Mapper(componentModel = "spring", uses = {Owner1Mapper.class, })
public interface Car1Mapper extends EntityMapper <Car1DTO, Car1> {

    @Mapping(source = "owner1.id", target = "owner1Id")
    @Mapping(source = "owner1.name", target = "owner1Name")
    Car1DTO toDto(Car1 car1); 

    @Mapping(source = "owner1Id", target = "owner1")
    Car1 toEntity(Car1DTO car1DTO); 
    default Car1 fromId(Long id) {
        if (id == null) {
            return null;
        }
        Car1 car1 = new Car1();
        car1.setId(id);
        return car1;
    }
}
