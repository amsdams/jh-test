package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.Owner5Service;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.Owner5DTO;
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
 * REST controller for managing Owner5.
 */
@RestController
@RequestMapping("/api")
public class Owner5Resource {

    private final Logger log = LoggerFactory.getLogger(Owner5Resource.class);

    private static final String ENTITY_NAME = "owner5";

    private final Owner5Service owner5Service;

    public Owner5Resource(Owner5Service owner5Service) {
        this.owner5Service = owner5Service;
    }

    /**
     * POST  /owner-5-s : Create a new owner5.
     *
     * @param owner5DTO the owner5DTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new owner5DTO, or with status 400 (Bad Request) if the owner5 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/owner-5-s")
    @Timed
    public ResponseEntity<Owner5DTO> createOwner5(@Valid @RequestBody Owner5DTO owner5DTO) throws URISyntaxException {
        log.debug("REST request to save Owner5 : {}", owner5DTO);
        if (owner5DTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new owner5 cannot already have an ID")).body(null);
        }
        Owner5DTO result = owner5Service.save(owner5DTO);
        return ResponseEntity.created(new URI("/api/owner-5-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /owner-5-s : Updates an existing owner5.
     *
     * @param owner5DTO the owner5DTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated owner5DTO,
     * or with status 400 (Bad Request) if the owner5DTO is not valid,
     * or with status 500 (Internal Server Error) if the owner5DTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/owner-5-s")
    @Timed
    public ResponseEntity<Owner5DTO> updateOwner5(@Valid @RequestBody Owner5DTO owner5DTO) throws URISyntaxException {
        log.debug("REST request to update Owner5 : {}", owner5DTO);
        if (owner5DTO.getId() == null) {
            return createOwner5(owner5DTO);
        }
        Owner5DTO result = owner5Service.save(owner5DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, owner5DTO.getId().toString()))
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
    public ResponseEntity<List<Owner5DTO>> getAllOwner5S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Owner5S");
        Page<Owner5DTO> page = owner5Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/owner-5-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /owner-5-s/:id : get the "id" owner5.
     *
     * @param id the id of the owner5DTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the owner5DTO, or with status 404 (Not Found)
     */
    @GetMapping("/owner-5-s/{id}")
    @Timed
    public ResponseEntity<Owner5DTO> getOwner5(@PathVariable Long id) {
        log.debug("REST request to get Owner5 : {}", id);
        Owner5DTO owner5DTO = owner5Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(owner5DTO));
    }

    /**
     * DELETE  /owner-5-s/:id : delete the "id" owner5.
     *
     * @param id the id of the owner5DTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/owner-5-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteOwner5(@PathVariable Long id) {
        log.debug("REST request to delete Owner5 : {}", id);
        owner5Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/owner-5-s?query=:query : search for the owner5 corresponding
     * to the query.
     *
     * @param query the query of the owner5 search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/owner-5-s")
    @Timed
    public ResponseEntity<List<Owner5DTO>> searchOwner5S(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Owner5S for query {}", query);
        Page<Owner5DTO> page = owner5Service.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/owner-5-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
