package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestApp;

import com.mycompany.myapp.domain.Car2;
import com.mycompany.myapp.repository.Car2Repository;
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

    @Autowired
    private Car2Repository car2Repository;

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
        Car2Resource car2Resource = new Car2Resource(car2Repository);
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
            .name(DEFAULT_NAME);
        return car2;
    }

    @Before
    public void initTest() {
        car2 = createEntity(em);
    }

    @Test
    @Transactional
    public void createCar2() throws Exception {
        int databaseSizeBeforeCreate = car2Repository.findAll().size();

        // Create the Car2
        restCar2MockMvc.perform(post("/api/car-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car2)))
            .andExpect(status().isCreated());

        // Validate the Car2 in the database
        List<Car2> car2List = car2Repository.findAll();
        assertThat(car2List).hasSize(databaseSizeBeforeCreate + 1);
        Car2 testCar2 = car2List.get(car2List.size() - 1);
        assertThat(testCar2.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCar2WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = car2Repository.findAll().size();

        // Create the Car2 with an existing ID
        car2.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCar2MockMvc.perform(post("/api/car-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car2)))
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
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
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
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
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
        int databaseSizeBeforeUpdate = car2Repository.findAll().size();

        // Update the car2
        Car2 updatedCar2 = car2Repository.findOne(car2.getId());
        updatedCar2
            .name(UPDATED_NAME);

        restCar2MockMvc.perform(put("/api/car-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCar2)))
            .andExpect(status().isOk());

        // Validate the Car2 in the database
        List<Car2> car2List = car2Repository.findAll();
        assertThat(car2List).hasSize(databaseSizeBeforeUpdate);
        Car2 testCar2 = car2List.get(car2List.size() - 1);
        assertThat(testCar2.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCar2() throws Exception {
        int databaseSizeBeforeUpdate = car2Repository.findAll().size();

        // Create the Car2

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCar2MockMvc.perform(put("/api/car-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car2)))
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
        int databaseSizeBeforeDelete = car2Repository.findAll().size();

        // Get the car2
        restCar2MockMvc.perform(delete("/api/car-2-s/{id}", car2.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Car2> car2List = car2Repository.findAll();
        assertThat(car2List).hasSize(databaseSizeBeforeDelete - 1);
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
}
