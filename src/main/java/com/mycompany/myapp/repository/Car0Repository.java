package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Car0;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Car0 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Car0Repository extends JpaRepository<Car0,Long> {
    
}
