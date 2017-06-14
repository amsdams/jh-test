package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Car4DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Car4.
 */
public interface Car4Service {

    /**
     * Save a car4.
     *
     * @param car4DTO the entity to save
     * @return the persisted entity
     */
    Car4DTO save(Car4DTO car4DTO);

    /**
     *  Get all the car4S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Car4DTO> findAll(Pageable pageable);

    /**
     *  Get the "id" car4.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Car4DTO findOne(Long id);

    /**
     *  Delete the "id" car4.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the car4 corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Car4DTO> search(String query, Pageable pageable);
}
