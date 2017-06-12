package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Owner0;

import com.mycompany.myapp.repository.Owner0Repository;
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
 * REST controller for managing Owner0.
 */
@RestController
@RequestMapping("/api")
public class Owner0Resource {

    private final Logger log = LoggerFactory.getLogger(Owner0Resource.class);

    private static final String ENTITY_NAME = "owner0";

    private final Owner0Repository owner0Repository;

    public Owner0Resource(Owner0Repository owner0Repository) {
        this.owner0Repository = owner0Repository;
    }

    /**
     * POST  /owner-0-s : Create a new owner0.
     *
     * @param owner0 the owner0 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new owner0, or with status 400 (Bad Request) if the owner0 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/owner-0-s")
    @Timed
    public ResponseEntity<Owner0> createOwner0(@RequestBody Owner0 owner0) throws URISyntaxException {
        log.debug("REST request to save Owner0 : {}", owner0);
        if (owner0.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new owner0 cannot already have an ID")).body(null);
        }
        Owner0 result = owner0Repository.save(owner0);
        return ResponseEntity.created(new URI("/api/owner-0-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /owner-0-s : Updates an existing owner0.
     *
     * @param owner0 the owner0 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated owner0,
     * or with status 400 (Bad Request) if the owner0 is not valid,
     * or with status 500 (Internal Server Error) if the owner0 couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/owner-0-s")
    @Timed
    public ResponseEntity<Owner0> updateOwner0(@RequestBody Owner0 owner0) throws URISyntaxException {
        log.debug("REST request to update Owner0 : {}", owner0);
        if (owner0.getId() == null) {
            return createOwner0(owner0);
        }
        Owner0 result = owner0Repository.save(owner0);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, owner0.getId().toString()))
            .body(result);
    }

    /**
     * GET  /owner-0-s : get all the owner0S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of owner0S in body
     */
    @GetMapping("/owner-0-s")
    @Timed
    public ResponseEntity<List<Owner0>> getAllOwner0S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Owner0S");
        Page<Owner0> page = owner0Repository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/owner-0-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /owner-0-s/:id : get the "id" owner0.
     *
     * @param id the id of the owner0 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the owner0, or with status 404 (Not Found)
     */
    @GetMapping("/owner-0-s/{id}")
    @Timed
    public ResponseEntity<Owner0> getOwner0(@PathVariable Long id) {
        log.debug("REST request to get Owner0 : {}", id);
        Owner0 owner0 = owner0Repository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(owner0));
    }

    /**
     * DELETE  /owner-0-s/:id : delete the "id" owner0.
     *
     * @param id the id of the owner0 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/owner-0-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteOwner0(@PathVariable Long id) {
        log.debug("REST request to delete Owner0 : {}", id);
        owner0Repository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
