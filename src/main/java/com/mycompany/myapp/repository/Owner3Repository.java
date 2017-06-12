package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Owner3;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Owner3 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Owner3Repository extends JpaRepository<Owner3,Long> {
    
}
