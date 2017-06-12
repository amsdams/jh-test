package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestApp;

import com.mycompany.myapp.domain.Owner5;
import com.mycompany.myapp.repository.Owner5Repository;
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
 * Test class for the Owner5Resource REST controller.
 *
 * @see Owner5Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
public class Owner5ResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private Owner5Repository owner5Repository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOwner5MockMvc;

    private Owner5 owner5;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Owner5Resource owner5Resource = new Owner5Resource(owner5Repository);
        this.restOwner5MockMvc = MockMvcBuilders.standaloneSetup(owner5Resource)
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
    public static Owner5 createEntity(EntityManager em) {
        Owner5 owner5 = new Owner5()
            .name(DEFAULT_NAME);
        return owner5;
    }

    @Before
    public void initTest() {
        owner5 = createEntity(em);
    }

    @Test
    @Transactional
    public void createOwner5() throws Exception {
        int databaseSizeBeforeCreate = owner5Repository.findAll().size();

        // Create the Owner5
        restOwner5MockMvc.perform(post("/api/owner-5-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner5)))
            .andExpect(status().isCreated());

        // Validate the Owner5 in the database
        List<Owner5> owner5List = owner5Repository.findAll();
        assertThat(owner5List).hasSize(databaseSizeBeforeCreate + 1);
        Owner5 testOwner5 = owner5List.get(owner5List.size() - 1);
        assertThat(testOwner5.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createOwner5WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = owner5Repository.findAll().size();

        // Create the Owner5 with an existing ID
        owner5.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOwner5MockMvc.perform(post("/api/owner-5-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner5)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Owner5> owner5List = owner5Repository.findAll();
        assertThat(owner5List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOwner5S() throws Exception {
        // Initialize the database
        owner5Repository.saveAndFlush(owner5);

        // Get all the owner5List
        restOwner5MockMvc.perform(get("/api/owner-5-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(owner5.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getOwner5() throws Exception {
        // Initialize the database
        owner5Repository.saveAndFlush(owner5);

        // Get the owner5
        restOwner5MockMvc.perform(get("/api/owner-5-s/{id}", owner5.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(owner5.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOwner5() throws Exception {
        // Get the owner5
        restOwner5MockMvc.perform(get("/api/owner-5-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOwner5() throws Exception {
        // Initialize the database
        owner5Repository.saveAndFlush(owner5);
        int databaseSizeBeforeUpdate = owner5Repository.findAll().size();

        // Update the owner5
        Owner5 updatedOwner5 = owner5Repository.findOne(owner5.getId());
        updatedOwner5
            .name(UPDATED_NAME);

        restOwner5MockMvc.perform(put("/api/owner-5-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOwner5)))
            .andExpect(status().isOk());

        // Validate the Owner5 in the database
        List<Owner5> owner5List = owner5Repository.findAll();
        assertThat(owner5List).hasSize(databaseSizeBeforeUpdate);
        Owner5 testOwner5 = owner5List.get(owner5List.size() - 1);
        assertThat(testOwner5.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingOwner5() throws Exception {
        int databaseSizeBeforeUpdate = owner5Repository.findAll().size();

        // Create the Owner5

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOwner5MockMvc.perform(put("/api/owner-5-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner5)))
            .andExpect(status().isCreated());

        // Validate the Owner5 in the database
        List<Owner5> owner5List = owner5Repository.findAll();
        assertThat(owner5List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOwner5() throws Exception {
        // Initialize the database
        owner5Repository.saveAndFlush(owner5);
        int databaseSizeBeforeDelete = owner5Repository.findAll().size();

        // Get the owner5
        restOwner5MockMvc.perform(delete("/api/owner-5-s/{id}", owner5.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Owner5> owner5List = owner5Repository.findAll();
        assertThat(owner5List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Owner5.class);
        Owner5 owner51 = new Owner5();
        owner51.setId(1L);
        Owner5 owner52 = new Owner5();
        owner52.setId(owner51.getId());
        assertThat(owner51).isEqualTo(owner52);
        owner52.setId(2L);
        assertThat(owner51).isNotEqualTo(owner52);
        owner51.setId(null);
        assertThat(owner51).isNotEqualTo(owner52);
    }
}
