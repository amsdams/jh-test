package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Car5;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Car5 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Car5Repository extends JpaRepository<Car5,Long> {
    
}
