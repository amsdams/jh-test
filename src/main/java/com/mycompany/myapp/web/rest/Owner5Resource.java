package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Owner5;

import com.mycompany.myapp.repository.Owner5Repository;
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
 * REST controller for managing Owner5.
 */
@RestController
@RequestMapping("/api")
public class Owner5Resource {

    private final Logger log = LoggerFactory.getLogger(Owner5Resource.class);

    private static final String ENTITY_NAME = "owner5";

    private final Owner5Repository owner5Repository;

    public Owner5Resource(Owner5Repository owner5Repository) {
        this.owner5Repository = owner5Repository;
    }

    /**
     * POST  /owner-5-s : Create a new owner5.
     *
     * @param owner5 the owner5 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new owner5, or with status 400 (Bad Request) if the owner5 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/owner-5-s")
    @Timed
    public ResponseEntity<Owner5> createOwner5(@RequestBody Owner5 owner5) throws URISyntaxException {
        log.debug("REST request to save Owner5 : {}", owner5);
        if (owner5.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new owner5 cannot already have an ID")).body(null);
        }
        Owner5 result = owner5Repository.save(owner5);
        return ResponseEntity.created(new URI("/api/owner-5-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /owner-5-s : Updates an existing owner5.
     *
     * @param owner5 the owner5 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated owner5,
     * or with status 400 (Bad Request) if the owner5 is not valid,
     * or with status 500 (Internal Server Error) if the owner5 couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/owner-5-s")
    @Timed
    public ResponseEntity<Owner5> updateOwner5(@RequestBody Owner5 owner5) throws URISyntaxException {
        log.debug("REST request to update Owner5 : {}", owner5);
        if (owner5.getId() == null) {
            return createOwner5(owner5);
        }
        Owner5 result = owner5Repository.save(owner5);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, owner5.getId().toString()))
            .body(result);
    }

    /**
     * GET  /owner-5-s : get all the owner5S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of owner5S in body
     */
    @GetMapping("/owner-5-s")
    @Timed
    public ResponseEntity<List<Owner5>> getAllOwner5S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Owner5S");
        Page<Owner5> page = owner5Repository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/owner-5-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /owner-5-s/:id : get the "id" owner5.
     *
     * @param id the id of the owner5 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the owner5, or with status 404 (Not Found)
     */
    @GetMapping("/owner-5-s/{id}")
    @Timed
    public ResponseEntity<Owner5> getOwner5(@PathVariable Long id) {
        log.debug("REST request to get Owner5 : {}", id);
        Owner5 owner5 = owner5Repository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(owner5));
    }

    /**
     * DELETE  /owner-5-s/:id : delete the "id" owner5.
     *
     * @param id the id of the owner5 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/owner-5-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteOwner5(@PathVariable Long id) {
        log.debug("REST request to delete Owner5 : {}", id);
        owner5Repository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
