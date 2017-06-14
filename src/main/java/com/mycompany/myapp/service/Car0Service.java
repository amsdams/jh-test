package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Car0DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Car0.
 */
public interface Car0Service {

    /**
     * Save a car0.
     *
     * @param car0DTO the entity to save
     * @return the persisted entity
     */
    Car0DTO save(Car0DTO car0DTO);

    /**
     *  Get all the car0S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Car0DTO> findAll(Pageable pageable);

    /**
     *  Get the "id" car0.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Car0DTO findOne(Long id);

    /**
     *  Delete the "id" car0.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the car0 corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Car0DTO> search(String query, Pageable pageable);
}
