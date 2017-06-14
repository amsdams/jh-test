package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestApp;

import com.mycompany.myapp.domain.Owner0;
import com.mycompany.myapp.repository.Owner0Repository;
import com.mycompany.myapp.service.Owner0Service;
import com.mycompany.myapp.repository.search.Owner0SearchRepository;
import com.mycompany.myapp.service.dto.Owner0DTO;
import com.mycompany.myapp.service.mapper.Owner0Mapper;
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
 * Test class for the Owner0Resource REST controller.
 *
 * @see Owner0Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
public class Owner0ResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private Owner0Repository owner0Repository;

    @Autowired
    private Owner0Mapper owner0Mapper;

    @Autowired
    private Owner0Service owner0Service;

    @Autowired
    private Owner0SearchRepository owner0SearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOwner0MockMvc;

    private Owner0 owner0;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Owner0Resource owner0Resource = new Owner0Resource(owner0Service);
        this.restOwner0MockMvc = MockMvcBuilders.standaloneSetup(owner0Resource)
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
    public static Owner0 createEntity(EntityManager em) {
        Owner0 owner0 = new Owner0()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return owner0;
    }

    @Before
    public void initTest() {
        owner0SearchRepository.deleteAll();
        owner0 = createEntity(em);
    }

    @Test
    @Transactional
    public void createOwner0() throws Exception {
        int databaseSizeBeforeCreate = owner0Repository.findAll().size();

        // Create the Owner0
        Owner0DTO owner0DTO = owner0Mapper.toDto(owner0);
        restOwner0MockMvc.perform(post("/api/owner-0-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner0DTO)))
            .andExpect(status().isCreated());

        // Validate the Owner0 in the database
        List<Owner0> owner0List = owner0Repository.findAll();
        assertThat(owner0List).hasSize(databaseSizeBeforeCreate + 1);
        Owner0 testOwner0 = owner0List.get(owner0List.size() - 1);
        assertThat(testOwner0.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOwner0.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Owner0 in Elasticsearch
        Owner0 owner0Es = owner0SearchRepository.findOne(testOwner0.getId());
        assertThat(owner0Es).isEqualToComparingFieldByField(testOwner0);
    }

    @Test
    @Transactional
    public void createOwner0WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = owner0Repository.findAll().size();

        // Create the Owner0 with an existing ID
        owner0.setId(1L);
        Owner0DTO owner0DTO = owner0Mapper.toDto(owner0);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOwner0MockMvc.perform(post("/api/owner-0-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner0DTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Owner0> owner0List = owner0Repository.findAll();
        assertThat(owner0List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOwner0S() throws Exception {
        // Initialize the database
        owner0Repository.saveAndFlush(owner0);

        // Get all the owner0List
        restOwner0MockMvc.perform(get("/api/owner-0-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(owner0.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getOwner0() throws Exception {
        // Initialize the database
        owner0Repository.saveAndFlush(owner0);

        // Get the owner0
        restOwner0MockMvc.perform(get("/api/owner-0-s/{id}", owner0.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(owner0.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOwner0() throws Exception {
        // Get the owner0
        restOwner0MockMvc.perform(get("/api/owner-0-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOwner0() throws Exception {
        // Initialize the database
        owner0Repository.saveAndFlush(owner0);
        owner0SearchRepository.save(owner0);
        int databaseSizeBeforeUpdate = owner0Repository.findAll().size();

        // Update the owner0
        Owner0 updatedOwner0 = owner0Repository.findOne(owner0.getId());
        updatedOwner0
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        Owner0DTO owner0DTO = owner0Mapper.toDto(updatedOwner0);

        restOwner0MockMvc.perform(put("/api/owner-0-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner0DTO)))
            .andExpect(status().isOk());

        // Validate the Owner0 in the database
        List<Owner0> owner0List = owner0Repository.findAll();
        assertThat(owner0List).hasSize(databaseSizeBeforeUpdate);
        Owner0 testOwner0 = owner0List.get(owner0List.size() - 1);
        assertThat(testOwner0.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOwner0.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Owner0 in Elasticsearch
        Owner0 owner0Es = owner0SearchRepository.findOne(testOwner0.getId());
        assertThat(owner0Es).isEqualToComparingFieldByField(testOwner0);
    }

    @Test
    @Transactional
    public void updateNonExistingOwner0() throws Exception {
        int databaseSizeBeforeUpdate = owner0Repository.findAll().size();

        // Create the Owner0
        Owner0DTO owner0DTO = owner0Mapper.toDto(owner0);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOwner0MockMvc.perform(put("/api/owner-0-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner0DTO)))
            .andExpect(status().isCreated());

        // Validate the Owner0 in the database
        List<Owner0> owner0List = owner0Repository.findAll();
        assertThat(owner0List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOwner0() throws Exception {
        // Initialize the database
        owner0Repository.saveAndFlush(owner0);
        owner0SearchRepository.save(owner0);
        int databaseSizeBeforeDelete = owner0Repository.findAll().size();

        // Get the owner0
        restOwner0MockMvc.perform(delete("/api/owner-0-s/{id}", owner0.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean owner0ExistsInEs = owner0SearchRepository.exists(owner0.getId());
        assertThat(owner0ExistsInEs).isFalse();

        // Validate the database is empty
        List<Owner0> owner0List = owner0Repository.findAll();
        assertThat(owner0List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOwner0() throws Exception {
        // Initialize the database
        owner0Repository.saveAndFlush(owner0);
        owner0SearchRepository.save(owner0);

        // Search the owner0
        restOwner0MockMvc.perform(get("/api/_search/owner-0-s?query=id:" + owner0.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(owner0.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Owner0.class);
        Owner0 owner01 = new Owner0();
        owner01.setId(1L);
        Owner0 owner02 = new Owner0();
        owner02.setId(owner01.getId());
        assertThat(owner01).isEqualTo(owner02);
        owner02.setId(2L);
        assertThat(owner01).isNotEqualTo(owner02);
        owner01.setId(null);
        assertThat(owner01).isNotEqualTo(owner02);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Owner0DTO.class);
        Owner0DTO owner0DTO1 = new Owner0DTO();
        owner0DTO1.setId(1L);
        Owner0DTO owner0DTO2 = new Owner0DTO();
        assertThat(owner0DTO1).isNotEqualTo(owner0DTO2);
        owner0DTO2.setId(owner0DTO1.getId());
        assertThat(owner0DTO1).isEqualTo(owner0DTO2);
        owner0DTO2.setId(2L);
        assertThat(owner0DTO1).isNotEqualTo(owner0DTO2);
        owner0DTO1.setId(null);
        assertThat(owner0DTO1).isNotEqualTo(owner0DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(owner0Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(owner0Mapper.fromId(null)).isNull();
    }
}
