package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestApp;

import com.mycompany.myapp.domain.Owner3;
import com.mycompany.myapp.repository.Owner3Repository;
import com.mycompany.myapp.service.Owner3Service;
import com.mycompany.myapp.repository.search.Owner3SearchRepository;
import com.mycompany.myapp.service.dto.Owner3DTO;
import com.mycompany.myapp.service.mapper.Owner3Mapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Owner3Resource REST controller.
 *
 * @see Owner3Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
public class Owner3ResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private Owner3Repository owner3Repository;

    @Autowired
    private Owner3Mapper owner3Mapper;

    @Autowired
    private Owner3Service owner3Service;

    @Autowired
    private Owner3SearchRepository owner3SearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOwner3MockMvc;

    private Owner3 owner3;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Owner3Resource owner3Resource = new Owner3Resource(owner3Service);
        this.restOwner3MockMvc = MockMvcBuilders.standaloneSetup(owner3Resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Owner3 createEntity(EntityManager em) {
        Owner3 owner3 = new Owner3()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return owner3;
    }

    @Before
    public void initTest() {
        owner3SearchRepository.deleteAll();
        owner3 = createEntity(em);
    }

    @Test
    @Transactional
    public void createOwner3() throws Exception {
        int databaseSizeBeforeCreate = owner3Repository.findAll().size();

        // Create the Owner3
        Owner3DTO owner3DTO = owner3Mapper.toDto(owner3);
        restOwner3MockMvc.perform(post("/api/owner-3-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner3DTO)))
            .andExpect(status().isCreated());

        // Validate the Owner3 in the database
        List<Owner3> owner3List = owner3Repository.findAll();
        assertThat(owner3List).hasSize(databaseSizeBeforeCreate + 1);
        Owner3 testOwner3 = owner3List.get(owner3List.size() - 1);
        assertThat(testOwner3.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOwner3.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Owner3 in Elasticsearch
        Owner3 owner3Es = owner3SearchRepository.findOne(testOwner3.getId());
        assertThat(owner3Es).isEqualToComparingFieldByField(testOwner3);
    }

    @Test
    @Transactional
    public void createOwner3WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = owner3Repository.findAll().size();

        // Create the Owner3 with an existing ID
        owner3.setId(1L);
        Owner3DTO owner3DTO = owner3Mapper.toDto(owner3);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOwner3MockMvc.perform(post("/api/owner-3-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner3DTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Owner3> owner3List = owner3Repository.findAll();
        assertThat(owner3List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOwner3S() throws Exception {
        // Initialize the database
        owner3Repository.saveAndFlush(owner3);

        // Get all the owner3List
        restOwner3MockMvc.perform(get("/api/owner-3-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(owner3.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getOwner3() throws Exception {
        // Initialize the database
        owner3Repository.saveAndFlush(owner3);

        // Get the owner3
        restOwner3MockMvc.perform(get("/api/owner-3-s/{id}", owner3.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(owner3.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOwner3() throws Exception {
        // Get the owner3
        restOwner3MockMvc.perform(get("/api/owner-3-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOwner3() throws Exception {
        // Initialize the database
        owner3Repository.saveAndFlush(owner3);
        owner3SearchRepository.save(owner3);
        int databaseSizeBeforeUpdate = owner3Repository.findAll().size();

        // Update the owner3
        Owner3 updatedOwner3 = owner3Repository.findOne(owner3.getId());
        updatedOwner3
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        Owner3DTO owner3DTO = owner3Mapper.toDto(updatedOwner3);

        restOwner3MockMvc.perform(put("/api/owner-3-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner3DTO)))
            .andExpect(status().isOk());

        // Validate the Owner3 in the database
        List<Owner3> owner3List = owner3Repository.findAll();
        assertThat(owner3List).hasSize(databaseSizeBeforeUpdate);
        Owner3 testOwner3 = owner3List.get(owner3List.size() - 1);
        assertThat(testOwner3.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOwner3.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Owner3 in Elasticsearch
        Owner3 owner3Es = owner3SearchRepository.findOne(testOwner3.getId());
        assertThat(owner3Es).isEqualToComparingFieldByField(testOwner3);
    }

    @Test
    @Transactional
    public void updateNonExistingOwner3() throws Exception {
        int databaseSizeBeforeUpdate = owner3Repository.findAll().size();

        // Create the Owner3
        Owner3DTO owner3DTO = owner3Mapper.toDto(owner3);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOwner3MockMvc.perform(put("/api/owner-3-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner3DTO)))
            .andExpect(status().isCreated());

        // Validate the Owner3 in the database
        List<Owner3> owner3List = owner3Repository.findAll();
        assertThat(owner3List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOwner3() throws Exception {
        // Initialize the database
        owner3Repository.saveAndFlush(owner3);
        owner3SearchRepository.save(owner3);
        int databaseSizeBeforeDelete = owner3Repository.findAll().size();

        // Get the owner3
        restOwner3MockMvc.perform(delete("/api/owner-3-s/{id}", owner3.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean owner3ExistsInEs = owner3SearchRepository.exists(owner3.getId());
        assertThat(owner3ExistsInEs).isFalse();

        // Validate the database is empty
        List<Owner3> owner3List = owner3Repository.findAll();
        assertThat(owner3List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOwner3() throws Exception {
        // Initialize the database
        owner3Repository.saveAndFlush(owner3);
        owner3SearchRepository.save(owner3);

        // Search the owner3
        restOwner3MockMvc.perform(get("/api/_search/owner-3-s?query=id:" + owner3.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(owner3.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Owner3.class);
        Owner3 owner31 = new Owner3();
        owner31.setId(1L);
        Owner3 owner32 = new Owner3();
        owner32.setId(owner31.getId());
        assertThat(owner31).isEqualTo(owner32);
        owner32.setId(2L);
        assertThat(owner31).isNotEqualTo(owner32);
        owner31.setId(null);
        assertThat(owner31).isNotEqualTo(owner32);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Owner3DTO.class);
        Owner3DTO owner3DTO1 = new Owner3DTO();
        owner3DTO1.setId(1L);
        Owner3DTO owner3DTO2 = new Owner3DTO();
        assertThat(owner3DTO1).isNotEqualTo(owner3DTO2);
        owner3DTO2.setId(owner3DTO1.getId());
        assertThat(owner3DTO1).isEqualTo(owner3DTO2);
        owner3DTO2.setId(2L);
        assertThat(owner3DTO1).isNotEqualTo(owner3DTO2);
        owner3DTO1.setId(null);
        assertThat(owner3DTO1).isNotEqualTo(owner3DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(owner3Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(owner3Mapper.fromId(null)).isNull();
    }
}
