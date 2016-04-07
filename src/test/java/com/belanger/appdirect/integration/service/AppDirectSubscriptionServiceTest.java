package com.belanger.appdirect.integration.service;

import static org.mockito.Matchers.*;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.belanger.appdirect.integration.AppdirectIntegrationApplication;
import com.belanger.appdirect.integration.domain.data.ClientProfile;
import com.belanger.appdirect.integration.domain.data.ClientProfileFixtures;
import com.belanger.appdirect.integration.domain.data.User;
import com.belanger.appdirect.integration.domain.vo.AppDirectAccountStatus;
import com.belanger.appdirect.integration.domain.vo.AppDirectErrorCodes;
import com.belanger.appdirect.integration.domain.vo.AppDirectEvent;
import com.belanger.appdirect.integration.domain.vo.AppDirectEventFixtures;
import com.belanger.appdirect.integration.domain.vo.AppDirectEventType;
import com.belanger.appdirect.integration.domain.vo.AppDirectFlag;
import com.belanger.appdirect.integration.domain.vo.AppDirectNotificationResponse;
import com.belanger.appdirect.integration.service.repository.ClientProfileRepository;
import com.belanger.appdirect.integration.service.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppdirectIntegrationApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles("junit")
public class AppDirectSubscriptionServiceTest {

	@InjectMocks
	AppDirectSubscriptionService subscriptionService;

	@Mock
	ClientProfileRepository clientRepository;

	@Mock
	UserRepository userRepository;

	UUID uuid;
	AppDirectEvent event;
	
	@Autowired
	AppDirectEventFixtures eventFixtures;
	
	@Autowired
	ClientProfileFixtures clientProfileFixtures;

	@Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);
		uuid = UUID.randomUUID();

		//Reset the mocks
		event = new AppDirectEvent();
	}

	@Test
	public void testCreateSuccess()
	{
		Mockito.when(clientRepository.findByCompanyUUID(uuid.toString())).thenReturn(null);
		Mockito.when(clientRepository.save(any(ClientProfile.class))).thenAnswer(new Answer<ClientProfile>() {
			@Override
			public ClientProfile answer(InvocationOnMock invocation) throws Throwable {
				ClientProfile clientProfile = (ClientProfile) invocation.getArguments()[0];
				clientProfile.setId(uuid.toString());

				return clientProfile;
			}
		});
		Mockito.when(userRepository.save(any(User.class))).thenAnswer(new Answer<User>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				User user = (User) invocation.getArguments()[0];
				user.setId(UUID.randomUUID().toString());

				return user;
			}
		});

		event = eventFixtures.createOrder();
		AppDirectNotificationResponse result = subscriptionService.create(event);

		Assert.assertNull(result.getErrorCode());
		Assert.assertTrue(result.getSuccess());
		Assert.assertEquals(uuid.toString(), result.getAccountIdentifier());
	}

	@Test
	public void testCreateAlreadyExists()
	{
		event = eventFixtures.createOrder();
		Mockito.when(clientRepository.findByCompanyUUID(event.getPayload().getCompany().getUuid())).thenReturn(new ClientProfile());

		AppDirectNotificationResponse result = subscriptionService.create(event);

		Assert.assertNotNull(result.getErrorCode());
		Assert.assertFalse(result.getSuccess());
		Assert.assertEquals(AppDirectErrorCodes.INVALID_RESPONSE, result.getErrorCode());
	}


	@Test
	public void testUpdateSuccess()
	{
		ClientProfile clientProfile = clientProfileFixtures.createBase();
		event = eventFixtures.createUpdate();

		Mockito.when(clientRepository.findById(event.getPayload().getAccount().getAccountIdentifier())).thenReturn(clientProfile);
		Mockito.when(clientRepository.save(any(ClientProfile.class))).thenAnswer(new Answer<ClientProfile>() {
			@Override
			public ClientProfile answer(InvocationOnMock invocation) throws Throwable {
				ClientProfile clientProfile = (ClientProfile) invocation.getArguments()[0];

				return clientProfile;
			}
		});

		AppDirectNotificationResponse result = subscriptionService.update(event);

		Assert.assertNull(result.getAccountIdentifier());
		Assert.assertTrue(result.getSuccess());
	}

	@Test
	public void testUpdateMissingAccount()
	{
		Mockito.when(clientRepository.findByCompanyUUID(uuid.toString())).thenReturn(null);

		event = eventFixtures.createUpdate();
		AppDirectNotificationResponse result = subscriptionService.update(event);

		Assert.assertEquals(AppDirectErrorCodes.ACCOUNT_NOT_FOUND, result.getErrorCode());
		Assert.assertFalse(result.getSuccess());
	}

	@Test
	public void testCancelSuccess()
	{
		ClientProfile clientProfile = clientProfileFixtures.createBase();
		event = eventFixtures.createCancel();

		Mockito.when(clientRepository.findById(event.getPayload().getAccount().getAccountIdentifier())).thenReturn(clientProfile);
		Mockito.when(clientRepository.save(any(ClientProfile.class))).thenAnswer(new Answer<ClientProfile>() {
			@Override
			public ClientProfile answer(InvocationOnMock invocation) throws Throwable {
				ClientProfile clientProfile = (ClientProfile) invocation.getArguments()[0];
				
				Assert.assertEquals(AppDirectAccountStatus.CANCELLED, clientProfile.getStatus());

				return clientProfile;
			}
		});

		AppDirectNotificationResponse result = subscriptionService.cancel(event);

		Assert.assertNull(result.getAccountIdentifier());
		Assert.assertTrue(result.getSuccess());
	}

	@Test
	public void testCancelMissingAccount()
	{
		Mockito.when(clientRepository.findByCompanyUUID(uuid.toString())).thenReturn(null);

		event = eventFixtures.createCancel();
		AppDirectNotificationResponse result = subscriptionService.cancel(event);

		Assert.assertEquals(AppDirectErrorCodes.ACCOUNT_NOT_FOUND, result.getErrorCode());
		Assert.assertFalse(result.getSuccess());
	}

	@Test
	public void testStatusSuccess()
	{
		ClientProfile clientProfile = clientProfileFixtures.createBase();
		event = eventFixtures.createStatus();

		Mockito.when(clientRepository.findById(event.getPayload().getAccount().getAccountIdentifier())).thenReturn(clientProfile);
		Mockito.when(clientRepository.save(any(ClientProfile.class))).thenAnswer(new Answer<ClientProfile>() {
			@Override
			public ClientProfile answer(InvocationOnMock invocation) throws Throwable {
				ClientProfile clientProfile = (ClientProfile) invocation.getArguments()[0];
				
				Assert.assertEquals(AppDirectAccountStatus.FREE_TRIAL_EXPIRED, clientProfile.getStatus());

				return clientProfile;
			}
		});

		AppDirectNotificationResponse result = subscriptionService.status(event);

		Assert.assertNull(result.getAccountIdentifier());
		Assert.assertTrue(result.getSuccess());
	}

	
	@Test
	public void testCreateEmptyEvent()
	{
		AppDirectNotificationResponse result = subscriptionService.create(null);

		Assert.assertFalse(result.getSuccess());
		Assert.assertEquals(AppDirectErrorCodes.INVALID_RESPONSE, result.getErrorCode());
	}

	@Test
	public void testUpdateEmptyEvent()
	{
		AppDirectNotificationResponse result = subscriptionService.update(null);

		Assert.assertFalse(result.getSuccess());
		Assert.assertEquals(AppDirectErrorCodes.INVALID_RESPONSE, result.getErrorCode());
	}

	@Test
	public void testCancelEmptyEvent()
	{
		AppDirectNotificationResponse result = subscriptionService.cancel(null);

		Assert.assertFalse(result.getSuccess());
		Assert.assertEquals(AppDirectErrorCodes.INVALID_RESPONSE, result.getErrorCode());
	}

	@Test
	public void testStatusEmptyEvent()
	{
		AppDirectNotificationResponse result = subscriptionService.status(null);

		Assert.assertFalse(result.getSuccess());
		Assert.assertEquals(AppDirectErrorCodes.INVALID_RESPONSE, result.getErrorCode());
	}

	@Test
	public void testCreateStatelessResult()
	{
		event = eventFixtures.createOrder();
		event.setFlag(AppDirectFlag.STATELESS);
		AppDirectNotificationResponse result = subscriptionService.create(event);

		Assert.assertNotNull(result.getErrorCode());
		Assert.assertFalse(result.getSuccess());
		Assert.assertEquals(AppDirectErrorCodes.USER_ALREADY_EXISTS, result.getErrorCode());
	}

	@Test
	public void testUpdateStatelessResult()
	{
		event = eventFixtures.createUpdate();
		event.setFlag(AppDirectFlag.STATELESS);
		AppDirectNotificationResponse result = subscriptionService.update(event);

		Assert.assertNotNull(result.getErrorCode());
		Assert.assertFalse(result.getSuccess());
		Assert.assertEquals(AppDirectErrorCodes.ACCOUNT_NOT_FOUND, result.getErrorCode());
	}

	@Test
	public void testCancelStatelessResult()
	{
		event = eventFixtures.createCancel();
		event.setFlag(AppDirectFlag.STATELESS);
		AppDirectNotificationResponse result = subscriptionService.cancel(event);

		Assert.assertNotNull(result.getErrorCode());
		Assert.assertFalse(result.getSuccess());
		Assert.assertEquals(AppDirectErrorCodes.ACCOUNT_NOT_FOUND, result.getErrorCode());
	}

	@Test
	public void testStatusStatelessResult()
	{
		event = eventFixtures.createStatus();
		event.setFlag(AppDirectFlag.STATELESS);
		AppDirectNotificationResponse result = subscriptionService.status(event);

		Assert.assertNotNull(result.getErrorCode());
		Assert.assertFalse(result.getSuccess());
		Assert.assertEquals(AppDirectErrorCodes.ACCOUNT_NOT_FOUND, result.getErrorCode());
	}

	@Test
	public void testCreateWrongEvent()
	{
		event = eventFixtures.createOrder();
		event.setType(AppDirectEventType.USER_ASSIGNMENT);
		AppDirectNotificationResponse result = subscriptionService.create(event);

		Assert.assertNotNull(result.getErrorCode());
		Assert.assertFalse(result.getSuccess());
		Assert.assertEquals(AppDirectErrorCodes.INVALID_RESPONSE, result.getErrorCode());
	}

	@Test
	public void testUpdateWrongEvent()
	{
		event = eventFixtures.createUpdate();
		event.setType(AppDirectEventType.USER_ASSIGNMENT);
		AppDirectNotificationResponse result = subscriptionService.update(event);

		Assert.assertNotNull(result.getErrorCode());
		Assert.assertFalse(result.getSuccess());
		Assert.assertEquals(AppDirectErrorCodes.INVALID_RESPONSE, result.getErrorCode());
	}

	@Test
	public void testCancelWrongEvent()
	{
		event = eventFixtures.createCancel();
		event.setType(AppDirectEventType.USER_ASSIGNMENT);
		AppDirectNotificationResponse result = subscriptionService.cancel(event);

		Assert.assertNotNull(result.getErrorCode());
		Assert.assertFalse(result.getSuccess());
		Assert.assertEquals(AppDirectErrorCodes.INVALID_RESPONSE, result.getErrorCode());
	}

	@Test
	public void testStatusWrongEvent()
	{
		event = eventFixtures.createStatus();
		event.setType(AppDirectEventType.USER_ASSIGNMENT);
		AppDirectNotificationResponse result = subscriptionService.status(event);

		Assert.assertNotNull(result.getErrorCode());
		Assert.assertFalse(result.getSuccess());
		Assert.assertEquals(AppDirectErrorCodes.INVALID_RESPONSE, result.getErrorCode());
	}
}
