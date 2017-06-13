package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Car3DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Car3 and its DTO Car3DTO.
 */
@Mapper(componentModel = "spring", uses = {Owner3Mapper.class, })
public interface Car3Mapper extends EntityMapper <Car3DTO, Car3> {
    
    
    default Car3 fromId(Long id) {
        if (id == null) {
            return null;
        }
        Car3 car3 = new Car3();
        car3.setId(id);
        return car3;
    }
}
