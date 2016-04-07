package com.belanger.appdirect.integration.service;

import javax.transaction.Transactional;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.belanger.appdirect.integration.domain.data.ClientProfile;
import com.belanger.appdirect.integration.domain.data.User;
import com.belanger.appdirect.integration.domain.vo.AppDirectAccountStatus;
import com.belanger.appdirect.integration.domain.vo.AppDirectErrorCodes;
import com.belanger.appdirect.integration.domain.vo.AppDirectEvent;
import com.belanger.appdirect.integration.domain.vo.AppDirectEventType;
import com.belanger.appdirect.integration.domain.vo.AppDirectFlag;
import com.belanger.appdirect.integration.domain.vo.AppDirectNotificationResponse;
import com.belanger.appdirect.integration.service.repository.ClientProfileRepository;
import com.belanger.appdirect.integration.service.repository.UserRepository;

@Service
public class AppDirectSubscriptionService {

	@Autowired
	private ClientProfileRepository clientRepository;

	@Autowired
	private UserRepository userRepository;

	private Logger logger = Logger.getLogger(AppDirectSubscriptionService.class);
	
	@Transactional
	public AppDirectNotificationResponse create(AppDirectEvent event) {

		if ( event == null || event.getType() != AppDirectEventType.SUBSCRIPTION_ORDER )
			return new AppDirectNotificationResponse().error(AppDirectErrorCodes.INVALID_RESPONSE, "Invalid event type");

		if ( event.getFlag() == AppDirectFlag.STATELESS )
			return new AppDirectNotificationResponse().error(AppDirectErrorCodes.USER_ALREADY_EXISTS, "Stateless request, return a dummy response");

		ClientProfile clientProfile = clientRepository.findByCompanyUUID(event.getPayload().getCompany().getUuid());
		if ( clientProfile != null )
			return new AppDirectNotificationResponse().error(AppDirectErrorCodes.INVALID_RESPONSE, event.getPayload().getCompany().getUuid() + " id already exists");

		//Create a new ClientProfile and initialize some information into our database
		clientProfile = new ClientProfile();
		clientProfile.setCompanyUUID(event.getPayload().getCompany().getUuid());
		clientProfile.setEmail(event.getPayload().getCompany().getEmail());
		clientProfile.setName(event.getPayload().getCompany().getName());
		clientProfile.setPhone(event.getPayload().getCompany().getPhoneNumber());
		clientProfile.setWebsite(event.getPayload().getCompany().getWebsite());
		clientProfile.setEdition(event.getPayload().getOrder().getEditionCode());
		clientProfile.setMaxUsers(event.getPayload().getOrder().getItemAsInt("USER"));
		clientProfile.setBandwidth(event.getPayload().getOrder().getItemAsInt("MEGABYTE"));
		clientProfile.setStatus(AppDirectAccountStatus.ACTIVE);
		clientProfile = clientRepository.save(clientProfile);

		logger.info("Created a new client: " + ToStringBuilder.reflectionToString(clientProfile, new RecursiveToStringStyle()));

		User user = new User();
		user.setActive(true);
		user.setAdmin(true);
		user.setEmail(event.getCreator().getEmail());
		user.setFirstName(event.getCreator().getFirstName());
		user.setLanguage(event.getCreator().getLanguage());
		user.setLastName(event.getCreator().getLastName());
		user.setOpenId(event.getCreator().getOpenId());
		user.setUuid(event.getCreator().getUuid());
		userRepository.save(user);

		logger.info("Created a new admin: " + ToStringBuilder.reflectionToString(user, new RecursiveToStringStyle()));

		return new AppDirectNotificationResponse().successful(clientProfile.getId());
	}

	@Transactional
	public AppDirectNotificationResponse update(AppDirectEvent event) {

		if ( event == null || event.getType() != AppDirectEventType.SUBSCRIPTION_CHANGE )
			return new AppDirectNotificationResponse().error(AppDirectErrorCodes.INVALID_RESPONSE, "Invalid event type");

		ClientProfile clientProfile = clientRepository.findById(event.getPayload().getAccount().getAccountIdentifier());
		if ( clientProfile == null )
			return new AppDirectNotificationResponse().error(AppDirectErrorCodes.ACCOUNT_NOT_FOUND, event.getPayload().getAccount().getAccountIdentifier() + " does not exist");

		clientProfile.setEdition(event.getPayload().getOrder().getEditionCode());
		clientProfile.setMaxUsers(event.getPayload().getOrder().getItemAsInt("USER"));
		clientProfile.setBandwidth(event.getPayload().getOrder().getItemAsInt("MEGABYTE"));
		clientProfile = clientRepository.save(clientProfile);

		logger.info("Updated client: " + ToStringBuilder.reflectionToString(clientProfile, new RecursiveToStringStyle()));

		return new AppDirectNotificationResponse().successful();
	}

	@Transactional
	public AppDirectNotificationResponse cancel(AppDirectEvent event) {

		if ( event == null || event.getType() != AppDirectEventType.SUBSCRIPTION_CANCEL )
			return new AppDirectNotificationResponse().error(AppDirectErrorCodes.INVALID_RESPONSE, "Invalid event type");

		ClientProfile clientProfile = clientRepository.findById(event.getPayload().getAccount().getAccountIdentifier());
		if ( clientProfile == null )
			return new AppDirectNotificationResponse().error(AppDirectErrorCodes.ACCOUNT_NOT_FOUND, event.getPayload().getAccount().getAccountIdentifier() + " does not exist");
		
		clientProfile.setStatus(AppDirectAccountStatus.CANCELLED);

		logger.info("Canceled client: " + ToStringBuilder.reflectionToString(clientProfile, new RecursiveToStringStyle()));

		return new AppDirectNotificationResponse().successful();
	}

	public AppDirectNotificationResponse status(AppDirectEvent event) {

		if ( event == null || event.getType() != AppDirectEventType.SUBSCRIPTION_NOTICE )
			return new AppDirectNotificationResponse().error(AppDirectErrorCodes.INVALID_RESPONSE, "Invalid event type");
		
		ClientProfile clientProfile = clientRepository.findById(event.getPayload().getAccount().getAccountIdentifier());
		if ( clientProfile == null )
			return new AppDirectNotificationResponse().error(AppDirectErrorCodes.ACCOUNT_NOT_FOUND, event.getPayload().getAccount().getAccountIdentifier() + " does not exist");
		
		clientProfile.setStatus(event.getPayload().getAccount().getStatus());

		logger.info("Canceled client: " + ToStringBuilder.reflectionToString(clientProfile, new RecursiveToStringStyle()));

		return new AppDirectNotificationResponse().successful();
	}
}
