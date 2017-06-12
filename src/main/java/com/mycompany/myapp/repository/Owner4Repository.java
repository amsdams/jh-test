package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Owner4;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Owner4 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Owner4Repository extends JpaRepository<Owner4,Long> {
    
}
