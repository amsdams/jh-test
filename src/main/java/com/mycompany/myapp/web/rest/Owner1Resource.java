package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.Owner1Service;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.Owner1DTO;
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
 * REST controller for managing Owner1.
 */
@RestController
@RequestMapping("/api")
public class Owner1Resource {

    private final Logger log = LoggerFactory.getLogger(Owner1Resource.class);

    private static final String ENTITY_NAME = "owner1";

    private final Owner1Service owner1Service;

    public Owner1Resource(Owner1Service owner1Service) {
        this.owner1Service = owner1Service;
    }

    /**
     * POST  /owner-1-s : Create a new owner1.
     *
     * @param owner1DTO the owner1DTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new owner1DTO, or with status 400 (Bad Request) if the owner1 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/owner-1-s")
    @Timed
    public ResponseEntity<Owner1DTO> createOwner1(@Valid @RequestBody Owner1DTO owner1DTO) throws URISyntaxException {
        log.debug("REST request to save Owner1 : {}", owner1DTO);
        if (owner1DTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new owner1 cannot already have an ID")).body(null);
        }
        Owner1DTO result = owner1Service.save(owner1DTO);
        return ResponseEntity.created(new URI("/api/owner-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /owner-1-s : Updates an existing owner1.
     *
     * @param owner1DTO the owner1DTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated owner1DTO,
     * or with status 400 (Bad Request) if the owner1DTO is not valid,
     * or with status 500 (Internal Server Error) if the owner1DTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/owner-1-s")
    @Timed
    public ResponseEntity<Owner1DTO> updateOwner1(@Valid @RequestBody Owner1DTO owner1DTO) throws URISyntaxException {
        log.debug("REST request to update Owner1 : {}", owner1DTO);
        if (owner1DTO.getId() == null) {
            return createOwner1(owner1DTO);
        }
        Owner1DTO result = owner1Service.save(owner1DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, owner1DTO.getId().toString()))
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
    public ResponseEntity<List<Owner1DTO>> getAllOwner1S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Owner1S");
        Page<Owner1DTO> page = owner1Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/owner-1-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /owner-1-s/:id : get the "id" owner1.
     *
     * @param id the id of the owner1DTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the owner1DTO, or with status 404 (Not Found)
     */
    @GetMapping("/owner-1-s/{id}")
    @Timed
    public ResponseEntity<Owner1DTO> getOwner1(@PathVariable Long id) {
        log.debug("REST request to get Owner1 : {}", id);
        Owner1DTO owner1DTO = owner1Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(owner1DTO));
    }

    /**
     * DELETE  /owner-1-s/:id : delete the "id" owner1.
     *
     * @param id the id of the owner1DTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/owner-1-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteOwner1(@PathVariable Long id) {
        log.debug("REST request to delete Owner1 : {}", id);
        owner1Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/owner-1-s?query=:query : search for the owner1 corresponding
     * to the query.
     *
     * @param query the query of the owner1 search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/owner-1-s")
    @Timed
    public ResponseEntity<List<Owner1DTO>> searchOwner1S(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Owner1S for query {}", query);
        Page<Owner1DTO> page = owner1Service.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/owner-1-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
