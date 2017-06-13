package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Owner4DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Owner4.
 */
public interface Owner4Service {

    /**
     * Save a owner4.
     *
     * @param owner4DTO the entity to save
     * @return the persisted entity
     */
    Owner4DTO save(Owner4DTO owner4DTO);

    /**
     *  Get all the owner4S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Owner4DTO> findAll(Pageable pageable);

    /**
     *  Get the "id" owner4.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Owner4DTO findOne(Long id);

    /**
     *  Delete the "id" owner4.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
