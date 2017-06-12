package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Car2;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Car2 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Car2Repository extends JpaRepository<Car2,Long> {
    
}
