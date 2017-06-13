package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.Owner1Service;
import com.mycompany.myapp.domain.Owner1;
import com.mycompany.myapp.repository.Owner1Repository;
import com.mycompany.myapp.service.dto.Owner1DTO;
import com.mycompany.myapp.service.mapper.Owner1Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Owner1.
 */
@Service
@Transactional
public class Owner1ServiceImpl implements Owner1Service{

    private final Logger log = LoggerFactory.getLogger(Owner1ServiceImpl.class);

    private final Owner1Repository owner1Repository;

    private final Owner1Mapper owner1Mapper;

    public Owner1ServiceImpl(Owner1Repository owner1Repository, Owner1Mapper owner1Mapper) {
        this.owner1Repository = owner1Repository;
        this.owner1Mapper = owner1Mapper;
    }

    /**
     * Save a owner1.
     *
     * @param owner1DTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Owner1DTO save(Owner1DTO owner1DTO) {
        log.debug("Request to save Owner1 : {}", owner1DTO);
        Owner1 owner1 = owner1Mapper.toEntity(owner1DTO);
        owner1 = owner1Repository.save(owner1);
        return owner1Mapper.toDto(owner1);
    }

    /**
     *  Get all the owner1S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Owner1DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Owner1S");
        return owner1Repository.findAll(pageable)
            .map(owner1Mapper::toDto);
    }

    /**
     *  Get one owner1 by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Owner1DTO findOne(Long id) {
        log.debug("Request to get Owner1 : {}", id);
        Owner1 owner1 = owner1Repository.findOne(id);
        return owner1Mapper.toDto(owner1);
    }

    /**
     *  Delete the  owner1 by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Owner1 : {}", id);
        owner1Repository.delete(id);
    }
}
