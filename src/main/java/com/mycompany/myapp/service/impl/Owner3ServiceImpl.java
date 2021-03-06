package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.Owner3Service;
import com.mycompany.myapp.domain.Owner3;
import com.mycompany.myapp.repository.Owner3Repository;
import com.mycompany.myapp.repository.search.Owner3SearchRepository;
import com.mycompany.myapp.service.dto.Owner3DTO;
import com.mycompany.myapp.service.mapper.Owner3Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Owner3.
 */
@Service
@Transactional
public class Owner3ServiceImpl implements Owner3Service{

    private final Logger log = LoggerFactory.getLogger(Owner3ServiceImpl.class);

    private final Owner3Repository owner3Repository;

    private final Owner3Mapper owner3Mapper;

    private final Owner3SearchRepository owner3SearchRepository;

    public Owner3ServiceImpl(Owner3Repository owner3Repository, Owner3Mapper owner3Mapper, Owner3SearchRepository owner3SearchRepository) {
        this.owner3Repository = owner3Repository;
        this.owner3Mapper = owner3Mapper;
        this.owner3SearchRepository = owner3SearchRepository;
    }

    /**
     * Save a owner3.
     *
     * @param owner3DTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Owner3DTO save(Owner3DTO owner3DTO) {
        log.debug("Request to save Owner3 : {}", owner3DTO);
        Owner3 owner3 = owner3Mapper.toEntity(owner3DTO);
        owner3 = owner3Repository.save(owner3);
        Owner3DTO result = owner3Mapper.toDto(owner3);
        owner3SearchRepository.save(owner3);
        return result;
    }

    /**
     *  Get all the owner3S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Owner3DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Owner3S");
        return owner3Repository.findAll(pageable)
            .map(owner3Mapper::toDto);
    }

    /**
     *  Get one owner3 by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Owner3DTO findOne(Long id) {
        log.debug("Request to get Owner3 : {}", id);
        Owner3 owner3 = owner3Repository.findOne(id);
        return owner3Mapper.toDto(owner3);
    }

    /**
     *  Delete the  owner3 by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Owner3 : {}", id);
        owner3Repository.delete(id);
        owner3SearchRepository.delete(id);
    }

    /**
     * Search for the owner3 corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Owner3DTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Owner3S for query {}", query);
        Page<Owner3> result = owner3SearchRepository.search(queryStringQuery(query), pageable);
        return result.map(owner3Mapper::toDto);
    }
}
