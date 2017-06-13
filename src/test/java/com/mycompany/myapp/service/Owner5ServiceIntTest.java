package com.mycompany.myapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.myapp.TestApp;
import com.mycompany.myapp.domain.Car5;
import com.mycompany.myapp.domain.Car5;
import com.mycompany.myapp.domain.Owner5;
import com.mycompany.myapp.repository.Owner5Repository;
import com.mycompany.myapp.service.dto.Car5DTO;
import com.mycompany.myapp.service.dto.Owner5DTO;
import com.mycompany.myapp.service.mapper.Car5Mapper;
import com.mycompany.myapp.service.mapper.Owner5Mapper;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
@Transactional
public class Owner5ServiceIntTest {

	@Autowired
	private Owner5Repository owner5Repository;

	@Autowired
	private Owner5Service owner5Service;

	@Autowired
	private Owner5Mapper owner5Mapper;

	@Autowired
	private Car5Service car5Service;

	@Autowired
	private Car5Mapper car5Mapper;

	@Test
	public void assertThatOwnerHasCars() {
		Owner5DTO owner5DTO = owner5Service.findOne(1001L);
		Owner5 owner5 = owner5Mapper.toEntity(owner5DTO);
		System.out.printf("found owner5 %s\r\n", owner5.toString());
		assertThat(!owner5.getDescription().equals(null));
		for (Car5 car5 : owner5.getCar5S()) {
			System.out.printf("found car5 %s\r\n", car5.toString());
			assertThat(!car5.getDescription().equals(null));
		}
	}

	@Test
	public void assertThatCarHasOwners() {
		Car5DTO Car5DTO = car5Service.findOne(959L);
		Car5 car5 = car5Mapper.toEntity(Car5DTO);
		System.out.printf("found car5 %s\r\n", car5.toString());
		assertThat(!car5.getDescription().equals(null));
		for (Owner5 owner5 : car5.getOwner5S()) {
			System.out.printf("found owner5 %s\r\n", owner5.toString());
			assertThat(!owner5.getDescription().equals(null));
		}
	}

}
