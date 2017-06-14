package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.Car3Service;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.Car3DTO;
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
 * REST controller for managing Car3.
 */
@RestController
@RequestMapping("/api")
public class Car3Resource {

    private final Logger log = LoggerFactory.getLogger(Car3Resource.class);

    private static final String ENTITY_NAME = "car3";

    private final Car3Service car3Service;

    public Car3Resource(Car3Service car3Service) {
        this.car3Service = car3Service;
    }

    /**
     * POST  /car-3-s : Create a new car3.
     *
     * @param car3DTO the car3DTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new car3DTO, or with status 400 (Bad Request) if the car3 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/car-3-s")
    @Timed
    public ResponseEntity<Car3DTO> createCar3(@Valid @RequestBody Car3DTO car3DTO) throws URISyntaxException {
        log.debug("REST request to save Car3 : {}", car3DTO);
        if (car3DTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new car3 cannot already have an ID")).body(null);
        }
        Car3DTO result = car3Service.save(car3DTO);
        return ResponseEntity.created(new URI("/api/car-3-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /car-3-s : Updates an existing car3.
     *
     * @param car3DTO the car3DTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated car3DTO,
     * or with status 400 (Bad Request) if the car3DTO is not valid,
     * or with status 500 (Internal Server Error) if the car3DTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/car-3-s")
    @Timed
    public ResponseEntity<Car3DTO> updateCar3(@Valid @RequestBody Car3DTO car3DTO) throws URISyntaxException {
        log.debug("REST request to update Car3 : {}", car3DTO);
        if (car3DTO.getId() == null) {
            return createCar3(car3DTO);
        }
        Car3DTO result = car3Service.save(car3DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, car3DTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /car-3-s : get all the car3S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of car3S in body
     */
    @GetMapping("/car-3-s")
    @Timed
    public ResponseEntity<List<Car3DTO>> getAllCar3S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Car3S");
        Page<Car3DTO> page = car3Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/car-3-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /car-3-s/:id : get the "id" car3.
     *
     * @param id the id of the car3DTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the car3DTO, or with status 404 (Not Found)
     */
    @GetMapping("/car-3-s/{id}")
    @Timed
    public ResponseEntity<Car3DTO> getCar3(@PathVariable Long id) {
        log.debug("REST request to get Car3 : {}", id);
        Car3DTO car3DTO = car3Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(car3DTO));
    }

    /**
     * DELETE  /car-3-s/:id : delete the "id" car3.
     *
     * @param id the id of the car3DTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/car-3-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteCar3(@PathVariable Long id) {
        log.debug("REST request to delete Car3 : {}", id);
        car3Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/car-3-s?query=:query : search for the car3 corresponding
     * to the query.
     *
     * @param query the query of the car3 search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/car-3-s")
    @Timed
    public ResponseEntity<List<Car3DTO>> searchCar3S(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Car3S for query {}", query);
        Page<Car3DTO> page = car3Service.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/car-3-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
