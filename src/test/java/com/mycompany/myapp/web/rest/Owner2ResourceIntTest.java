package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestApp;

import com.mycompany.myapp.domain.Owner2;
import com.mycompany.myapp.repository.Owner2Repository;
import com.mycompany.myapp.service.Owner2Service;
import com.mycompany.myapp.service.dto.Owner2DTO;
import com.mycompany.myapp.service.mapper.Owner2Mapper;
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
 * Test class for the Owner2Resource REST controller.
 *
 * @see Owner2Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
public class Owner2ResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private Owner2Repository owner2Repository;

    @Autowired
    private Owner2Mapper owner2Mapper;

    @Autowired
    private Owner2Service owner2Service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOwner2MockMvc;

    private Owner2 owner2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Owner2Resource owner2Resource = new Owner2Resource(owner2Service);
        this.restOwner2MockMvc = MockMvcBuilders.standaloneSetup(owner2Resource)
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
    public static Owner2 createEntity(EntityManager em) {
        Owner2 owner2 = new Owner2()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return owner2;
    }

    @Before
    public void initTest() {
        owner2 = createEntity(em);
    }

    @Test
    @Transactional
    public void createOwner2() throws Exception {
        int databaseSizeBeforeCreate = owner2Repository.findAll().size();

        // Create the Owner2
        Owner2DTO owner2DTO = owner2Mapper.toDto(owner2);
        restOwner2MockMvc.perform(post("/api/owner-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner2DTO)))
            .andExpect(status().isCreated());

        // Validate the Owner2 in the database
        List<Owner2> owner2List = owner2Repository.findAll();
        assertThat(owner2List).hasSize(databaseSizeBeforeCreate + 1);
        Owner2 testOwner2 = owner2List.get(owner2List.size() - 1);
        assertThat(testOwner2.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOwner2.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createOwner2WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = owner2Repository.findAll().size();

        // Create the Owner2 with an existing ID
        owner2.setId(1L);
        Owner2DTO owner2DTO = owner2Mapper.toDto(owner2);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOwner2MockMvc.perform(post("/api/owner-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner2DTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Owner2> owner2List = owner2Repository.findAll();
        assertThat(owner2List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOwner2S() throws Exception {
        // Initialize the database
        owner2Repository.saveAndFlush(owner2);

        // Get all the owner2List
        restOwner2MockMvc.perform(get("/api/owner-2-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(owner2.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getOwner2() throws Exception {
        // Initialize the database
        owner2Repository.saveAndFlush(owner2);

        // Get the owner2
        restOwner2MockMvc.perform(get("/api/owner-2-s/{id}", owner2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(owner2.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOwner2() throws Exception {
        // Get the owner2
        restOwner2MockMvc.perform(get("/api/owner-2-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOwner2() throws Exception {
        // Initialize the database
        owner2Repository.saveAndFlush(owner2);
        int databaseSizeBeforeUpdate = owner2Repository.findAll().size();

        // Update the owner2
        Owner2 updatedOwner2 = owner2Repository.findOne(owner2.getId());
        updatedOwner2
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        Owner2DTO owner2DTO = owner2Mapper.toDto(updatedOwner2);

        restOwner2MockMvc.perform(put("/api/owner-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner2DTO)))
            .andExpect(status().isOk());

        // Validate the Owner2 in the database
        List<Owner2> owner2List = owner2Repository.findAll();
        assertThat(owner2List).hasSize(databaseSizeBeforeUpdate);
        Owner2 testOwner2 = owner2List.get(owner2List.size() - 1);
        assertThat(testOwner2.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOwner2.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingOwner2() throws Exception {
        int databaseSizeBeforeUpdate = owner2Repository.findAll().size();

        // Create the Owner2
        Owner2DTO owner2DTO = owner2Mapper.toDto(owner2);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOwner2MockMvc.perform(put("/api/owner-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(owner2DTO)))
            .andExpect(status().isCreated());

        // Validate the Owner2 in the database
        List<Owner2> owner2List = owner2Repository.findAll();
        assertThat(owner2List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOwner2() throws Exception {
        // Initialize the database
        owner2Repository.saveAndFlush(owner2);
        int databaseSizeBeforeDelete = owner2Repository.findAll().size();

        // Get the owner2
        restOwner2MockMvc.perform(delete("/api/owner-2-s/{id}", owner2.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Owner2> owner2List = owner2Repository.findAll();
        assertThat(owner2List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Owner2.class);
        Owner2 owner21 = new Owner2();
        owner21.setId(1L);
        Owner2 owner22 = new Owner2();
        owner22.setId(owner21.getId());
        assertThat(owner21).isEqualTo(owner22);
        owner22.setId(2L);
        assertThat(owner21).isNotEqualTo(owner22);
        owner21.setId(null);
        assertThat(owner21).isNotEqualTo(owner22);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Owner2DTO.class);
        Owner2DTO owner2DTO1 = new Owner2DTO();
        owner2DTO1.setId(1L);
        Owner2DTO owner2DTO2 = new Owner2DTO();
        assertThat(owner2DTO1).isNotEqualTo(owner2DTO2);
        owner2DTO2.setId(owner2DTO1.getId());
        assertThat(owner2DTO1).isEqualTo(owner2DTO2);
        owner2DTO2.setId(2L);
        assertThat(owner2DTO1).isNotEqualTo(owner2DTO2);
        owner2DTO1.setId(null);
        assertThat(owner2DTO1).isNotEqualTo(owner2DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(owner2Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(owner2Mapper.fromId(null)).isNull();
    }
}
