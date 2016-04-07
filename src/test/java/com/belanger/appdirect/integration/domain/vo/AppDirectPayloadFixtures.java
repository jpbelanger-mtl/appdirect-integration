package com.belanger.appdirect.integration.domain.vo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppDirectPayloadFixtures {

	@Autowired
	AppDirectCompanyFixtures companyFixtures;
	
	@Autowired
	AppDirectAccountFixtures accountFixtures;
	
	@Autowired
	AppDirectOrderFixtures orderFixtures;

	public AppDirectPayload createOrder() {
		AppDirectPayload payload = new AppDirectPayload();
		payload.setCompany(companyFixtures.createBase());
		payload.setOrder(orderFixtures.createOrder());
		
		return payload;
	}

	public AppDirectPayload createChange() {
		AppDirectPayload payload = new AppDirectPayload();
		payload.setAccount(accountFixtures.createChange());
		payload.setOrder(orderFixtures.createChange());
		
		return payload;
	}

	public AppDirectPayload createCancel() {
		AppDirectPayload payload = new AppDirectPayload();
		payload.setAccount(accountFixtures.createCancel());
		
		return payload;
	}

	public AppDirectPayload createStatus() {
		AppDirectPayload payload = new AppDirectPayload();
		payload.setAccount(accountFixtures.createStatus());
		
		return payload;
	}

}
