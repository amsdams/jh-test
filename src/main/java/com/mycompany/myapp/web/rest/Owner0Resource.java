package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.Owner0Service;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.Owner0DTO;
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
 * REST controller for managing Owner0.
 */
@RestController
@RequestMapping("/api")
public class Owner0Resource {

    private final Logger log = LoggerFactory.getLogger(Owner0Resource.class);

    private static final String ENTITY_NAME = "owner0";

    private final Owner0Service owner0Service;

    public Owner0Resource(Owner0Service owner0Service) {
        this.owner0Service = owner0Service;
    }

    /**
     * POST  /owner-0-s : Create a new owner0.
     *
     * @param owner0DTO the owner0DTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new owner0DTO, or with status 400 (Bad Request) if the owner0 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/owner-0-s")
    @Timed
    public ResponseEntity<Owner0DTO> createOwner0(@Valid @RequestBody Owner0DTO owner0DTO) throws URISyntaxException {
        log.debug("REST request to save Owner0 : {}", owner0DTO);
        if (owner0DTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new owner0 cannot already have an ID")).body(null);
        }
        Owner0DTO result = owner0Service.save(owner0DTO);
        return ResponseEntity.created(new URI("/api/owner-0-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /owner-0-s : Updates an existing owner0.
     *
     * @param owner0DTO the owner0DTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated owner0DTO,
     * or with status 400 (Bad Request) if the owner0DTO is not valid,
     * or with status 500 (Internal Server Error) if the owner0DTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/owner-0-s")
    @Timed
    public ResponseEntity<Owner0DTO> updateOwner0(@Valid @RequestBody Owner0DTO owner0DTO) throws URISyntaxException {
        log.debug("REST request to update Owner0 : {}", owner0DTO);
        if (owner0DTO.getId() == null) {
            return createOwner0(owner0DTO);
        }
        Owner0DTO result = owner0Service.save(owner0DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, owner0DTO.getId().toString()))
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
    public ResponseEntity<List<Owner0DTO>> getAllOwner0S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Owner0S");
        Page<Owner0DTO> page = owner0Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/owner-0-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /owner-0-s/:id : get the "id" owner0.
     *
     * @param id the id of the owner0DTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the owner0DTO, or with status 404 (Not Found)
     */
    @GetMapping("/owner-0-s/{id}")
    @Timed
    public ResponseEntity<Owner0DTO> getOwner0(@PathVariable Long id) {
        log.debug("REST request to get Owner0 : {}", id);
        Owner0DTO owner0DTO = owner0Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(owner0DTO));
    }

    /**
     * DELETE  /owner-0-s/:id : delete the "id" owner0.
     *
     * @param id the id of the owner0DTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/owner-0-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteOwner0(@PathVariable Long id) {
        log.debug("REST request to delete Owner0 : {}", id);
        owner0Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/owner-0-s?query=:query : search for the owner0 corresponding
     * to the query.
     *
     * @param query the query of the owner0 search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/owner-0-s")
    @Timed
    public ResponseEntity<List<Owner0DTO>> searchOwner0S(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Owner0S for query {}", query);
        Page<Owner0DTO> page = owner0Service.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/owner-0-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
