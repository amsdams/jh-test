package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Owner0DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Owner0.
 */
public interface Owner0Service {

    /**
     * Save a owner0.
     *
     * @param owner0DTO the entity to save
     * @return the persisted entity
     */
    Owner0DTO save(Owner0DTO owner0DTO);

    /**
     *  Get all the owner0S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Owner0DTO> findAll(Pageable pageable);

    /**
     *  Get the "id" owner0.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Owner0DTO findOne(Long id);

    /**
     *  Delete the "id" owner0.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
