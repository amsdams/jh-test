package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Owner1;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Owner1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Owner1Repository extends JpaRepository<Owner1,Long> {
    
}
