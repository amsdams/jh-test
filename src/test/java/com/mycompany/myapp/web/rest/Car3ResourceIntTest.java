package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestApp;

import com.mycompany.myapp.domain.Car3;
import com.mycompany.myapp.repository.Car3Repository;
import com.mycompany.myapp.service.Car3Service;
import com.mycompany.myapp.repository.search.Car3SearchRepository;
import com.mycompany.myapp.service.dto.Car3DTO;
import com.mycompany.myapp.service.mapper.Car3Mapper;
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
 * Test class for the Car3Resource REST controller.
 *
 * @see Car3Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
public class Car3ResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private Car3Repository car3Repository;

    @Autowired
    private Car3Mapper car3Mapper;

    @Autowired
    private Car3Service car3Service;

    @Autowired
    private Car3SearchRepository car3SearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCar3MockMvc;

    private Car3 car3;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Car3Resource car3Resource = new Car3Resource(car3Service);
        this.restCar3MockMvc = MockMvcBuilders.standaloneSetup(car3Resource)
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
    public static Car3 createEntity(EntityManager em) {
        Car3 car3 = new Car3()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return car3;
    }

    @Before
    public void initTest() {
        car3SearchRepository.deleteAll();
        car3 = createEntity(em);
    }

    @Test
    @Transactional
    public void createCar3() throws Exception {
        int databaseSizeBeforeCreate = car3Repository.findAll().size();

        // Create the Car3
        Car3DTO car3DTO = car3Mapper.toDto(car3);
        restCar3MockMvc.perform(post("/api/car-3-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car3DTO)))
            .andExpect(status().isCreated());

        // Validate the Car3 in the database
        List<Car3> car3List = car3Repository.findAll();
        assertThat(car3List).hasSize(databaseSizeBeforeCreate + 1);
        Car3 testCar3 = car3List.get(car3List.size() - 1);
        assertThat(testCar3.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCar3.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Car3 in Elasticsearch
        Car3 car3Es = car3SearchRepository.findOne(testCar3.getId());
        assertThat(car3Es).isEqualToComparingFieldByField(testCar3);
    }

    @Test
    @Transactional
    public void createCar3WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = car3Repository.findAll().size();

        // Create the Car3 with an existing ID
        car3.setId(1L);
        Car3DTO car3DTO = car3Mapper.toDto(car3);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCar3MockMvc.perform(post("/api/car-3-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car3DTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Car3> car3List = car3Repository.findAll();
        assertThat(car3List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCar3S() throws Exception {
        // Initialize the database
        car3Repository.saveAndFlush(car3);

        // Get all the car3List
        restCar3MockMvc.perform(get("/api/car-3-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(car3.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCar3() throws Exception {
        // Initialize the database
        car3Repository.saveAndFlush(car3);

        // Get the car3
        restCar3MockMvc.perform(get("/api/car-3-s/{id}", car3.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(car3.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCar3() throws Exception {
        // Get the car3
        restCar3MockMvc.perform(get("/api/car-3-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCar3() throws Exception {
        // Initialize the database
        car3Repository.saveAndFlush(car3);
        car3SearchRepository.save(car3);
        int databaseSizeBeforeUpdate = car3Repository.findAll().size();

        // Update the car3
        Car3 updatedCar3 = car3Repository.findOne(car3.getId());
        updatedCar3
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        Car3DTO car3DTO = car3Mapper.toDto(updatedCar3);

        restCar3MockMvc.perform(put("/api/car-3-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car3DTO)))
            .andExpect(status().isOk());

        // Validate the Car3 in the database
        List<Car3> car3List = car3Repository.findAll();
        assertThat(car3List).hasSize(databaseSizeBeforeUpdate);
        Car3 testCar3 = car3List.get(car3List.size() - 1);
        assertThat(testCar3.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCar3.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Car3 in Elasticsearch
        Car3 car3Es = car3SearchRepository.findOne(testCar3.getId());
        assertThat(car3Es).isEqualToComparingFieldByField(testCar3);
    }

    @Test
    @Transactional
    public void updateNonExistingCar3() throws Exception {
        int databaseSizeBeforeUpdate = car3Repository.findAll().size();

        // Create the Car3
        Car3DTO car3DTO = car3Mapper.toDto(car3);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCar3MockMvc.perform(put("/api/car-3-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car3DTO)))
            .andExpect(status().isCreated());

        // Validate the Car3 in the database
        List<Car3> car3List = car3Repository.findAll();
        assertThat(car3List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCar3() throws Exception {
        // Initialize the database
        car3Repository.saveAndFlush(car3);
        car3SearchRepository.save(car3);
        int databaseSizeBeforeDelete = car3Repository.findAll().size();

        // Get the car3
        restCar3MockMvc.perform(delete("/api/car-3-s/{id}", car3.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean car3ExistsInEs = car3SearchRepository.exists(car3.getId());
        assertThat(car3ExistsInEs).isFalse();

        // Validate the database is empty
        List<Car3> car3List = car3Repository.findAll();
        assertThat(car3List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCar3() throws Exception {
        // Initialize the database
        car3Repository.saveAndFlush(car3);
        car3SearchRepository.save(car3);

        // Search the car3
        restCar3MockMvc.perform(get("/api/_search/car-3-s?query=id:" + car3.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(car3.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Car3.class);
        Car3 car31 = new Car3();
        car31.setId(1L);
        Car3 car32 = new Car3();
        car32.setId(car31.getId());
        assertThat(car31).isEqualTo(car32);
        car32.setId(2L);
        assertThat(car31).isNotEqualTo(car32);
        car31.setId(null);
        assertThat(car31).isNotEqualTo(car32);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Car3DTO.class);
        Car3DTO car3DTO1 = new Car3DTO();
        car3DTO1.setId(1L);
        Car3DTO car3DTO2 = new Car3DTO();
        assertThat(car3DTO1).isNotEqualTo(car3DTO2);
        car3DTO2.setId(car3DTO1.getId());
        assertThat(car3DTO1).isEqualTo(car3DTO2);
        car3DTO2.setId(2L);
        assertThat(car3DTO1).isNotEqualTo(car3DTO2);
        car3DTO1.setId(null);
        assertThat(car3DTO1).isNotEqualTo(car3DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(car3Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(car3Mapper.fromId(null)).isNull();
    }
}
