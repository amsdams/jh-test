package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Owner5DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Owner5.
 */
public interface Owner5Service {

    /**
     * Save a owner5.
     *
     * @param owner5DTO the entity to save
     * @return the persisted entity
     */
    Owner5DTO save(Owner5DTO owner5DTO);

    /**
     *  Get all the owner5S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Owner5DTO> findAll(Pageable pageable);

    /**
     *  Get the "id" owner5.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Owner5DTO findOne(Long id);

    /**
     *  Delete the "id" owner5.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
