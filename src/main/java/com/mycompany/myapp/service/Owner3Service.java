package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.Owner3DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Owner3.
 */
public interface Owner3Service {

    /**
     * Save a owner3.
     *
     * @param owner3DTO the entity to save
     * @return the persisted entity
     */
    Owner3DTO save(Owner3DTO owner3DTO);

    /**
     *  Get all the owner3S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Owner3DTO> findAll(Pageable pageable);

    /**
     *  Get the "id" owner3.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Owner3DTO findOne(Long id);

    /**
     *  Delete the "id" owner3.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the owner3 corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Owner3DTO> search(String query, Pageable pageable);
}
