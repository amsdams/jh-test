package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Car4DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Car4 and its DTO Car4DTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Car4Mapper extends EntityMapper <Car4DTO, Car4> {
    
    
    default Car4 fromId(Long id) {
        if (id == null) {
            return null;
        }
        Car4 car4 = new Car4();
        car4.setId(id);
        return car4;
    }
}
