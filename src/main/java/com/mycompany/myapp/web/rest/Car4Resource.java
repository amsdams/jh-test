package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Car4;

import com.mycompany.myapp.repository.Car4Repository;
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
 * REST controller for managing Car4.
 */
@RestController
@RequestMapping("/api")
public class Car4Resource {

    private final Logger log = LoggerFactory.getLogger(Car4Resource.class);

    private static final String ENTITY_NAME = "car4";

    private final Car4Repository car4Repository;

    public Car4Resource(Car4Repository car4Repository) {
        this.car4Repository = car4Repository;
    }

    /**
     * POST  /car-4-s : Create a new car4.
     *
     * @param car4 the car4 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new car4, or with status 400 (Bad Request) if the car4 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/car-4-s")
    @Timed
    public ResponseEntity<Car4> createCar4(@RequestBody Car4 car4) throws URISyntaxException {
        log.debug("REST request to save Car4 : {}", car4);
        if (car4.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new car4 cannot already have an ID")).body(null);
        }
        Car4 result = car4Repository.save(car4);
        return ResponseEntity.created(new URI("/api/car-4-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /car-4-s : Updates an existing car4.
     *
     * @param car4 the car4 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated car4,
     * or with status 400 (Bad Request) if the car4 is not valid,
     * or with status 500 (Internal Server Error) if the car4 couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/car-4-s")
    @Timed
    public ResponseEntity<Car4> updateCar4(@RequestBody Car4 car4) throws URISyntaxException {
        log.debug("REST request to update Car4 : {}", car4);
        if (car4.getId() == null) {
            return createCar4(car4);
        }
        Car4 result = car4Repository.save(car4);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, car4.getId().toString()))
            .body(result);
    }

    /**
     * GET  /car-4-s : get all the car4S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of car4S in body
     */
    @GetMapping("/car-4-s")
    @Timed
    public ResponseEntity<List<Car4>> getAllCar4S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Car4S");
        Page<Car4> page = car4Repository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/car-4-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /car-4-s/:id : get the "id" car4.
     *
     * @param id the id of the car4 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the car4, or with status 404 (Not Found)
     */
    @GetMapping("/car-4-s/{id}")
    @Timed
    public ResponseEntity<Car4> getCar4(@PathVariable Long id) {
        log.debug("REST request to get Car4 : {}", id);
        Car4 car4 = car4Repository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(car4));
    }

    /**
     * DELETE  /car-4-s/:id : delete the "id" car4.
     *
     * @param id the id of the car4 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/car-4-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteCar4(@PathVariable Long id) {
        log.debug("REST request to delete Car4 : {}", id);
        car4Repository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
