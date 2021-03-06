package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.Owner2Service;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.Owner2DTO;
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
 * REST controller for managing Owner2.
 */
@RestController
@RequestMapping("/api")
public class Owner2Resource {

    private final Logger log = LoggerFactory.getLogger(Owner2Resource.class);

    private static final String ENTITY_NAME = "owner2";

    private final Owner2Service owner2Service;

    public Owner2Resource(Owner2Service owner2Service) {
        this.owner2Service = owner2Service;
    }

    /**
     * POST  /owner-2-s : Create a new owner2.
     *
     * @param owner2DTO the owner2DTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new owner2DTO, or with status 400 (Bad Request) if the owner2 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/owner-2-s")
    @Timed
    public ResponseEntity<Owner2DTO> createOwner2(@Valid @RequestBody Owner2DTO owner2DTO) throws URISyntaxException {
        log.debug("REST request to save Owner2 : {}", owner2DTO);
        if (owner2DTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new owner2 cannot already have an ID")).body(null);
        }
        Owner2DTO result = owner2Service.save(owner2DTO);
        return ResponseEntity.created(new URI("/api/owner-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /owner-2-s : Updates an existing owner2.
     *
     * @param owner2DTO the owner2DTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated owner2DTO,
     * or with status 400 (Bad Request) if the owner2DTO is not valid,
     * or with status 500 (Internal Server Error) if the owner2DTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/owner-2-s")
    @Timed
    public ResponseEntity<Owner2DTO> updateOwner2(@Valid @RequestBody Owner2DTO owner2DTO) throws URISyntaxException {
        log.debug("REST request to update Owner2 : {}", owner2DTO);
        if (owner2DTO.getId() == null) {
            return createOwner2(owner2DTO);
        }
        Owner2DTO result = owner2Service.save(owner2DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, owner2DTO.getId().toString()))
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
    public ResponseEntity<List<Owner2DTO>> getAllOwner2S(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Owner2S");
        Page<Owner2DTO> page = owner2Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/owner-2-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /owner-2-s/:id : get the "id" owner2.
     *
     * @param id the id of the owner2DTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the owner2DTO, or with status 404 (Not Found)
     */
    @GetMapping("/owner-2-s/{id}")
    @Timed
    public ResponseEntity<Owner2DTO> getOwner2(@PathVariable Long id) {
        log.debug("REST request to get Owner2 : {}", id);
        Owner2DTO owner2DTO = owner2Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(owner2DTO));
    }

    /**
     * DELETE  /owner-2-s/:id : delete the "id" owner2.
     *
     * @param id the id of the owner2DTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/owner-2-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteOwner2(@PathVariable Long id) {
        log.debug("REST request to delete Owner2 : {}", id);
        owner2Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/owner-2-s?query=:query : search for the owner2 corresponding
     * to the query.
     *
     * @param query the query of the owner2 search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/owner-2-s")
    @Timed
    public ResponseEntity<List<Owner2DTO>> searchOwner2S(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Owner2S for query {}", query);
        Page<Owner2DTO> page = owner2Service.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/owner-2-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
