package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Owner0;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Owner0 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Owner0Repository extends JpaRepository<Owner0,Long> {
    
}
