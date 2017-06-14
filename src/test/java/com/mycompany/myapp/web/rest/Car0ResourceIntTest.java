package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestApp;

import com.mycompany.myapp.domain.Car0;
import com.mycompany.myapp.repository.Car0Repository;
import com.mycompany.myapp.service.Car0Service;
import com.mycompany.myapp.repository.search.Car0SearchRepository;
import com.mycompany.myapp.service.dto.Car0DTO;
import com.mycompany.myapp.service.mapper.Car0Mapper;
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
 * Test class for the Car0Resource REST controller.
 *
 * @see Car0Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
public class Car0ResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private Car0Repository car0Repository;

    @Autowired
    private Car0Mapper car0Mapper;

    @Autowired
    private Car0Service car0Service;

    @Autowired
    private Car0SearchRepository car0SearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCar0MockMvc;

    private Car0 car0;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Car0Resource car0Resource = new Car0Resource(car0Service);
        this.restCar0MockMvc = MockMvcBuilders.standaloneSetup(car0Resource)
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
    public static Car0 createEntity(EntityManager em) {
        Car0 car0 = new Car0()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return car0;
    }

    @Before
    public void initTest() {
        car0SearchRepository.deleteAll();
        car0 = createEntity(em);
    }

    @Test
    @Transactional
    public void createCar0() throws Exception {
        int databaseSizeBeforeCreate = car0Repository.findAll().size();

        // Create the Car0
        Car0DTO car0DTO = car0Mapper.toDto(car0);
        restCar0MockMvc.perform(post("/api/car-0-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car0DTO)))
            .andExpect(status().isCreated());

        // Validate the Car0 in the database
        List<Car0> car0List = car0Repository.findAll();
        assertThat(car0List).hasSize(databaseSizeBeforeCreate + 1);
        Car0 testCar0 = car0List.get(car0List.size() - 1);
        assertThat(testCar0.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCar0.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Car0 in Elasticsearch
        Car0 car0Es = car0SearchRepository.findOne(testCar0.getId());
        assertThat(car0Es).isEqualToComparingFieldByField(testCar0);
    }

    @Test
    @Transactional
    public void createCar0WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = car0Repository.findAll().size();

        // Create the Car0 with an existing ID
        car0.setId(1L);
        Car0DTO car0DTO = car0Mapper.toDto(car0);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCar0MockMvc.perform(post("/api/car-0-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car0DTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Car0> car0List = car0Repository.findAll();
        assertThat(car0List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCar0S() throws Exception {
        // Initialize the database
        car0Repository.saveAndFlush(car0);

        // Get all the car0List
        restCar0MockMvc.perform(get("/api/car-0-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(car0.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCar0() throws Exception {
        // Initialize the database
        car0Repository.saveAndFlush(car0);

        // Get the car0
        restCar0MockMvc.perform(get("/api/car-0-s/{id}", car0.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(car0.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCar0() throws Exception {
        // Get the car0
        restCar0MockMvc.perform(get("/api/car-0-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCar0() throws Exception {
        // Initialize the database
        car0Repository.saveAndFlush(car0);
        car0SearchRepository.save(car0);
        int databaseSizeBeforeUpdate = car0Repository.findAll().size();

        // Update the car0
        Car0 updatedCar0 = car0Repository.findOne(car0.getId());
        updatedCar0
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        Car0DTO car0DTO = car0Mapper.toDto(updatedCar0);

        restCar0MockMvc.perform(put("/api/car-0-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car0DTO)))
            .andExpect(status().isOk());

        // Validate the Car0 in the database
        List<Car0> car0List = car0Repository.findAll();
        assertThat(car0List).hasSize(databaseSizeBeforeUpdate);
        Car0 testCar0 = car0List.get(car0List.size() - 1);
        assertThat(testCar0.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCar0.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Car0 in Elasticsearch
        Car0 car0Es = car0SearchRepository.findOne(testCar0.getId());
        assertThat(car0Es).isEqualToComparingFieldByField(testCar0);
    }

    @Test
    @Transactional
    public void updateNonExistingCar0() throws Exception {
        int databaseSizeBeforeUpdate = car0Repository.findAll().size();

        // Create the Car0
        Car0DTO car0DTO = car0Mapper.toDto(car0);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCar0MockMvc.perform(put("/api/car-0-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car0DTO)))
            .andExpect(status().isCreated());

        // Validate the Car0 in the database
        List<Car0> car0List = car0Repository.findAll();
        assertThat(car0List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCar0() throws Exception {
        // Initialize the database
        car0Repository.saveAndFlush(car0);
        car0SearchRepository.save(car0);
        int databaseSizeBeforeDelete = car0Repository.findAll().size();

        // Get the car0
        restCar0MockMvc.perform(delete("/api/car-0-s/{id}", car0.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean car0ExistsInEs = car0SearchRepository.exists(car0.getId());
        assertThat(car0ExistsInEs).isFalse();

        // Validate the database is empty
        List<Car0> car0List = car0Repository.findAll();
        assertThat(car0List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCar0() throws Exception {
        // Initialize the database
        car0Repository.saveAndFlush(car0);
        car0SearchRepository.save(car0);

        // Search the car0
        restCar0MockMvc.perform(get("/api/_search/car-0-s?query=id:" + car0.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(car0.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Car0.class);
        Car0 car01 = new Car0();
        car01.setId(1L);
        Car0 car02 = new Car0();
        car02.setId(car01.getId());
        assertThat(car01).isEqualTo(car02);
        car02.setId(2L);
        assertThat(car01).isNotEqualTo(car02);
        car01.setId(null);
        assertThat(car01).isNotEqualTo(car02);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Car0DTO.class);
        Car0DTO car0DTO1 = new Car0DTO();
        car0DTO1.setId(1L);
        Car0DTO car0DTO2 = new Car0DTO();
        assertThat(car0DTO1).isNotEqualTo(car0DTO2);
        car0DTO2.setId(car0DTO1.getId());
        assertThat(car0DTO1).isEqualTo(car0DTO2);
        car0DTO2.setId(2L);
        assertThat(car0DTO1).isNotEqualTo(car0DTO2);
        car0DTO1.setId(null);
        assertThat(car0DTO1).isNotEqualTo(car0DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(car0Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(car0Mapper.fromId(null)).isNull();
    }
}
