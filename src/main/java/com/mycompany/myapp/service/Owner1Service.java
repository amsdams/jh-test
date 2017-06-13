package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Owner1DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Owner1.
 */
public interface Owner1Service {

    /**
     * Save a owner1.
     *
     * @param owner1DTO the entity to save
     * @return the persisted entity
     */
    Owner1DTO save(Owner1DTO owner1DTO);

    /**
     *  Get all the owner1S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Owner1DTO> findAll(Pageable pageable);

    /**
     *  Get the "id" owner1.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Owner1DTO findOne(Long id);

    /**
     *  Delete the "id" owner1.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
