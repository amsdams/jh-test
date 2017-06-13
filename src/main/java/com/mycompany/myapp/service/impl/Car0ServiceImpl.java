package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.Car0Service;
import com.mycompany.myapp.domain.Car0;
import com.mycompany.myapp.repository.Car0Repository;
import com.mycompany.myapp.service.dto.Car0DTO;
import com.mycompany.myapp.service.mapper.Car0Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Car0.
 */
@Service
@Transactional
public class Car0ServiceImpl implements Car0Service{

    private final Logger log = LoggerFactory.getLogger(Car0ServiceImpl.class);

    private final Car0Repository car0Repository;

    private final Car0Mapper car0Mapper;

    public Car0ServiceImpl(Car0Repository car0Repository, Car0Mapper car0Mapper) {
        this.car0Repository = car0Repository;
        this.car0Mapper = car0Mapper;
    }

    /**
     * Save a car0.
     *
     * @param car0DTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Car0DTO save(Car0DTO car0DTO) {
        log.debug("Request to save Car0 : {}", car0DTO);
        Car0 car0 = car0Mapper.toEntity(car0DTO);
        car0 = car0Repository.save(car0);
        return car0Mapper.toDto(car0);
    }

    /**
     *  Get all the car0S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Car0DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Car0S");
        return car0Repository.findAll(pageable)
            .map(car0Mapper::toDto);
    }

    /**
     *  Get one car0 by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Car0DTO findOne(Long id) {
        log.debug("Request to get Car0 : {}", id);
        Car0 car0 = car0Repository.findOne(id);
        return car0Mapper.toDto(car0);
    }

    /**
     *  Delete the  car0 by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Car0 : {}", id);
        car0Repository.delete(id);
    }
}
