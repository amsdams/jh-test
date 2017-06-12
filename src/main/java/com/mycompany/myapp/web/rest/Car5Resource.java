package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Car5;

import com.mycompany.myapp.repository.Car5Repository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Car5.
 */
@RestController
@RequestMapping("/api")
public class Car5Resource {

    private final Logger log = LoggerFactory.getLogger(Car5Resource.class);

    private static final String ENTITY_NAME = "car5";

    private final Car5Repository car5Repository;

    public Car5Resource(Car5Repository car5Repository) {
        this.car5Repository = car5Repository;
    }

    /**
     * POST  /car-5-s : Create a new car5.
     *
     * @param car5 the car5 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new car5, or with status 400 (Bad Request) if the car5 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/car-5-s")
    @Timed
    public ResponseEntity<Car5> createCar5(@RequestBody Car5 car5) throws URISyntaxException {
        log.debug("REST request to save Car5 : {}", car5);
        if (car5.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new car5 cannot already have an ID")).body(null);
        }
        Car5 result = car5Repository.save(car5);
        return ResponseEntity.created(new URI("/api/car-5-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /car-5-s : Updates an existing car5.
     *
     * @param car5 the car5 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated car5,
     * or with status 400 (Bad Request) if the car5 is not valid,
     * or with status 500 (Internal Server Error) if the car5 couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/car-5-s")
    @Timed
    public ResponseEntity<Car5> updateCar5(@RequestBody Car5 car5) throws URISyntaxException {
        log.debug("REST request to update Car5 : {}", car5);
        if (car5.getId() == null) {
            return createCar5(car5);
        }
        Car5 result = car5Repository.save(car5);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, car5.getId().toString()))
            .body(result);
    }

    /**
     * GET  /car-5-s : get all the car5S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of car5S in body
     */
    @GetMapping("/car-5-s")
    @Timed
    public ResponseEntity<List<Car5>> getAllCar5S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Car5S");
        Page<Car5> page = car5Repository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/car-5-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /car-5-s/:id : get the "id" car5.
     *
     * @param id the id of the car5 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the car5, or with status 404 (Not Found)
     */
    @GetMapping("/car-5-s/{id}")
    @Timed
    public ResponseEntity<Car5> getCar5(@PathVariable Long id) {
        log.debug("REST request to get Car5 : {}", id);
        Car5 car5 = car5Repository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(car5));
    }

    /**
     * DELETE  /car-5-s/:id : delete the "id" car5.
     *
     * @param id the id of the car5 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/car-5-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteCar5(@PathVariable Long id) {
        log.debug("REST request to delete Car5 : {}", id);
        car5Repository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
