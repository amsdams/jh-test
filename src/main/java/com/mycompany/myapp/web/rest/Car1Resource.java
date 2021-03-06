package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.Car1Service;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.Car1DTO;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Car1.
 */
@RestController
@RequestMapping("/api")
public class Car1Resource {

    private final Logger log = LoggerFactory.getLogger(Car1Resource.class);

    private static final String ENTITY_NAME = "car1";

    private final Car1Service car1Service;

    public Car1Resource(Car1Service car1Service) {
        this.car1Service = car1Service;
    }

    /**
     * POST  /car-1-s : Create a new car1.
     *
     * @param car1DTO the car1DTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new car1DTO, or with status 400 (Bad Request) if the car1 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/car-1-s")
    @Timed
    public ResponseEntity<Car1DTO> createCar1(@Valid @RequestBody Car1DTO car1DTO) throws URISyntaxException {
        log.debug("REST request to save Car1 : {}", car1DTO);
        if (car1DTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new car1 cannot already have an ID")).body(null);
        }
        Car1DTO result = car1Service.save(car1DTO);
        return ResponseEntity.created(new URI("/api/car-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /car-1-s : Updates an existing car1.
     *
     * @param car1DTO the car1DTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated car1DTO,
     * or with status 400 (Bad Request) if the car1DTO is not valid,
     * or with status 500 (Internal Server Error) if the car1DTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/car-1-s")
    @Timed
    public ResponseEntity<Car1DTO> updateCar1(@Valid @RequestBody Car1DTO car1DTO) throws URISyntaxException {
        log.debug("REST request to update Car1 : {}", car1DTO);
        if (car1DTO.getId() == null) {
            return createCar1(car1DTO);
        }
        Car1DTO result = car1Service.save(car1DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, car1DTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /car-1-s : get all the car1S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of car1S in body
     */
    @GetMapping("/car-1-s")
    @Timed
    public ResponseEntity<List<Car1DTO>> getAllCar1S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Car1S");
        Page<Car1DTO> page = car1Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/car-1-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /car-1-s/:id : get the "id" car1.
     *
     * @param id the id of the car1DTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the car1DTO, or with status 404 (Not Found)
     */
    @GetMapping("/car-1-s/{id}")
    @Timed
    public ResponseEntity<Car1DTO> getCar1(@PathVariable Long id) {
        log.debug("REST request to get Car1 : {}", id);
        Car1DTO car1DTO = car1Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(car1DTO));
    }

    /**
     * DELETE  /car-1-s/:id : delete the "id" car1.
     *
     * @param id the id of the car1DTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/car-1-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteCar1(@PathVariable Long id) {
        log.debug("REST request to delete Car1 : {}", id);
        car1Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/car-1-s?query=:query : search for the car1 corresponding
     * to the query.
     *
     * @param query the query of the car1 search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/car-1-s")
    @Timed
    public ResponseEntity<List<Car1DTO>> searchCar1S(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Car1S for query {}", query);
        Page<Car1DTO> page = car1Service.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/car-1-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
