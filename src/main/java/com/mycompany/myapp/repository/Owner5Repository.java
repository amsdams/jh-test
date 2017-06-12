package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Owner5;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Owner5 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Owner5Repository extends JpaRepository<Owner5,Long> {
    
    @Query("select distinct owner_5 from Owner5 owner_5 left join fetch owner_5.car5S")
    List<Owner5> findAllWithEagerRelationships();

    @Query("select owner_5 from Owner5 owner_5 left join fetch owner_5.car5S where owner_5.id =:id")
    Owner5 findOneWithEagerRelationships(@Param("id") Long id);
    
}
