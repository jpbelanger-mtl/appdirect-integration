package com.belanger.appdirect.integration.web.controller.api;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.belanger.appdirect.integration.AppdirectIntegrationApplication;
import com.belanger.appdirect.integration.domain.vo.AppDirectEvent;
import com.belanger.appdirect.integration.domain.vo.AppDirectNotificationResponse;
import com.belanger.appdirect.integration.service.AppDirectSubscriptionService;
import com.belanger.appdirect.integration.utils.EventFetchHelper;
import com.belanger.appdirect.integration.web.controller.api.SubscriptionController;
import com.jayway.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppdirectIntegrationApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles("junit")
public class SubscriptionControllerTest {

	@Value("${local.server.port}")
	int port;

	@Mock
	EventFetchHelper eventFetchHelper;

	@Mock
	AppDirectSubscriptionService subscriptionService;

	@InjectMocks
	SubscriptionController controller;

	String url = "http://test.com/dummy";
	AppDirectEvent event;
	UUID uuid;

	@Before
    public void setUp() {
		RestAssured.port = port;
		MockitoAnnotations.initMocks(this);
		uuid = UUID.randomUUID();

		//Reset the mocks
		event = new AppDirectEvent();
	}

	@Test
	public void createSuccess()
	{
		Mockito.when(eventFetchHelper.fetchEvent(url)).thenReturn(event);
		Mockito.when(subscriptionService.create(event)).thenReturn(new AppDirectNotificationResponse().successful(uuid.toString()));

		given().
				standaloneSetup(controller).
				param("eventUrl", url).
		when().
				get("/api/subscription/create").
		then().
				statusCode(HttpStatus.SC_OK).
				body("success", equalTo(Boolean.TRUE)).
				body("accountIdentifier", equalTo(uuid.toString()));
	}

	@Test
	public void updateSuccess()
	{
		Mockito.when(eventFetchHelper.fetchEvent(url)).thenReturn(event);
		Mockito.when(subscriptionService.update(event)).thenReturn(new AppDirectNotificationResponse().successful(uuid.toString()));

		given().
				standaloneSetup(controller).
				param("eventUrl", url).
		when().
				get("/api/subscription/update").
		then().
				statusCode(HttpStatus.SC_OK).
				body("success", equalTo(Boolean.TRUE)).
				body("accountIdentifier", equalTo(uuid.toString()));
	}

	@Test
	public void cancelSuccess()
	{
		Mockito.when(eventFetchHelper.fetchEvent(url)).thenReturn(event);
		Mockito.when(subscriptionService.cancel(event)).thenReturn(new AppDirectNotificationResponse().successful(uuid.toString()));

		given().
				standaloneSetup(controller).
				param("eventUrl", url).
		when().
				get("/api/subscription/cancel").
		then().
				statusCode(HttpStatus.SC_OK).
				body("success", equalTo(Boolean.TRUE)).
				body("accountIdentifier", equalTo(uuid.toString()));
	}

	@Test
	public void statusSuccess()
	{
		Mockito.when(eventFetchHelper.fetchEvent(url)).thenReturn(event);
		Mockito.when(subscriptionService.status(event)).thenReturn(new AppDirectNotificationResponse().successful(uuid.toString()));

		given().
				standaloneSetup(controller).
				param("eventUrl", url).
		when().
				get("/api/subscription/status").
		then().
				statusCode(HttpStatus.SC_OK).
				body("success", equalTo(Boolean.TRUE));
	}

	@Test
	public void createWithEventUrlMissing()
	{
		given().
				standaloneSetup(controller).
		when().
				
				get("/api/subscription/create").
		then().
				statusCode(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void updateWithEventUrlMissing()
	{
		given().
				standaloneSetup(controller).
		when().
				
				get("/api/subscription/update").
		then().
				statusCode(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void cancelWithEventUrlMissing()
	{
		given().
				standaloneSetup(controller).
		when().
				
				get("/api/subscription/cancel").
		then().
				statusCode(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void statusWithEventUrlMissing()
	{
		given().
				standaloneSetup(controller).
		when().
				
				get("/api/subscription/status").
		then().
				statusCode(HttpStatus.SC_BAD_REQUEST);
	}

}
