package com.belanger.appdirect.integration.web.controller.login;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.*;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.belanger.appdirect.integration.AppdirectIntegrationApplication;
import com.belanger.appdirect.integration.web.controller.login.LoginController;
import com.jayway.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppdirectIntegrationApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles("junit")
public class LoginControllerTest {

	@Value("${local.server.port}")
	int port;

	@InjectMocks
	LoginController controller;

	@Before
    public void setUp() {
		RestAssured.port = port;
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void simpleTestLogin()
	{
		//simple test to validate that the mapping havn't changed
		given().
			standaloneSetup(controller).
		when().
			get("/login").
		then().
			statusCode(HttpStatus.SC_OK);
	}

	@Test
	public void simpleTestDenied()
	{
		//simple test to validate that the mapping havn't changed
		given().
			standaloneSetup(controller).
		when().
			get("/denied").
		then().
			statusCode(HttpStatus.SC_OK);
	}
}
