package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestApp;

import com.mycompany.myapp.domain.Owner4;
import com.mycompany.myapp.repository.Owner4Repository;
import com.mycompany.myapp.service.Owner4Service;
import com.mycompany.myapp.service.dto.Owner4DTO;
import com.mycompany.myapp.service.mapper.Owner4Mapper;
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
 * Test class for the Owner4Resource REST controller.
 *
 * @see Owner4Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
public class Owner4ResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private Owner4Repository owner4Repository;

    @Autowired
    private Owner4Mapper owner4Mapper;

    @Autowired
    private Owner4Service owner4Service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOwner4MockMvc;

    private Owner4 owner4;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Owner4Resource owner4Resource = new Owner4Resource(owner4Service);
        this.restOwner4MockMvc = MockMvcBuilders.standaloneSetup(owner4Resource)
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
    public static Owner4 createEntity(EntityManager em) {
        Owner4 owner4 = new Owner4()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return owner4;
    }

    @Before
    public void initTest() {
        owner4 = createEntity(em);
    }

    @Test
    @Transactional
    public void createOwner4() throws Exception {
        int databaseSizeBeforeCreate = owner4Repository.findAll().size();

        // Create the Owner4
        Owner4DTO owner4DTO = owner4Mapper.toDto(owner4);
        restOwner4MockMvc.perform(post("/api/owner-4-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner4DTO)))
            .andExpect(status().isCreated());

        // Validate the Owner4 in the database
        List<Owner4> owner4List = owner4Repository.findAll();
        assertThat(owner4List).hasSize(databaseSizeBeforeCreate + 1);
        Owner4 testOwner4 = owner4List.get(owner4List.size() - 1);
        assertThat(testOwner4.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOwner4.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createOwner4WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = owner4Repository.findAll().size();

        // Create the Owner4 with an existing ID
        owner4.setId(1L);
        Owner4DTO owner4DTO = owner4Mapper.toDto(owner4);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOwner4MockMvc.perform(post("/api/owner-4-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner4DTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Owner4> owner4List = owner4Repository.findAll();
        assertThat(owner4List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOwner4S() throws Exception {
        // Initialize the database
        owner4Repository.saveAndFlush(owner4);

        // Get all the owner4List
        restOwner4MockMvc.perform(get("/api/owner-4-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(owner4.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getOwner4() throws Exception {
        // Initialize the database
        owner4Repository.saveAndFlush(owner4);

        // Get the owner4
        restOwner4MockMvc.perform(get("/api/owner-4-s/{id}", owner4.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(owner4.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOwner4() throws Exception {
        // Get the owner4
        restOwner4MockMvc.perform(get("/api/owner-4-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOwner4() throws Exception {
        // Initialize the database
        owner4Repository.saveAndFlush(owner4);
        int databaseSizeBeforeUpdate = owner4Repository.findAll().size();

        // Update the owner4
        Owner4 updatedOwner4 = owner4Repository.findOne(owner4.getId());
        updatedOwner4
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        Owner4DTO owner4DTO = owner4Mapper.toDto(updatedOwner4);

        restOwner4MockMvc.perform(put("/api/owner-4-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner4DTO)))
            .andExpect(status().isOk());

        // Validate the Owner4 in the database
        List<Owner4> owner4List = owner4Repository.findAll();
        assertThat(owner4List).hasSize(databaseSizeBeforeUpdate);
        Owner4 testOwner4 = owner4List.get(owner4List.size() - 1);
        assertThat(testOwner4.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOwner4.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingOwner4() throws Exception {
        int databaseSizeBeforeUpdate = owner4Repository.findAll().size();

        // Create the Owner4
        Owner4DTO owner4DTO = owner4Mapper.toDto(owner4);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOwner4MockMvc.perform(put("/api/owner-4-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner4DTO)))
            .andExpect(status().isCreated());

        // Validate the Owner4 in the database
        List<Owner4> owner4List = owner4Repository.findAll();
        assertThat(owner4List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOwner4() throws Exception {
        // Initialize the database
        owner4Repository.saveAndFlush(owner4);
        int databaseSizeBeforeDelete = owner4Repository.findAll().size();

        // Get the owner4
        restOwner4MockMvc.perform(delete("/api/owner-4-s/{id}", owner4.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Owner4> owner4List = owner4Repository.findAll();
        assertThat(owner4List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Owner4.class);
        Owner4 owner41 = new Owner4();
        owner41.setId(1L);
        Owner4 owner42 = new Owner4();
        owner42.setId(owner41.getId());
        assertThat(owner41).isEqualTo(owner42);
        owner42.setId(2L);
        assertThat(owner41).isNotEqualTo(owner42);
        owner41.setId(null);
        assertThat(owner41).isNotEqualTo(owner42);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Owner4DTO.class);
        Owner4DTO owner4DTO1 = new Owner4DTO();
        owner4DTO1.setId(1L);
        Owner4DTO owner4DTO2 = new Owner4DTO();
        assertThat(owner4DTO1).isNotEqualTo(owner4DTO2);
        owner4DTO2.setId(owner4DTO1.getId());
        assertThat(owner4DTO1).isEqualTo(owner4DTO2);
        owner4DTO2.setId(2L);
        assertThat(owner4DTO1).isNotEqualTo(owner4DTO2);
        owner4DTO1.setId(null);
        assertThat(owner4DTO1).isNotEqualTo(owner4DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(owner4Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(owner4Mapper.fromId(null)).isNull();
    }
}
