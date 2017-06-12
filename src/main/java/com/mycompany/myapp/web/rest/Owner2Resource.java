package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Owner2;

import com.mycompany.myapp.repository.Owner2Repository;
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
 * REST controller for managing Owner2.
 */
@RestController
@RequestMapping("/api")
public class Owner2Resource {

    private final Logger log = LoggerFactory.getLogger(Owner2Resource.class);

    private static final String ENTITY_NAME = "owner2";

    private final Owner2Repository owner2Repository;

    public Owner2Resource(Owner2Repository owner2Repository) {
        this.owner2Repository = owner2Repository;
    }

    /**
     * POST  /owner-2-s : Create a new owner2.
     *
     * @param owner2 the owner2 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new owner2, or with status 400 (Bad Request) if the owner2 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/owner-2-s")
    @Timed
    public ResponseEntity<Owner2> createOwner2(@RequestBody Owner2 owner2) throws URISyntaxException {
        log.debug("REST request to save Owner2 : {}", owner2);
        if (owner2.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new owner2 cannot already have an ID")).body(null);
        }
        Owner2 result = owner2Repository.save(owner2);
        return ResponseEntity.created(new URI("/api/owner-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /owner-2-s : Updates an existing owner2.
     *
     * @param owner2 the owner2 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated owner2,
     * or with status 400 (Bad Request) if the owner2 is not valid,
     * or with status 500 (Internal Server Error) if the owner2 couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/owner-2-s")
    @Timed
    public ResponseEntity<Owner2> updateOwner2(@RequestBody Owner2 owner2) throws URISyntaxException {
        log.debug("REST request to update Owner2 : {}", owner2);
        if (owner2.getId() == null) {
            return createOwner2(owner2);
        }
        Owner2 result = owner2Repository.save(owner2);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, owner2.getId().toString()))
            .body(result);
    }

    /**
     * GET  /owner-2-s : get all the owner2S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of owner2S in body
     */
    @GetMapping("/owner-2-s")
    @Timed
    public ResponseEntity<List<Owner2>> getAllOwner2S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Owner2S");
        Page<Owner2> page = owner2Repository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/owner-2-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /owner-2-s/:id : get the "id" owner2.
     *
     * @param id the id of the owner2 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the owner2, or with status 404 (Not Found)
     */
    @GetMapping("/owner-2-s/{id}")
    @Timed
    public ResponseEntity<Owner2> getOwner2(@PathVariable Long id) {
        log.debug("REST request to get Owner2 : {}", id);
        Owner2 owner2 = owner2Repository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(owner2));
    }

    /**
     * DELETE  /owner-2-s/:id : delete the "id" owner2.
     *
     * @param id the id of the owner2 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/owner-2-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteOwner2(@PathVariable Long id) {
        log.debug("REST request to delete Owner2 : {}", id);
        owner2Repository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
