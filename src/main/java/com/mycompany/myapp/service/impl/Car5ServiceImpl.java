package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.Car5Service;
import com.mycompany.myapp.domain.Car5;
import com.mycompany.myapp.repository.Car5Repository;
import com.mycompany.myapp.repository.search.Car5SearchRepository;
import com.mycompany.myapp.service.dto.Car5DTO;
import com.mycompany.myapp.service.mapper.Car5Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Car5.
 */
@Service
@Transactional
public class Car5ServiceImpl implements Car5Service{

    private final Logger log = LoggerFactory.getLogger(Car5ServiceImpl.class);

    private final Car5Repository car5Repository;

    private final Car5Mapper car5Mapper;

    private final Car5SearchRepository car5SearchRepository;

    public Car5ServiceImpl(Car5Repository car5Repository, Car5Mapper car5Mapper, Car5SearchRepository car5SearchRepository) {
        this.car5Repository = car5Repository;
        this.car5Mapper = car5Mapper;
        this.car5SearchRepository = car5SearchRepository;
    }

    /**
     * Save a car5.
     *
     * @param car5DTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Car5DTO save(Car5DTO car5DTO) {
        log.debug("Request to save Car5 : {}", car5DTO);
        Car5 car5 = car5Mapper.toEntity(car5DTO);
        car5 = car5Repository.save(car5);
        Car5DTO result = car5Mapper.toDto(car5);
        car5SearchRepository.save(car5);
        return result;
    }

    /**
     *  Get all the car5S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Car5DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Car5S");
        return car5Repository.findAll(pageable)
            .map(car5Mapper::toDto);
    }

    /**
     *  Get one car5 by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Car5DTO findOne(Long id) {
        log.debug("Request to get Car5 : {}", id);
        Car5 car5 = car5Repository.findOne(id);
        return car5Mapper.toDto(car5);
    }

    /**
     *  Delete the  car5 by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Car5 : {}", id);
        car5Repository.delete(id);
        car5SearchRepository.delete(id);
    }

    /**
     * Search for the car5 corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Car5DTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Car5S for query {}", query);
        Page<Car5> result = car5SearchRepository.search(queryStringQuery(query), pageable);
        return result.map(car5Mapper::toDto);
    }
}
