package com.belanger.appdirect.integration.web.controller.app;

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
import com.jayway.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppdirectIntegrationApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles("junit")
public class HomeControllerTest {

	@Value("${local.server.port}")
	int port;

	@InjectMocks
	HomeController controller;

	@Before
    public void setUp() {
		RestAssured.port = port;
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void simpleHomeRedirect()
	{
		//simple test to validate that the mapping havn't changed
		given().
			standaloneSetup(controller).
		when().
			get("/app/home").
		then().
			statusCode(HttpStatus.SC_MOVED_TEMPORARILY).
			header("location", "/index.html");
	}

}
