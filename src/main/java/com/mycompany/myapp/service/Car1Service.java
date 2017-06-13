package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Car1DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Car1.
 */
public interface Car1Service {

    /**
     * Save a car1.
     *
     * @param car1DTO the entity to save
     * @return the persisted entity
     */
    Car1DTO save(Car1DTO car1DTO);

    /**
     *  Get all the car1S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Car1DTO> findAll(Pageable pageable);

    /**
     *  Get the "id" car1.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Car1DTO findOne(Long id);

    /**
     *  Delete the "id" car1.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
