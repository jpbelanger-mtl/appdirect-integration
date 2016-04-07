package com.belanger.appdirect.integration.domain.vo;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppDirectEventFixtures {

	@Autowired
	private AppDirectUserFixtures userFixtures;
	
	@Autowired
	private AppDirectMarketplaceFixtures marketplaceFixtures;
	
	@Autowired
	private AppDirectPayloadFixtures payloadFixtures;

	public AppDirectEvent createOrder()
	{
		AppDirectEvent event = new AppDirectEvent();
		
		event.setApplicationUuid(UUID.randomUUID().toString());
		event.setCreator(userFixtures.createBase());
		event.setFlag(AppDirectFlag.DEVELOPMENT);
		event.setMarketplace(marketplaceFixtures.createBase());
		event.setPayload(payloadFixtures.createOrder());
		event.setReturnUrl("http://abc.com");
		event.setType(AppDirectEventType.SUBSCRIPTION_ORDER);

		return event;
	}

	public AppDirectEvent createUpdate()
	{
		AppDirectEvent event = new AppDirectEvent();
		
		event.setApplicationUuid(UUID.randomUUID().toString());
		event.setCreator(userFixtures.createBase());
		event.setFlag(AppDirectFlag.STATELESS);
		event.setMarketplace(marketplaceFixtures.createBase());
		event.setPayload(payloadFixtures.createChange());
		event.setType(AppDirectEventType.SUBSCRIPTION_CHANGE);

		return event;
	}

	public AppDirectEvent createCancel() {
		AppDirectEvent event = new AppDirectEvent();

		event.setApplicationUuid(UUID.randomUUID().toString());
		event.setCreator(userFixtures.createBase());
		event.setFlag(AppDirectFlag.STATELESS);
		event.setMarketplace(marketplaceFixtures.createBase());
		event.setPayload(payloadFixtures.createCancel());
		event.setType(AppDirectEventType.SUBSCRIPTION_CANCEL);

		return event;
	}

	public AppDirectEvent createStatus() {
		AppDirectEvent event = new AppDirectEvent();

		event.setApplicationUuid(UUID.randomUUID().toString());
		event.setCreator(userFixtures.createBase());
		event.setFlag(AppDirectFlag.STATELESS);
		event.setMarketplace(marketplaceFixtures.createBase());
		event.setPayload(payloadFixtures.createStatus());
		event.setType(AppDirectEventType.SUBSCRIPTION_NOTICE);

		return event;
	}
}
