package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.Owner5Service;
import com.mycompany.myapp.domain.Owner5;
import com.mycompany.myapp.repository.Owner5Repository;
import com.mycompany.myapp.repository.search.Owner5SearchRepository;
import com.mycompany.myapp.service.dto.Owner5DTO;
import com.mycompany.myapp.service.mapper.Owner5Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Owner5.
 */
@Service
@Transactional
public class Owner5ServiceImpl implements Owner5Service{

    private final Logger log = LoggerFactory.getLogger(Owner5ServiceImpl.class);

    private final Owner5Repository owner5Repository;

    private final Owner5Mapper owner5Mapper;

    private final Owner5SearchRepository owner5SearchRepository;

    public Owner5ServiceImpl(Owner5Repository owner5Repository, Owner5Mapper owner5Mapper, Owner5SearchRepository owner5SearchRepository) {
        this.owner5Repository = owner5Repository;
        this.owner5Mapper = owner5Mapper;
        this.owner5SearchRepository = owner5SearchRepository;
    }

    /**
     * Save a owner5.
     *
     * @param owner5DTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Owner5DTO save(Owner5DTO owner5DTO) {
        log.debug("Request to save Owner5 : {}", owner5DTO);
        Owner5 owner5 = owner5Mapper.toEntity(owner5DTO);
        owner5 = owner5Repository.save(owner5);
        Owner5DTO result = owner5Mapper.toDto(owner5);
        owner5SearchRepository.save(owner5);
        return result;
    }

    /**
     *  Get all the owner5S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Owner5DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Owner5S");
        return owner5Repository.findAll(pageable)
            .map(owner5Mapper::toDto);
    }

    /**
     *  Get one owner5 by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Owner5DTO findOne(Long id) {
        log.debug("Request to get Owner5 : {}", id);
        Owner5 owner5 = owner5Repository.findOneWithEagerRelationships(id);
        return owner5Mapper.toDto(owner5);
    }

    /**
     *  Delete the  owner5 by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Owner5 : {}", id);
        owner5Repository.delete(id);
        owner5SearchRepository.delete(id);
    }

    /**
     * Search for the owner5 corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Owner5DTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Owner5S for query {}", query);
        Page<Owner5> result = owner5SearchRepository.search(queryStringQuery(query), pageable);
        return result.map(owner5Mapper::toDto);
    }
}
