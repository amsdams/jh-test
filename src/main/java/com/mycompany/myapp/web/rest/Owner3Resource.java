package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.Owner3Service;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.Owner3DTO;
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
 * REST controller for managing Owner3.
 */
@RestController
@RequestMapping("/api")
public class Owner3Resource {

    private final Logger log = LoggerFactory.getLogger(Owner3Resource.class);

    private static final String ENTITY_NAME = "owner3";

    private final Owner3Service owner3Service;

    public Owner3Resource(Owner3Service owner3Service) {
        this.owner3Service = owner3Service;
    }

    /**
     * POST  /owner-3-s : Create a new owner3.
     *
     * @param owner3DTO the owner3DTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new owner3DTO, or with status 400 (Bad Request) if the owner3 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/owner-3-s")
    @Timed
    public ResponseEntity<Owner3DTO> createOwner3(@Valid @RequestBody Owner3DTO owner3DTO) throws URISyntaxException {
        log.debug("REST request to save Owner3 : {}", owner3DTO);
        if (owner3DTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new owner3 cannot already have an ID")).body(null);
        }
        Owner3DTO result = owner3Service.save(owner3DTO);
        return ResponseEntity.created(new URI("/api/owner-3-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /owner-3-s : Updates an existing owner3.
     *
     * @param owner3DTO the owner3DTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated owner3DTO,
     * or with status 400 (Bad Request) if the owner3DTO is not valid,
     * or with status 500 (Internal Server Error) if the owner3DTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/owner-3-s")
    @Timed
    public ResponseEntity<Owner3DTO> updateOwner3(@Valid @RequestBody Owner3DTO owner3DTO) throws URISyntaxException {
        log.debug("REST request to update Owner3 : {}", owner3DTO);
        if (owner3DTO.getId() == null) {
            return createOwner3(owner3DTO);
        }
        Owner3DTO result = owner3Service.save(owner3DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, owner3DTO.getId().toString()))
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
    public ResponseEntity<List<Owner3DTO>> getAllOwner3S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Owner3S");
        Page<Owner3DTO> page = owner3Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/owner-3-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /owner-3-s/:id : get the "id" owner3.
     *
     * @param id the id of the owner3DTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the owner3DTO, or with status 404 (Not Found)
     */
    @GetMapping("/owner-3-s/{id}")
    @Timed
    public ResponseEntity<Owner3DTO> getOwner3(@PathVariable Long id) {
        log.debug("REST request to get Owner3 : {}", id);
        Owner3DTO owner3DTO = owner3Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(owner3DTO));
    }

    /**
     * DELETE  /owner-3-s/:id : delete the "id" owner3.
     *
     * @param id the id of the owner3DTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/owner-3-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteOwner3(@PathVariable Long id) {
        log.debug("REST request to delete Owner3 : {}", id);
        owner3Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/owner-3-s?query=:query : search for the owner3 corresponding
     * to the query.
     *
     * @param query the query of the owner3 search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/owner-3-s")
    @Timed
    public ResponseEntity<List<Owner3DTO>> searchOwner3S(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Owner3S for query {}", query);
        Page<Owner3DTO> page = owner3Service.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/owner-3-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
