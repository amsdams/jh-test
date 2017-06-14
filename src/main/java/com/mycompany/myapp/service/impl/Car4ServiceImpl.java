package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.Car4Service;
import com.mycompany.myapp.domain.Car4;
import com.mycompany.myapp.repository.Car4Repository;
import com.mycompany.myapp.repository.search.Car4SearchRepository;
import com.mycompany.myapp.service.dto.Car4DTO;
import com.mycompany.myapp.service.mapper.Car4Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Car4.
 */
@Service
@Transactional
public class Car4ServiceImpl implements Car4Service{

    private final Logger log = LoggerFactory.getLogger(Car4ServiceImpl.class);

    private final Car4Repository car4Repository;

    private final Car4Mapper car4Mapper;

    private final Car4SearchRepository car4SearchRepository;

    public Car4ServiceImpl(Car4Repository car4Repository, Car4Mapper car4Mapper, Car4SearchRepository car4SearchRepository) {
        this.car4Repository = car4Repository;
        this.car4Mapper = car4Mapper;
        this.car4SearchRepository = car4SearchRepository;
    }

    /**
     * Save a car4.
     *
     * @param car4DTO the entity to save
     * @return the persisted entity
     */
    @Override
    public Car4DTO save(Car4DTO car4DTO) {
        log.debug("Request to save Car4 : {}", car4DTO);
        Car4 car4 = car4Mapper.toEntity(car4DTO);
        car4 = car4Repository.save(car4);
        Car4DTO result = car4Mapper.toDto(car4);
        car4SearchRepository.save(car4);
        return result;
    }

    /**
     *  Get all the car4S.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Car4DTO> findAll(Pageable pageable) {
        log.debug("Request to get all Car4S");
        return car4Repository.findAll(pageable)
            .map(car4Mapper::toDto);
    }

    /**
     *  Get one car4 by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Car4DTO findOne(Long id) {
        log.debug("Request to get Car4 : {}", id);
        Car4 car4 = car4Repository.findOne(id);
        return car4Mapper.toDto(car4);
    }

    /**
     *  Delete the  car4 by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Car4 : {}", id);
        car4Repository.delete(id);
        car4SearchRepository.delete(id);
    }

    /**
     * Search for the car4 corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Car4DTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Car4S for query {}", query);
        Page<Car4> result = car4SearchRepository.search(queryStringQuery(query), pageable);
        return result.map(car4Mapper::toDto);
    }
}
