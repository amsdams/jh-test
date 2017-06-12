package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Owner1;

import com.mycompany.myapp.repository.Owner1Repository;
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
 * REST controller for managing Owner1.
 */
@RestController
@RequestMapping("/api")
public class Owner1Resource {

    private final Logger log = LoggerFactory.getLogger(Owner1Resource.class);

    private static final String ENTITY_NAME = "owner1";

    private final Owner1Repository owner1Repository;

    public Owner1Resource(Owner1Repository owner1Repository) {
        this.owner1Repository = owner1Repository;
    }

    /**
     * POST  /owner-1-s : Create a new owner1.
     *
     * @param owner1 the owner1 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new owner1, or with status 400 (Bad Request) if the owner1 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/owner-1-s")
    @Timed
    public ResponseEntity<Owner1> createOwner1(@RequestBody Owner1 owner1) throws URISyntaxException {
        log.debug("REST request to save Owner1 : {}", owner1);
        if (owner1.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new owner1 cannot already have an ID")).body(null);
        }
        Owner1 result = owner1Repository.save(owner1);
        return ResponseEntity.created(new URI("/api/owner-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /owner-1-s : Updates an existing owner1.
     *
     * @param owner1 the owner1 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated owner1,
     * or with status 400 (Bad Request) if the owner1 is not valid,
     * or with status 500 (Internal Server Error) if the owner1 couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/owner-1-s")
    @Timed
    public ResponseEntity<Owner1> updateOwner1(@RequestBody Owner1 owner1) throws URISyntaxException {
        log.debug("REST request to update Owner1 : {}", owner1);
        if (owner1.getId() == null) {
            return createOwner1(owner1);
        }
        Owner1 result = owner1Repository.save(owner1);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, owner1.getId().toString()))
            .body(result);
    }

    /**
     * GET  /owner-1-s : get all the owner1S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of owner1S in body
     */
    @GetMapping("/owner-1-s")
    @Timed
    public ResponseEntity<List<Owner1>> getAllOwner1S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Owner1S");
        Page<Owner1> page = owner1Repository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/owner-1-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /owner-1-s/:id : get the "id" owner1.
     *
     * @param id the id of the owner1 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the owner1, or with status 404 (Not Found)
     */
    @GetMapping("/owner-1-s/{id}")
    @Timed
    public ResponseEntity<Owner1> getOwner1(@PathVariable Long id) {
        log.debug("REST request to get Owner1 : {}", id);
        Owner1 owner1 = owner1Repository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(owner1));
    }

    /**
     * DELETE  /owner-1-s/:id : delete the "id" owner1.
     *
     * @param id the id of the owner1 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/owner-1-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteOwner1(@PathVariable Long id) {
        log.debug("REST request to delete Owner1 : {}", id);
        owner1Repository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
