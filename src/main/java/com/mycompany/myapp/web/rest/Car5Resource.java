package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.Car5Service;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.Car5DTO;
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
 * REST controller for managing Car5.
 */
@RestController
@RequestMapping("/api")
public class Car5Resource {

    private final Logger log = LoggerFactory.getLogger(Car5Resource.class);

    private static final String ENTITY_NAME = "car5";

    private final Car5Service car5Service;

    public Car5Resource(Car5Service car5Service) {
        this.car5Service = car5Service;
    }

    /**
     * POST  /car-5-s : Create a new car5.
     *
     * @param car5DTO the car5DTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new car5DTO, or with status 400 (Bad Request) if the car5 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/car-5-s")
    @Timed
    public ResponseEntity<Car5DTO> createCar5(@Valid @RequestBody Car5DTO car5DTO) throws URISyntaxException {
        log.debug("REST request to save Car5 : {}", car5DTO);
        if (car5DTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new car5 cannot already have an ID")).body(null);
        }
        Car5DTO result = car5Service.save(car5DTO);
        return ResponseEntity.created(new URI("/api/car-5-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /car-5-s : Updates an existing car5.
     *
     * @param car5DTO the car5DTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated car5DTO,
     * or with status 400 (Bad Request) if the car5DTO is not valid,
     * or with status 500 (Internal Server Error) if the car5DTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/car-5-s")
    @Timed
    public ResponseEntity<Car5DTO> updateCar5(@Valid @RequestBody Car5DTO car5DTO) throws URISyntaxException {
        log.debug("REST request to update Car5 : {}", car5DTO);
        if (car5DTO.getId() == null) {
            return createCar5(car5DTO);
        }
        Car5DTO result = car5Service.save(car5DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, car5DTO.getId().toString()))
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
    public ResponseEntity<List<Car5DTO>> getAllCar5S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Car5S");
        Page<Car5DTO> page = car5Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/car-5-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /car-5-s/:id : get the "id" car5.
     *
     * @param id the id of the car5DTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the car5DTO, or with status 404 (Not Found)
     */
    @GetMapping("/car-5-s/{id}")
    @Timed
    public ResponseEntity<Car5DTO> getCar5(@PathVariable Long id) {
        log.debug("REST request to get Car5 : {}", id);
        Car5DTO car5DTO = car5Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(car5DTO));
    }

    /**
     * DELETE  /car-5-s/:id : delete the "id" car5.
     *
     * @param id the id of the car5DTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/car-5-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteCar5(@PathVariable Long id) {
        log.debug("REST request to delete Car5 : {}", id);
        car5Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/car-5-s?query=:query : search for the car5 corresponding
     * to the query.
     *
     * @param query the query of the car5 search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/car-5-s")
    @Timed
    public ResponseEntity<List<Car5DTO>> searchCar5S(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Car5S for query {}", query);
        Page<Car5DTO> page = car5Service.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/car-5-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
