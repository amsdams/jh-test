package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.Car2Service;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.Car2DTO;
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

/**
 * REST controller for managing Car2.
 */
@RestController
@RequestMapping("/api")
public class Car2Resource {

    private final Logger log = LoggerFactory.getLogger(Car2Resource.class);

    private static final String ENTITY_NAME = "car2";

    private final Car2Service car2Service;

    public Car2Resource(Car2Service car2Service) {
        this.car2Service = car2Service;
    }

    /**
     * POST  /car-2-s : Create a new car2.
     *
     * @param car2DTO the car2DTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new car2DTO, or with status 400 (Bad Request) if the car2 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/car-2-s")
    @Timed
    public ResponseEntity<Car2DTO> createCar2(@Valid @RequestBody Car2DTO car2DTO) throws URISyntaxException {
        log.debug("REST request to save Car2 : {}", car2DTO);
        if (car2DTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new car2 cannot already have an ID")).body(null);
        }
        Car2DTO result = car2Service.save(car2DTO);
        return ResponseEntity.created(new URI("/api/car-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /car-2-s : Updates an existing car2.
     *
     * @param car2DTO the car2DTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated car2DTO,
     * or with status 400 (Bad Request) if the car2DTO is not valid,
     * or with status 500 (Internal Server Error) if the car2DTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/car-2-s")
    @Timed
    public ResponseEntity<Car2DTO> updateCar2(@Valid @RequestBody Car2DTO car2DTO) throws URISyntaxException {
        log.debug("REST request to update Car2 : {}", car2DTO);
        if (car2DTO.getId() == null) {
            return createCar2(car2DTO);
        }
        Car2DTO result = car2Service.save(car2DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, car2DTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /car-2-s : get all the car2S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of car2S in body
     */
    @GetMapping("/car-2-s")
    @Timed
    public ResponseEntity<List<Car2DTO>> getAllCar2S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Car2S");
        Page<Car2DTO> page = car2Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/car-2-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /car-2-s/:id : get the "id" car2.
     *
     * @param id the id of the car2DTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the car2DTO, or with status 404 (Not Found)
     */
    @GetMapping("/car-2-s/{id}")
    @Timed
    public ResponseEntity<Car2DTO> getCar2(@PathVariable Long id) {
        log.debug("REST request to get Car2 : {}", id);
        Car2DTO car2DTO = car2Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(car2DTO));
    }

    /**
     * DELETE  /car-2-s/:id : delete the "id" car2.
     *
     * @param id the id of the car2DTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/car-2-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteCar2(@PathVariable Long id) {
        log.debug("REST request to delete Car2 : {}", id);
        car2Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
