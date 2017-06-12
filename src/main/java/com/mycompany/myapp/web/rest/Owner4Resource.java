package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Owner4;

import com.mycompany.myapp.repository.Owner4Repository;
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
 * REST controller for managing Owner4.
 */
@RestController
@RequestMapping("/api")
public class Owner4Resource {

    private final Logger log = LoggerFactory.getLogger(Owner4Resource.class);

    private static final String ENTITY_NAME = "owner4";

    private final Owner4Repository owner4Repository;

    public Owner4Resource(Owner4Repository owner4Repository) {
        this.owner4Repository = owner4Repository;
    }

    /**
     * POST  /owner-4-s : Create a new owner4.
     *
     * @param owner4 the owner4 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new owner4, or with status 400 (Bad Request) if the owner4 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/owner-4-s")
    @Timed
    public ResponseEntity<Owner4> createOwner4(@RequestBody Owner4 owner4) throws URISyntaxException {
        log.debug("REST request to save Owner4 : {}", owner4);
        if (owner4.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new owner4 cannot already have an ID")).body(null);
        }
        Owner4 result = owner4Repository.save(owner4);
        return ResponseEntity.created(new URI("/api/owner-4-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /owner-4-s : Updates an existing owner4.
     *
     * @param owner4 the owner4 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated owner4,
     * or with status 400 (Bad Request) if the owner4 is not valid,
     * or with status 500 (Internal Server Error) if the owner4 couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/owner-4-s")
    @Timed
    public ResponseEntity<Owner4> updateOwner4(@RequestBody Owner4 owner4) throws URISyntaxException {
        log.debug("REST request to update Owner4 : {}", owner4);
        if (owner4.getId() == null) {
            return createOwner4(owner4);
        }
        Owner4 result = owner4Repository.save(owner4);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, owner4.getId().toString()))
            .body(result);
    }

    /**
     * GET  /owner-4-s : get all the owner4S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of owner4S in body
     */
    @GetMapping("/owner-4-s")
    @Timed
    public ResponseEntity<List<Owner4>> getAllOwner4S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Owner4S");
        Page<Owner4> page = owner4Repository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/owner-4-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /owner-4-s/:id : get the "id" owner4.
     *
     * @param id the id of the owner4 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the owner4, or with status 404 (Not Found)
     */
    @GetMapping("/owner-4-s/{id}")
    @Timed
    public ResponseEntity<Owner4> getOwner4(@PathVariable Long id) {
        log.debug("REST request to get Owner4 : {}", id);
        Owner4 owner4 = owner4Repository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(owner4));
    }

    /**
     * DELETE  /owner-4-s/:id : delete the "id" owner4.
     *
     * @param id the id of the owner4 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/owner-4-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteOwner4(@PathVariable Long id) {
        log.debug("REST request to delete Owner4 : {}", id);
        owner4Repository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
