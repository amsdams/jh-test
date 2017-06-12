package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Owner3;

import com.mycompany.myapp.repository.Owner3Repository;
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
 * REST controller for managing Owner3.
 */
@RestController
@RequestMapping("/api")
public class Owner3Resource {

    private final Logger log = LoggerFactory.getLogger(Owner3Resource.class);

    private static final String ENTITY_NAME = "owner3";

    private final Owner3Repository owner3Repository;

    public Owner3Resource(Owner3Repository owner3Repository) {
        this.owner3Repository = owner3Repository;
    }

    /**
     * POST  /owner-3-s : Create a new owner3.
     *
     * @param owner3 the owner3 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new owner3, or with status 400 (Bad Request) if the owner3 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/owner-3-s")
    @Timed
    public ResponseEntity<Owner3> createOwner3(@RequestBody Owner3 owner3) throws URISyntaxException {
        log.debug("REST request to save Owner3 : {}", owner3);
        if (owner3.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new owner3 cannot already have an ID")).body(null);
        }
        Owner3 result = owner3Repository.save(owner3);
        return ResponseEntity.created(new URI("/api/owner-3-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /owner-3-s : Updates an existing owner3.
     *
     * @param owner3 the owner3 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated owner3,
     * or with status 400 (Bad Request) if the owner3 is not valid,
     * or with status 500 (Internal Server Error) if the owner3 couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/owner-3-s")
    @Timed
    public ResponseEntity<Owner3> updateOwner3(@RequestBody Owner3 owner3) throws URISyntaxException {
        log.debug("REST request to update Owner3 : {}", owner3);
        if (owner3.getId() == null) {
            return createOwner3(owner3);
        }
        Owner3 result = owner3Repository.save(owner3);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, owner3.getId().toString()))
            .body(result);
    }

    /**
     * GET  /owner-3-s : get all the owner3S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of owner3S in body
     */
    @GetMapping("/owner-3-s")
    @Timed
    public ResponseEntity<List<Owner3>> getAllOwner3S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Owner3S");
        Page<Owner3> page = owner3Repository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/owner-3-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /owner-3-s/:id : get the "id" owner3.
     *
     * @param id the id of the owner3 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the owner3, or with status 404 (Not Found)
     */
    @GetMapping("/owner-3-s/{id}")
    @Timed
    public ResponseEntity<Owner3> getOwner3(@PathVariable Long id) {
        log.debug("REST request to get Owner3 : {}", id);
        Owner3 owner3 = owner3Repository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(owner3));
    }

    /**
     * DELETE  /owner-3-s/:id : delete the "id" owner3.
     *
     * @param id the id of the owner3 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/owner-3-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteOwner3(@PathVariable Long id) {
        log.debug("REST request to delete Owner3 : {}", id);
        owner3Repository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
