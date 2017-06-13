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
import com.mycompany.myapp.domain.Car0;
import com.mycompany.myapp.domain.Car0;
import com.mycompany.myapp.domain.Owner0;
import com.mycompany.myapp.repository.Owner0Repository;
import com.mycompany.myapp.service.dto.Car0DTO;
import com.mycompany.myapp.service.dto.Owner0DTO;
import com.mycompany.myapp.service.mapper.Car0Mapper;
import com.mycompany.myapp.service.mapper.Owner0Mapper;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
@Transactional
public class Owner0ServiceIntTest {

	@Autowired
	private Owner0Repository owner0Repository;

	@Autowired
	private Owner0Service owner0Service;

	@Autowired
	private Owner0Mapper owner0Mapper;

	@Autowired
	private Car0Service car0Service;

	@Autowired
	private Car0Mapper car0Mapper;

	@Test
	public void assertThatOwnerHasCars() {
		Owner0DTO Owner0DTO = owner0Service.findOne(1001L);
		Owner0 owner0 = owner0Mapper.toEntity(Owner0DTO);
		System.out.printf("found owner0 %s\r\n", owner0.toString());
		assertThat(!owner0.getDescription().equals(null));
		for (Car0 car0 : owner0.getCar0S()) {
			System.out.printf("found car0 %s\r\n", car0.toString());
			assertThat(!car0.getDescription().equals(null));
		}
	}

	@Test(expected = NullPointerException.class)
	public void assertThatCarHasOwners() {
		Car0DTO Car0DTO = car0Service.findOne(959L);
		Car0 car0 = car0Mapper.toEntity(Car0DTO);
		System.out.printf("found car0 %s\r\n", car0.toString());
		assertThat(!car0.getDescription().equals(null));;
		assertThat(!car0.getOwner0().getDescription().equals(null));
	}
}
