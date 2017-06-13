package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.Car3Service;
import com.mycompany.myapp.domain.Car3;
import com.mycompany.myapp.repository.Car3Repository;
import com.mycompany.myapp.service.dto.Car3DTO;
import com.mycompany.myapp.service.mapper.Car3Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Car3.
 */
@Service
@Transactional
public class Car3ServiceImpl implements Car3Service{

    private final Logger log = LoggerFactory.getLogger(Car3ServiceImpl.class);

    private final Car3Repository car3Repository;

    private final Car3Mapper car3Mapper;

    public Car3ServiceImpl(Car3Repository car3Repository, Car3Mapper car3Mapper) {
        this.car3Repository = car3Repository;
        this.car3Mapper = car3Mapper;
    }

    /**
     * Save a car3.
     *
     * @param car3DTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Car3DTO save(Car3DTO car3DTO) {
        log.debug("Request to save Car3 : {}", car3DTO);
        Car3 car3 = car3Mapper.toEntity(car3DTO);
        car3 = car3Repository.save(car3);
        return car3Mapper.toDto(car3);
    }

    /**
     *  Get all the car3S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Car3DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Car3S");
        return car3Repository.findAll(pageable)
            .map(car3Mapper::toDto);
    }

    /**
     *  Get one car3 by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Car3DTO findOne(Long id) {
        log.debug("Request to get Car3 : {}", id);
        Car3 car3 = car3Repository.findOneWithEagerRelationships(id);
        return car3Mapper.toDto(car3);
    }

    /**
     *  Delete the  car3 by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Car3 : {}", id);
        car3Repository.delete(id);
    }
}
