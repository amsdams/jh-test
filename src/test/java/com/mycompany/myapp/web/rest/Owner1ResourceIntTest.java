package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestApp;

import com.mycompany.myapp.domain.Owner1;
import com.mycompany.myapp.repository.Owner1Repository;
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
 * Test class for the Owner1Resource REST controller.
 *
 * @see Owner1Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
public class Owner1ResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private Owner1Repository owner1Repository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOwner1MockMvc;

    private Owner1 owner1;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Owner1Resource owner1Resource = new Owner1Resource(owner1Repository);
        this.restOwner1MockMvc = MockMvcBuilders.standaloneSetup(owner1Resource)
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
    public static Owner1 createEntity(EntityManager em) {
        Owner1 owner1 = new Owner1()
            .name(DEFAULT_NAME);
        return owner1;
    }

    @Before
    public void initTest() {
        owner1 = createEntity(em);
    }

    @Test
    @Transactional
    public void createOwner1() throws Exception {
        int databaseSizeBeforeCreate = owner1Repository.findAll().size();

        // Create the Owner1
        restOwner1MockMvc.perform(post("/api/owner-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner1)))
            .andExpect(status().isCreated());

        // Validate the Owner1 in the database
        List<Owner1> owner1List = owner1Repository.findAll();
        assertThat(owner1List).hasSize(databaseSizeBeforeCreate + 1);
        Owner1 testOwner1 = owner1List.get(owner1List.size() - 1);
        assertThat(testOwner1.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createOwner1WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = owner1Repository.findAll().size();

        // Create the Owner1 with an existing ID
        owner1.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOwner1MockMvc.perform(post("/api/owner-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner1)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Owner1> owner1List = owner1Repository.findAll();
        assertThat(owner1List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOwner1S() throws Exception {
        // Initialize the database
        owner1Repository.saveAndFlush(owner1);

        // Get all the owner1List
        restOwner1MockMvc.perform(get("/api/owner-1-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(owner1.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getOwner1() throws Exception {
        // Initialize the database
        owner1Repository.saveAndFlush(owner1);

        // Get the owner1
        restOwner1MockMvc.perform(get("/api/owner-1-s/{id}", owner1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(owner1.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOwner1() throws Exception {
        // Get the owner1
        restOwner1MockMvc.perform(get("/api/owner-1-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOwner1() throws Exception {
        // Initialize the database
        owner1Repository.saveAndFlush(owner1);
        int databaseSizeBeforeUpdate = owner1Repository.findAll().size();

        // Update the owner1
        Owner1 updatedOwner1 = owner1Repository.findOne(owner1.getId());
        updatedOwner1
            .name(UPDATED_NAME);

        restOwner1MockMvc.perform(put("/api/owner-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOwner1)))
            .andExpect(status().isOk());

        // Validate the Owner1 in the database
        List<Owner1> owner1List = owner1Repository.findAll();
        assertThat(owner1List).hasSize(databaseSizeBeforeUpdate);
        Owner1 testOwner1 = owner1List.get(owner1List.size() - 1);
        assertThat(testOwner1.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingOwner1() throws Exception {
        int databaseSizeBeforeUpdate = owner1Repository.findAll().size();

        // Create the Owner1

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOwner1MockMvc.perform(put("/api/owner-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner1)))
            .andExpect(status().isCreated());

        // Validate the Owner1 in the database
        List<Owner1> owner1List = owner1Repository.findAll();
        assertThat(owner1List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOwner1() throws Exception {
        // Initialize the database
        owner1Repository.saveAndFlush(owner1);
        int databaseSizeBeforeDelete = owner1Repository.findAll().size();

        // Get the owner1
        restOwner1MockMvc.perform(delete("/api/owner-1-s/{id}", owner1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Owner1> owner1List = owner1Repository.findAll();
        assertThat(owner1List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Owner1.class);
        Owner1 owner11 = new Owner1();
        owner11.setId(1L);
        Owner1 owner12 = new Owner1();
        owner12.setId(owner11.getId());
        assertThat(owner11).isEqualTo(owner12);
        owner12.setId(2L);
        assertThat(owner11).isNotEqualTo(owner12);
        owner11.setId(null);
        assertThat(owner11).isNotEqualTo(owner12);
    }
}
