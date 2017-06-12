package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Car4;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Car4 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Car4Repository extends JpaRepository<Car4,Long> {
    
}
