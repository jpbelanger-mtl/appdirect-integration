package com.belanger.appdirect.integration.web.controller.app;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.mockito.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.belanger.appdirect.integration.AppdirectIntegrationApplication;
import com.belanger.appdirect.integration.domain.data.ClientProfile;
import com.belanger.appdirect.integration.domain.data.ClientProfileFixtures;
import com.belanger.appdirect.integration.service.repository.ClientProfileRepository;
import com.jayway.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppdirectIntegrationApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles("junit")
public class CompaniesControllerTest {

	@Value("${local.server.port}")
	int port;

	@InjectMocks
	CompaniesController controller;

	@Mock
	ClientProfileRepository repository;

	@Autowired
	ClientProfileFixtures fixtures;

	@Before
    public void setUp() {
		RestAssured.port = port;
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void emptyList()
	{
		Page<ClientProfile> companies = new PageImpl<>(new ArrayList<>());
		Mockito.when(repository.findAll(any(Pageable.class))).thenReturn(companies);

		//simple test to validate that the mapping havn't changed
		given().
			standaloneSetup(controller).
		when().
			get("/app/companies").
		then().
			statusCode(HttpStatus.SC_OK).
			body("", Matchers.hasSize(0));
	}

	@Test
	public void populatedList()
	{
		List<ClientProfile> list = fixtures.getList();
		Page<ClientProfile> companies = new PageImpl<>(list);
		Mockito.when(repository.findAll(any(Pageable.class))).thenReturn(companies);

		//simple test to validate that the mapping havn't changed
		given().
			standaloneSetup(controller).
		when().
			get("/app/companies").
		then().
			statusCode(HttpStatus.SC_OK).
			body("", Matchers.hasSize(2)).
			body("id", org.hamcrest.Matchers.hasItems(list.get(0).getId(), list.get(1).getId()));
	}
}
