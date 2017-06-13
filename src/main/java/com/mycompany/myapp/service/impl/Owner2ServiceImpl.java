package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.Owner2Service;
import com.mycompany.myapp.domain.Owner2;
import com.mycompany.myapp.repository.Owner2Repository;
import com.mycompany.myapp.service.dto.Owner2DTO;
import com.mycompany.myapp.service.mapper.Owner2Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Owner2.
 */
@Service
@Transactional
public class Owner2ServiceImpl implements Owner2Service{

    private final Logger log = LoggerFactory.getLogger(Owner2ServiceImpl.class);

    private final Owner2Repository owner2Repository;

    private final Owner2Mapper owner2Mapper;

    public Owner2ServiceImpl(Owner2Repository owner2Repository, Owner2Mapper owner2Mapper) {
        this.owner2Repository = owner2Repository;
        this.owner2Mapper = owner2Mapper;
    }

    /**
     * Save a owner2.
     *
     * @param owner2DTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Owner2DTO save(Owner2DTO owner2DTO) {
        log.debug("Request to save Owner2 : {}", owner2DTO);
        Owner2 owner2 = owner2Mapper.toEntity(owner2DTO);
        owner2 = owner2Repository.save(owner2);
        return owner2Mapper.toDto(owner2);
    }

    /**
     *  Get all the owner2S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Owner2DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Owner2S");
        return owner2Repository.findAll(pageable)
            .map(owner2Mapper::toDto);
    }

    /**
     *  Get one owner2 by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Owner2DTO findOne(Long id) {
        log.debug("Request to get Owner2 : {}", id);
        Owner2 owner2 = owner2Repository.findOne(id);
        return owner2Mapper.toDto(owner2);
    }

    /**
     *  Delete the  owner2 by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Owner2 : {}", id);
        owner2Repository.delete(id);
    }
}
