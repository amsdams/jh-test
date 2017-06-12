package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Car3;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Car3 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Car3Repository extends JpaRepository<Car3,Long> {
    
    @Query("select distinct car_3 from Car3 car_3 left join fetch car_3.owner3S")
    List<Car3> findAllWithEagerRelationships();

    @Query("select car_3 from Car3 car_3 left join fetch car_3.owner3S where car_3.id =:id")
    Car3 findOneWithEagerRelationships(@Param("id") Long id);
    
}
