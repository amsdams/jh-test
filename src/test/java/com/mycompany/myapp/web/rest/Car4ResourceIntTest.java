package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestApp;

import com.mycompany.myapp.domain.Car4;
import com.mycompany.myapp.repository.Car4Repository;
import com.mycompany.myapp.service.Car4Service;
import com.mycompany.myapp.service.dto.Car4DTO;
import com.mycompany.myapp.service.mapper.Car4Mapper;
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
 * Test class for the Car4Resource REST controller.
 *
 * @see Car4Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
public class Car4ResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private Car4Repository car4Repository;

    @Autowired
    private Car4Mapper car4Mapper;

    @Autowired
    private Car4Service car4Service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCar4MockMvc;

    private Car4 car4;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Car4Resource car4Resource = new Car4Resource(car4Service);
        this.restCar4MockMvc = MockMvcBuilders.standaloneSetup(car4Resource)
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
    public static Car4 createEntity(EntityManager em) {
        Car4 car4 = new Car4()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return car4;
    }

    @Before
    public void initTest() {
        car4 = createEntity(em);
    }

    @Test
    @Transactional
    public void createCar4() throws Exception {
        int databaseSizeBeforeCreate = car4Repository.findAll().size();

        // Create the Car4
        Car4DTO car4DTO = car4Mapper.toDto(car4);
        restCar4MockMvc.perform(post("/api/car-4-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car4DTO)))
            .andExpect(status().isCreated());

        // Validate the Car4 in the database
        List<Car4> car4List = car4Repository.findAll();
        assertThat(car4List).hasSize(databaseSizeBeforeCreate + 1);
        Car4 testCar4 = car4List.get(car4List.size() - 1);
        assertThat(testCar4.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCar4.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCar4WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = car4Repository.findAll().size();

        // Create the Car4 with an existing ID
        car4.setId(1L);
        Car4DTO car4DTO = car4Mapper.toDto(car4);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCar4MockMvc.perform(post("/api/car-4-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car4DTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Car4> car4List = car4Repository.findAll();
        assertThat(car4List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCar4S() throws Exception {
        // Initialize the database
        car4Repository.saveAndFlush(car4);

        // Get all the car4List
        restCar4MockMvc.perform(get("/api/car-4-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(car4.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCar4() throws Exception {
        // Initialize the database
        car4Repository.saveAndFlush(car4);

        // Get the car4
        restCar4MockMvc.perform(get("/api/car-4-s/{id}", car4.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(car4.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCar4() throws Exception {
        // Get the car4
        restCar4MockMvc.perform(get("/api/car-4-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCar4() throws Exception {
        // Initialize the database
        car4Repository.saveAndFlush(car4);
        int databaseSizeBeforeUpdate = car4Repository.findAll().size();

        // Update the car4
        Car4 updatedCar4 = car4Repository.findOne(car4.getId());
        updatedCar4
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        Car4DTO car4DTO = car4Mapper.toDto(updatedCar4);

        restCar4MockMvc.perform(put("/api/car-4-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car4DTO)))
            .andExpect(status().isOk());

        // Validate the Car4 in the database
        List<Car4> car4List = car4Repository.findAll();
        assertThat(car4List).hasSize(databaseSizeBeforeUpdate);
        Car4 testCar4 = car4List.get(car4List.size() - 1);
        assertThat(testCar4.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCar4.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCar4() throws Exception {
        int databaseSizeBeforeUpdate = car4Repository.findAll().size();

        // Create the Car4
        Car4DTO car4DTO = car4Mapper.toDto(car4);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCar4MockMvc.perform(put("/api/car-4-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car4DTO)))
            .andExpect(status().isCreated());

        // Validate the Car4 in the database
        List<Car4> car4List = car4Repository.findAll();
        assertThat(car4List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCar4() throws Exception {
        // Initialize the database
        car4Repository.saveAndFlush(car4);
        int databaseSizeBeforeDelete = car4Repository.findAll().size();

        // Get the car4
        restCar4MockMvc.perform(delete("/api/car-4-s/{id}", car4.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Car4> car4List = car4Repository.findAll();
        assertThat(car4List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Car4.class);
        Car4 car41 = new Car4();
        car41.setId(1L);
        Car4 car42 = new Car4();
        car42.setId(car41.getId());
        assertThat(car41).isEqualTo(car42);
        car42.setId(2L);
        assertThat(car41).isNotEqualTo(car42);
        car41.setId(null);
        assertThat(car41).isNotEqualTo(car42);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Car4DTO.class);
        Car4DTO car4DTO1 = new Car4DTO();
        car4DTO1.setId(1L);
        Car4DTO car4DTO2 = new Car4DTO();
        assertThat(car4DTO1).isNotEqualTo(car4DTO2);
        car4DTO2.setId(car4DTO1.getId());
        assertThat(car4DTO1).isEqualTo(car4DTO2);
        car4DTO2.setId(2L);
        assertThat(car4DTO1).isNotEqualTo(car4DTO2);
        car4DTO1.setId(null);
        assertThat(car4DTO1).isNotEqualTo(car4DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(car4Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(car4Mapper.fromId(null)).isNull();
    }
}
