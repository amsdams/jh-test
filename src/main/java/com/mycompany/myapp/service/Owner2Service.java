package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Owner2DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Owner2.
 */
public interface Owner2Service {

    /**
     * Save a owner2.
     *
     * @param owner2DTO the entity to save
     * @return the persisted entity
     */
    Owner2DTO save(Owner2DTO owner2DTO);

    /**
     *  Get all the owner2S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Owner2DTO> findAll(Pageable pageable);

    /**
     *  Get the "id" owner2.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Owner2DTO findOne(Long id);

    /**
     *  Delete the "id" owner2.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
