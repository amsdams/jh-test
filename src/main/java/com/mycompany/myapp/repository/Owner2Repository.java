package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Owner2;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Owner2 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Owner2Repository extends JpaRepository<Owner2,Long> {
    
}
