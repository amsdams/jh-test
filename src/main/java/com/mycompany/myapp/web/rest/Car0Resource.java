package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Car0;

import com.mycompany.myapp.repository.Car0Repository;
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
 * REST controller for managing Car0.
 */
@RestController
@RequestMapping("/api")
public class Car0Resource {

    private final Logger log = LoggerFactory.getLogger(Car0Resource.class);

    private static final String ENTITY_NAME = "car0";

    private final Car0Repository car0Repository;

    public Car0Resource(Car0Repository car0Repository) {
        this.car0Repository = car0Repository;
    }

    /**
     * POST  /car-0-s : Create a new car0.
     *
     * @param car0 the car0 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new car0, or with status 400 (Bad Request) if the car0 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/car-0-s")
    @Timed
    public ResponseEntity<Car0> createCar0(@RequestBody Car0 car0) throws URISyntaxException {
        log.debug("REST request to save Car0 : {}", car0);
        if (car0.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new car0 cannot already have an ID")).body(null);
        }
        Car0 result = car0Repository.save(car0);
        return ResponseEntity.created(new URI("/api/car-0-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /car-0-s : Updates an existing car0.
     *
     * @param car0 the car0 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated car0,
     * or with status 400 (Bad Request) if the car0 is not valid,
     * or with status 500 (Internal Server Error) if the car0 couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/car-0-s")
    @Timed
    public ResponseEntity<Car0> updateCar0(@RequestBody Car0 car0) throws URISyntaxException {
        log.debug("REST request to update Car0 : {}", car0);
        if (car0.getId() == null) {
            return createCar0(car0);
        }
        Car0 result = car0Repository.save(car0);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, car0.getId().toString()))
            .body(result);
    }

    /**
     * GET  /car-0-s : get all the car0S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of car0S in body
     */
    @GetMapping("/car-0-s")
    @Timed
    public ResponseEntity<List<Car0>> getAllCar0S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Car0S");
        Page<Car0> page = car0Repository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/car-0-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /car-0-s/:id : get the "id" car0.
     *
     * @param id the id of the car0 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the car0, or with status 404 (Not Found)
     */
    @GetMapping("/car-0-s/{id}")
    @Timed
    public ResponseEntity<Car0> getCar0(@PathVariable Long id) {
        log.debug("REST request to get Car0 : {}", id);
        Car0 car0 = car0Repository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(car0));
    }

    /**
     * DELETE  /car-0-s/:id : delete the "id" car0.
     *
     * @param id the id of the car0 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/car-0-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteCar0(@PathVariable Long id) {
        log.debug("REST request to delete Car0 : {}", id);
        car0Repository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
