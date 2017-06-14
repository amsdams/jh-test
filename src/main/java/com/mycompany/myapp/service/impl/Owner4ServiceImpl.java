package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.Owner4Service;
import com.mycompany.myapp.domain.Owner4;
import com.mycompany.myapp.repository.Owner4Repository;
import com.mycompany.myapp.repository.search.Owner4SearchRepository;
import com.mycompany.myapp.service.dto.Owner4DTO;
import com.mycompany.myapp.service.mapper.Owner4Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Owner4.
 */
@Service
@Transactional
public class Owner4ServiceImpl implements Owner4Service{

    private final Logger log = LoggerFactory.getLogger(Owner4ServiceImpl.class);

    private final Owner4Repository owner4Repository;

    private final Owner4Mapper owner4Mapper;

    private final Owner4SearchRepository owner4SearchRepository;

    public Owner4ServiceImpl(Owner4Repository owner4Repository, Owner4Mapper owner4Mapper, Owner4SearchRepository owner4SearchRepository) {
        this.owner4Repository = owner4Repository;
        this.owner4Mapper = owner4Mapper;
        this.owner4SearchRepository = owner4SearchRepository;
    }

    /**
     * Save a owner4.
     *
     * @param owner4DTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Owner4DTO save(Owner4DTO owner4DTO) {
        log.debug("Request to save Owner4 : {}", owner4DTO);
        Owner4 owner4 = owner4Mapper.toEntity(owner4DTO);
        owner4 = owner4Repository.save(owner4);
        Owner4DTO result = owner4Mapper.toDto(owner4);
        owner4SearchRepository.save(owner4);
        return result;
    }

    /**
     *  Get all the owner4S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Owner4DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Owner4S");
        return owner4Repository.findAll(pageable)
            .map(owner4Mapper::toDto);
    }

    /**
     *  Get one owner4 by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Owner4DTO findOne(Long id) {
        log.debug("Request to get Owner4 : {}", id);
        Owner4 owner4 = owner4Repository.findOne(id);
        return owner4Mapper.toDto(owner4);
    }

    /**
     *  Delete the  owner4 by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Owner4 : {}", id);
        owner4Repository.delete(id);
        owner4SearchRepository.delete(id);
    }

    /**
     * Search for the owner4 corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Owner4DTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Owner4S for query {}", query);
        Page<Owner4> result = owner4SearchRepository.search(queryStringQuery(query), pageable);
        return result.map(owner4Mapper::toDto);
    }
}
