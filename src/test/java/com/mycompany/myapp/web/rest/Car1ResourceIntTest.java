package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestApp;

import com.mycompany.myapp.domain.Car1;
import com.mycompany.myapp.repository.Car1Repository;
import com.mycompany.myapp.service.Car1Service;
import com.mycompany.myapp.repository.search.Car1SearchRepository;
import com.mycompany.myapp.service.dto.Car1DTO;
import com.mycompany.myapp.service.mapper.Car1Mapper;
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
 * Test class for the Car1Resource REST controller.
 *
 * @see Car1Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
public class Car1ResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private Car1Repository car1Repository;

    @Autowired
    private Car1Mapper car1Mapper;

    @Autowired
    private Car1Service car1Service;

    @Autowired
    private Car1SearchRepository car1SearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCar1MockMvc;

    private Car1 car1;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Car1Resource car1Resource = new Car1Resource(car1Service);
        this.restCar1MockMvc = MockMvcBuilders.standaloneSetup(car1Resource)
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
    public static Car1 createEntity(EntityManager em) {
        Car1 car1 = new Car1()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return car1;
    }

    @Before
    public void initTest() {
        car1SearchRepository.deleteAll();
        car1 = createEntity(em);
    }

    @Test
    @Transactional
    public void createCar1() throws Exception {
        int databaseSizeBeforeCreate = car1Repository.findAll().size();

        // Create the Car1
        Car1DTO car1DTO = car1Mapper.toDto(car1);
        restCar1MockMvc.perform(post("/api/car-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car1DTO)))
            .andExpect(status().isCreated());

        // Validate the Car1 in the database
        List<Car1> car1List = car1Repository.findAll();
        assertThat(car1List).hasSize(databaseSizeBeforeCreate + 1);
        Car1 testCar1 = car1List.get(car1List.size() - 1);
        assertThat(testCar1.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCar1.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Car1 in Elasticsearch
        Car1 car1Es = car1SearchRepository.findOne(testCar1.getId());
        assertThat(car1Es).isEqualToComparingFieldByField(testCar1);
    }

    @Test
    @Transactional
    public void createCar1WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = car1Repository.findAll().size();

        // Create the Car1 with an existing ID
        car1.setId(1L);
        Car1DTO car1DTO = car1Mapper.toDto(car1);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCar1MockMvc.perform(post("/api/car-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car1DTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Car1> car1List = car1Repository.findAll();
        assertThat(car1List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCar1S() throws Exception {
        // Initialize the database
        car1Repository.saveAndFlush(car1);

        // Get all the car1List
        restCar1MockMvc.perform(get("/api/car-1-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(car1.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCar1() throws Exception {
        // Initialize the database
        car1Repository.saveAndFlush(car1);

        // Get the car1
        restCar1MockMvc.perform(get("/api/car-1-s/{id}", car1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(car1.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCar1() throws Exception {
        // Get the car1
        restCar1MockMvc.perform(get("/api/car-1-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCar1() throws Exception {
        // Initialize the database
        car1Repository.saveAndFlush(car1);
        car1SearchRepository.save(car1);
        int databaseSizeBeforeUpdate = car1Repository.findAll().size();

        // Update the car1
        Car1 updatedCar1 = car1Repository.findOne(car1.getId());
        updatedCar1
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        Car1DTO car1DTO = car1Mapper.toDto(updatedCar1);

        restCar1MockMvc.perform(put("/api/car-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car1DTO)))
            .andExpect(status().isOk());

        // Validate the Car1 in the database
        List<Car1> car1List = car1Repository.findAll();
        assertThat(car1List).hasSize(databaseSizeBeforeUpdate);
        Car1 testCar1 = car1List.get(car1List.size() - 1);
        assertThat(testCar1.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCar1.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Car1 in Elasticsearch
        Car1 car1Es = car1SearchRepository.findOne(testCar1.getId());
        assertThat(car1Es).isEqualToComparingFieldByField(testCar1);
    }

    @Test
    @Transactional
    public void updateNonExistingCar1() throws Exception {
        int databaseSizeBeforeUpdate = car1Repository.findAll().size();

        // Create the Car1
        Car1DTO car1DTO = car1Mapper.toDto(car1);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCar1MockMvc.perform(put("/api/car-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car1DTO)))
            .andExpect(status().isCreated());

        // Validate the Car1 in the database
        List<Car1> car1List = car1Repository.findAll();
        assertThat(car1List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCar1() throws Exception {
        // Initialize the database
        car1Repository.saveAndFlush(car1);
        car1SearchRepository.save(car1);
        int databaseSizeBeforeDelete = car1Repository.findAll().size();

        // Get the car1
        restCar1MockMvc.perform(delete("/api/car-1-s/{id}", car1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean car1ExistsInEs = car1SearchRepository.exists(car1.getId());
        assertThat(car1ExistsInEs).isFalse();

        // Validate the database is empty
        List<Car1> car1List = car1Repository.findAll();
        assertThat(car1List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCar1() throws Exception {
        // Initialize the database
        car1Repository.saveAndFlush(car1);
        car1SearchRepository.save(car1);

        // Search the car1
        restCar1MockMvc.perform(get("/api/_search/car-1-s?query=id:" + car1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(car1.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Car1.class);
        Car1 car11 = new Car1();
        car11.setId(1L);
        Car1 car12 = new Car1();
        car12.setId(car11.getId());
        assertThat(car11).isEqualTo(car12);
        car12.setId(2L);
        assertThat(car11).isNotEqualTo(car12);
        car11.setId(null);
        assertThat(car11).isNotEqualTo(car12);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Car1DTO.class);
        Car1DTO car1DTO1 = new Car1DTO();
        car1DTO1.setId(1L);
        Car1DTO car1DTO2 = new Car1DTO();
        assertThat(car1DTO1).isNotEqualTo(car1DTO2);
        car1DTO2.setId(car1DTO1.getId());
        assertThat(car1DTO1).isEqualTo(car1DTO2);
        car1DTO2.setId(2L);
        assertThat(car1DTO1).isNotEqualTo(car1DTO2);
        car1DTO1.setId(null);
        assertThat(car1DTO1).isNotEqualTo(car1DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(car1Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(car1Mapper.fromId(null)).isNull();
    }
}
