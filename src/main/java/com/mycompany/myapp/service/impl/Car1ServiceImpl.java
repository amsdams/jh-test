package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.Car1Service;
import com.mycompany.myapp.domain.Car1;
import com.mycompany.myapp.repository.Car1Repository;
import com.mycompany.myapp.service.dto.Car1DTO;
import com.mycompany.myapp.service.mapper.Car1Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Car1.
 */
@Service
@Transactional
public class Car1ServiceImpl implements Car1Service{

    private final Logger log = LoggerFactory.getLogger(Car1ServiceImpl.class);

    private final Car1Repository car1Repository;

    private final Car1Mapper car1Mapper;

    public Car1ServiceImpl(Car1Repository car1Repository, Car1Mapper car1Mapper) {
        this.car1Repository = car1Repository;
        this.car1Mapper = car1Mapper;
    }

    /**
     * Save a car1.
     *
     * @param car1DTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Car1DTO save(Car1DTO car1DTO) {
        log.debug("Request to save Car1 : {}", car1DTO);
        Car1 car1 = car1Mapper.toEntity(car1DTO);
        car1 = car1Repository.save(car1);
        return car1Mapper.toDto(car1);
    }

    /**
     *  Get all the car1S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Car1DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Car1S");
        return car1Repository.findAll(pageable)
            .map(car1Mapper::toDto);
    }

    /**
     *  Get one car1 by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Car1DTO findOne(Long id) {
        log.debug("Request to get Car1 : {}", id);
        Car1 car1 = car1Repository.findOne(id);
        return car1Mapper.toDto(car1);
    }

    /**
     *  Delete the  car1 by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Car1 : {}", id);
        car1Repository.delete(id);
    }
}
