package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.Owner0Service;
import com.mycompany.myapp.domain.Owner0;
import com.mycompany.myapp.repository.Owner0Repository;
import com.mycompany.myapp.service.dto.Owner0DTO;
import com.mycompany.myapp.service.mapper.Owner0Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Owner0.
 */
@Service
@Transactional
public class Owner0ServiceImpl implements Owner0Service{

    private final Logger log = LoggerFactory.getLogger(Owner0ServiceImpl.class);

    private final Owner0Repository owner0Repository;

    private final Owner0Mapper owner0Mapper;

    public Owner0ServiceImpl(Owner0Repository owner0Repository, Owner0Mapper owner0Mapper) {
        this.owner0Repository = owner0Repository;
        this.owner0Mapper = owner0Mapper;
    }

    /**
     * Save a owner0.
     *
     * @param owner0DTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Owner0DTO save(Owner0DTO owner0DTO) {
        log.debug("Request to save Owner0 : {}", owner0DTO);
        Owner0 owner0 = owner0Mapper.toEntity(owner0DTO);
        owner0 = owner0Repository.save(owner0);
        return owner0Mapper.toDto(owner0);
    }

    /**
     *  Get all the owner0S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Owner0DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Owner0S");
        return owner0Repository.findAll(pageable)
            .map(owner0Mapper::toDto);
    }

    /**
     *  Get one owner0 by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Owner0DTO findOne(Long id) {
        log.debug("Request to get Owner0 : {}", id);
        Owner0 owner0 = owner0Repository.findOne(id);
        return owner0Mapper.toDto(owner0);
    }

    /**
     *  Delete the  owner0 by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Owner0 : {}", id);
        owner0Repository.delete(id);
    }
}
