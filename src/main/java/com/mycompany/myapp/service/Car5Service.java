package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Car5DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Car5.
 */
public interface Car5Service {

    /**
     * Save a car5.
     *
     * @param car5DTO the entity to save
     * @return the persisted entity
     */
    Car5DTO save(Car5DTO car5DTO);

    /**
     *  Get all the car5S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Car5DTO> findAll(Pageable pageable);

    /**
     *  Get the "id" car5.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Car5DTO findOne(Long id);

    /**
     *  Delete the "id" car5.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the car5 corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Car5DTO> search(String query, Pageable pageable);
}
