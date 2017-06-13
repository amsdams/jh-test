package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestApp;

import com.mycompany.myapp.domain.Car5;
import com.mycompany.myapp.repository.Car5Repository;
import com.mycompany.myapp.service.Car5Service;
import com.mycompany.myapp.service.dto.Car5DTO;
import com.mycompany.myapp.service.mapper.Car5Mapper;
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
 * Test class for the Car5Resource REST controller.
 *
 * @see Car5Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
public class Car5ResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private Car5Repository car5Repository;

    @Autowired
    private Car5Mapper car5Mapper;

    @Autowired
    private Car5Service car5Service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCar5MockMvc;

    private Car5 car5;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Car5Resource car5Resource = new Car5Resource(car5Service);
        this.restCar5MockMvc = MockMvcBuilders.standaloneSetup(car5Resource)
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
    public static Car5 createEntity(EntityManager em) {
        Car5 car5 = new Car5()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return car5;
    }

    @Before
    public void initTest() {
        car5 = createEntity(em);
    }

    @Test
    @Transactional
    public void createCar5() throws Exception {
        int databaseSizeBeforeCreate = car5Repository.findAll().size();

        // Create the Car5
        Car5DTO car5DTO = car5Mapper.toDto(car5);
        restCar5MockMvc.perform(post("/api/car-5-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car5DTO)))
            .andExpect(status().isCreated());

        // Validate the Car5 in the database
        List<Car5> car5List = car5Repository.findAll();
        assertThat(car5List).hasSize(databaseSizeBeforeCreate + 1);
        Car5 testCar5 = car5List.get(car5List.size() - 1);
        assertThat(testCar5.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCar5.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCar5WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = car5Repository.findAll().size();

        // Create the Car5 with an existing ID
        car5.setId(1L);
        Car5DTO car5DTO = car5Mapper.toDto(car5);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCar5MockMvc.perform(post("/api/car-5-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car5DTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Car5> car5List = car5Repository.findAll();
        assertThat(car5List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCar5S() throws Exception {
        // Initialize the database
        car5Repository.saveAndFlush(car5);

        // Get all the car5List
        restCar5MockMvc.perform(get("/api/car-5-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(car5.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCar5() throws Exception {
        // Initialize the database
        car5Repository.saveAndFlush(car5);

        // Get the car5
        restCar5MockMvc.perform(get("/api/car-5-s/{id}", car5.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(car5.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCar5() throws Exception {
        // Get the car5
        restCar5MockMvc.perform(get("/api/car-5-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCar5() throws Exception {
        // Initialize the database
        car5Repository.saveAndFlush(car5);
        int databaseSizeBeforeUpdate = car5Repository.findAll().size();

        // Update the car5
        Car5 updatedCar5 = car5Repository.findOne(car5.getId());
        updatedCar5
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        Car5DTO car5DTO = car5Mapper.toDto(updatedCar5);

        restCar5MockMvc.perform(put("/api/car-5-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car5DTO)))
            .andExpect(status().isOk());

        // Validate the Car5 in the database
        List<Car5> car5List = car5Repository.findAll();
        assertThat(car5List).hasSize(databaseSizeBeforeUpdate);
        Car5 testCar5 = car5List.get(car5List.size() - 1);
        assertThat(testCar5.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCar5.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCar5() throws Exception {
        int databaseSizeBeforeUpdate = car5Repository.findAll().size();

        // Create the Car5
        Car5DTO car5DTO = car5Mapper.toDto(car5);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCar5MockMvc.perform(put("/api/car-5-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car5DTO)))
            .andExpect(status().isCreated());

        // Validate the Car5 in the database
        List<Car5> car5List = car5Repository.findAll();
        assertThat(car5List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCar5() throws Exception {
        // Initialize the database
        car5Repository.saveAndFlush(car5);
        int databaseSizeBeforeDelete = car5Repository.findAll().size();

        // Get the car5
        restCar5MockMvc.perform(delete("/api/car-5-s/{id}", car5.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Car5> car5List = car5Repository.findAll();
        assertThat(car5List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Car5.class);
        Car5 car51 = new Car5();
        car51.setId(1L);
        Car5 car52 = new Car5();
        car52.setId(car51.getId());
        assertThat(car51).isEqualTo(car52);
        car52.setId(2L);
        assertThat(car51).isNotEqualTo(car52);
        car51.setId(null);
        assertThat(car51).isNotEqualTo(car52);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Car5DTO.class);
        Car5DTO car5DTO1 = new Car5DTO();
        car5DTO1.setId(1L);
        Car5DTO car5DTO2 = new Car5DTO();
        assertThat(car5DTO1).isNotEqualTo(car5DTO2);
        car5DTO2.setId(car5DTO1.getId());
        assertThat(car5DTO1).isEqualTo(car5DTO2);
        car5DTO2.setId(2L);
        assertThat(car5DTO1).isNotEqualTo(car5DTO2);
        car5DTO1.setId(null);
        assertThat(car5DTO1).isNotEqualTo(car5DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(car5Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(car5Mapper.fromId(null)).isNull();
    }
}
