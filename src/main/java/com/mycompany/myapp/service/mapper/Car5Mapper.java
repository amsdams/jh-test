package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Car5DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Car5 and its DTO Car5DTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Car5Mapper extends EntityMapper <Car5DTO, Car5> {
    
    @Mapping(target = "owner5S", ignore = true)
    Car5 toEntity(Car5DTO car5DTO); 
    default Car5 fromId(Long id) {
        if (id == null) {
            return null;
        }
        Car5 car5 = new Car5();
        car5.setId(id);
        return car5;
    }
}
