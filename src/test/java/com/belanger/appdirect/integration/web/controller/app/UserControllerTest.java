package com.belanger.appdirect.integration.web.controller.app;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.mockito.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.auth.BasicUserPrincipal;
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
import com.belanger.appdirect.integration.domain.data.User;
import com.belanger.appdirect.integration.domain.data.UserFixtures;
import com.belanger.appdirect.integration.service.repository.UserRepository;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppdirectIntegrationApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles("junit")
public class UserControllerTest {

	@Value("${local.server.port}")
	int port;

	@InjectMocks
	UserController controller;
	
	@Mock
	UserRepository repository;
	
	@Autowired
	UserFixtures fixtures;

	@Before
    public void setUp() {
		RestAssured.port = port;
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void emptyList()
	{
		Page<User> users = new PageImpl<>(new ArrayList<>());
		Mockito.when(repository.findAll(any(Pageable.class))).thenReturn(users);

		//simple test to validate that the mapping havn't changed
		given().
			standaloneSetup(controller).
		when().
			get("/app/users").
		then().
			statusCode(HttpStatus.SC_OK).
			body("", Matchers.hasSize(0));
	}

	@Test
	public void populatedList()
	{
		List<User> list = fixtures.getList();
		Page<User> users = new PageImpl<>(list);
		Mockito.when(repository.findAll(any(Pageable.class))).thenReturn(users);

		//simple test to validate that the mapping havn't changed
		given().
			standaloneSetup(controller).
		when().
			get("/app/users").
		then().
			statusCode(HttpStatus.SC_OK).
			body("", Matchers.hasSize(2)).
			body("id", org.hamcrest.Matchers.hasItems(list.get(0).getId(), list.get(1).getId()));
	}
	
	@Test
	public void infoEmpty()
	{
		//simple test to validate that the mapping havn't changed
		given().
			standaloneSetup(controller).
		when().
			get("/app/info").
		then().
			statusCode(HttpStatus.SC_NO_CONTENT);
	}

	@Test
	public void infoWithUser()
	{
		String username = "test";
		//simple test to validate that the mapping havn't changed
		given().
			standaloneSetup(controller).
			auth().principal(new BasicUserPrincipal(username)).
		when().
			get("/app/info").
		then().
			statusCode(HttpStatus.SC_OK).
			body("name", Matchers.equalTo(username));
	}
}
