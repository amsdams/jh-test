package com.mycompany.myapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.myapp.TestApp;
import com.mycompany.myapp.domain.Car1;
import com.mycompany.myapp.domain.Owner1;
import com.mycompany.myapp.repository.Owner1Repository;
import com.mycompany.myapp.service.dto.Car1DTO;
import com.mycompany.myapp.service.dto.Owner1DTO;
import com.mycompany.myapp.service.mapper.Car1Mapper;
import com.mycompany.myapp.service.mapper.Owner1Mapper;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
@Transactional
public class Owner1ServiceIntTest {

	@Autowired
	private Owner1Repository owner1Repository;

	@Autowired
	private Owner1Service owner1Service;

	@Autowired
	private Owner1Mapper owner1Mapper;

	@Autowired
	private Car1Service car1Service;

	@Autowired
	private Car1Mapper car1Mapper;

	@Test
	public void assertThatOwnerHasCars() {
		Owner1DTO Owner1DTO = owner1Service.findOne(1001L);
		Owner1 owner1 = owner1Mapper.toEntity(Owner1DTO);
		System.out.printf("found owner1 %s\r\n", owner1.toString());
		assertThat(!owner1.getDescription().equals(null));
	}

	@Test(expected = NullPointerException.class)
	public void assertThatCarHasOwners() {
		Car1DTO Car1DTO = car1Service.findOne(959L);
		Car1 car1 = car1Mapper.toEntity(Car1DTO);
		System.out.printf("found car1 %s\r\n", car1.toString());
		assertThat(!car1.getDescription().equals(null));
		assertThat(!car1.getOwner1().getDescription().equals(null));
	}

}
