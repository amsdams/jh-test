package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Car2DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Car2.
 */
public interface Car2Service {

    /**
     * Save a car2.
     *
     * @param car2DTO the entity to save
     * @return the persisted entity
     */
    Car2DTO save(Car2DTO car2DTO);

    /**
     *  Get all the car2S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Car2DTO> findAll(Pageable pageable);

    /**
     *  Get the "id" car2.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Car2DTO findOne(Long id);

    /**
     *  Delete the "id" car2.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the car2 corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Car2DTO> search(String query, Pageable pageable);
}
