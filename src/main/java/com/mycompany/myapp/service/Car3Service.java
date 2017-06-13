package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Car3DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Car3.
 */
public interface Car3Service {

    /**
     * Save a car3.
     *
     * @param car3DTO the entity to save
     * @return the persisted entity
     */
    Car3DTO save(Car3DTO car3DTO);

    /**
     *  Get all the car3S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Car3DTO> findAll(Pageable pageable);

    /**
     *  Get the "id" car3.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Car3DTO findOne(Long id);

    /**
     *  Delete the "id" car3.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
