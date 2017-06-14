package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.Car2Service;
import com.mycompany.myapp.domain.Car2;
import com.mycompany.myapp.repository.Car2Repository;
import com.mycompany.myapp.repository.search.Car2SearchRepository;
import com.mycompany.myapp.service.dto.Car2DTO;
import com.mycompany.myapp.service.mapper.Car2Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Car2.
 */
@Service
@Transactional
public class Car2ServiceImpl implements Car2Service{

    private final Logger log = LoggerFactory.getLogger(Car2ServiceImpl.class);

    private final Car2Repository car2Repository;

    private final Car2Mapper car2Mapper;

    private final Car2SearchRepository car2SearchRepository;

    public Car2ServiceImpl(Car2Repository car2Repository, Car2Mapper car2Mapper, Car2SearchRepository car2SearchRepository) {
        this.car2Repository = car2Repository;
        this.car2Mapper = car2Mapper;
        this.car2SearchRepository = car2SearchRepository;
    }

    /**
     * Save a car2.
     *
     * @param car2DTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Car2DTO save(Car2DTO car2DTO) {
        log.debug("Request to save Car2 : {}", car2DTO);
        Car2 car2 = car2Mapper.toEntity(car2DTO);
        car2 = car2Repository.save(car2);
        Car2DTO result = car2Mapper.toDto(car2);
        car2SearchRepository.save(car2);
        return result;
    }

    /**
     *  Get all the car2S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Car2DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Car2S");
        return car2Repository.findAll(pageable)
            .map(car2Mapper::toDto);
    }

    /**
     *  Get one car2 by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Car2DTO findOne(Long id) {
        log.debug("Request to get Car2 : {}", id);
        Car2 car2 = car2Repository.findOne(id);
        return car2Mapper.toDto(car2);
    }

    /**
     *  Delete the  car2 by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Car2 : {}", id);
        car2Repository.delete(id);
        car2SearchRepository.delete(id);
    }

    /**
     * Search for the car2 corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Car2DTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Car2S for query {}", query);
        Page<Car2> result = car2SearchRepository.search(queryStringQuery(query), pageable);
        return result.map(car2Mapper::toDto);
    }
}
