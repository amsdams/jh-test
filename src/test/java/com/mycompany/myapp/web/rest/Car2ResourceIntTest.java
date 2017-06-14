package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestApp;

import com.mycompany.myapp.domain.Car2;
import com.mycompany.myapp.repository.Car2Repository;
import com.mycompany.myapp.service.Car2Service;
import com.mycompany.myapp.repository.search.Car2SearchRepository;
import com.mycompany.myapp.service.dto.Car2DTO;
import com.mycompany.myapp.service.mapper.Car2Mapper;
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
 * Test class for the Car2Resource REST controller.
 *
 * @see Car2Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
public class Car2ResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private Car2Repository car2Repository;

    @Autowired
    private Car2Mapper car2Mapper;

    @Autowired
    private Car2Service car2Service;

    @Autowired
    private Car2SearchRepository car2SearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCar2MockMvc;

    private Car2 car2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Car2Resource car2Resource = new Car2Resource(car2Service);
        this.restCar2MockMvc = MockMvcBuilders.standaloneSetup(car2Resource)
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
    public static Car2 createEntity(EntityManager em) {
        Car2 car2 = new Car2()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return car2;
    }

    @Before
    public void initTest() {
        car2SearchRepository.deleteAll();
        car2 = createEntity(em);
    }

    @Test
    @Transactional
    public void createCar2() throws Exception {
        int databaseSizeBeforeCreate = car2Repository.findAll().size();

        // Create the Car2
        Car2DTO car2DTO = car2Mapper.toDto(car2);
        restCar2MockMvc.perform(post("/api/car-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car2DTO)))
            .andExpect(status().isCreated());

        // Validate the Car2 in the database
        List<Car2> car2List = car2Repository.findAll();
        assertThat(car2List).hasSize(databaseSizeBeforeCreate + 1);
        Car2 testCar2 = car2List.get(car2List.size() - 1);
        assertThat(testCar2.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCar2.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Car2 in Elasticsearch
        Car2 car2Es = car2SearchRepository.findOne(testCar2.getId());
        assertThat(car2Es).isEqualToComparingFieldByField(testCar2);
    }

    @Test
    @Transactional
    public void createCar2WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = car2Repository.findAll().size();

        // Create the Car2 with an existing ID
        car2.setId(1L);
        Car2DTO car2DTO = car2Mapper.toDto(car2);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCar2MockMvc.perform(post("/api/car-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car2DTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Car2> car2List = car2Repository.findAll();
        assertThat(car2List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCar2S() throws Exception {
        // Initialize the database
        car2Repository.saveAndFlush(car2);

        // Get all the car2List
        restCar2MockMvc.perform(get("/api/car-2-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(car2.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCar2() throws Exception {
        // Initialize the database
        car2Repository.saveAndFlush(car2);

        // Get the car2
        restCar2MockMvc.perform(get("/api/car-2-s/{id}", car2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(car2.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCar2() throws Exception {
        // Get the car2
        restCar2MockMvc.perform(get("/api/car-2-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCar2() throws Exception {
        // Initialize the database
        car2Repository.saveAndFlush(car2);
        car2SearchRepository.save(car2);
        int databaseSizeBeforeUpdate = car2Repository.findAll().size();

        // Update the car2
        Car2 updatedCar2 = car2Repository.findOne(car2.getId());
        updatedCar2
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        Car2DTO car2DTO = car2Mapper.toDto(updatedCar2);

        restCar2MockMvc.perform(put("/api/car-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car2DTO)))
            .andExpect(status().isOk());

        // Validate the Car2 in the database
        List<Car2> car2List = car2Repository.findAll();
        assertThat(car2List).hasSize(databaseSizeBeforeUpdate);
        Car2 testCar2 = car2List.get(car2List.size() - 1);
        assertThat(testCar2.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCar2.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Car2 in Elasticsearch
        Car2 car2Es = car2SearchRepository.findOne(testCar2.getId());
        assertThat(car2Es).isEqualToComparingFieldByField(testCar2);
    }

    @Test
    @Transactional
    public void updateNonExistingCar2() throws Exception {
        int databaseSizeBeforeUpdate = car2Repository.findAll().size();

        // Create the Car2
        Car2DTO car2DTO = car2Mapper.toDto(car2);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCar2MockMvc.perform(put("/api/car-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car2DTO)))
            .andExpect(status().isCreated());

        // Validate the Car2 in the database
        List<Car2> car2List = car2Repository.findAll();
        assertThat(car2List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCar2() throws Exception {
        // Initialize the database
        car2Repository.saveAndFlush(car2);
        car2SearchRepository.save(car2);
        int databaseSizeBeforeDelete = car2Repository.findAll().size();

        // Get the car2
        restCar2MockMvc.perform(delete("/api/car-2-s/{id}", car2.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean car2ExistsInEs = car2SearchRepository.exists(car2.getId());
        assertThat(car2ExistsInEs).isFalse();

        // Validate the database is empty
        List<Car2> car2List = car2Repository.findAll();
        assertThat(car2List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCar2() throws Exception {
        // Initialize the database
        car2Repository.saveAndFlush(car2);
        car2SearchRepository.save(car2);

        // Search the car2
        restCar2MockMvc.perform(get("/api/_search/car-2-s?query=id:" + car2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(car2.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Car2.class);
        Car2 car21 = new Car2();
        car21.setId(1L);
        Car2 car22 = new Car2();
        car22.setId(car21.getId());
        assertThat(car21).isEqualTo(car22);
        car22.setId(2L);
        assertThat(car21).isNotEqualTo(car22);
        car21.setId(null);
        assertThat(car21).isNotEqualTo(car22);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Car2DTO.class);
        Car2DTO car2DTO1 = new Car2DTO();
        car2DTO1.setId(1L);
        Car2DTO car2DTO2 = new Car2DTO();
        assertThat(car2DTO1).isNotEqualTo(car2DTO2);
        car2DTO2.setId(car2DTO1.getId());
        assertThat(car2DTO1).isEqualTo(car2DTO2);
        car2DTO2.setId(2L);
        assertThat(car2DTO1).isNotEqualTo(car2DTO2);
        car2DTO1.setId(null);
        assertThat(car2DTO1).isNotEqualTo(car2DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(car2Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(car2Mapper.fromId(null)).isNull();
    }
}
