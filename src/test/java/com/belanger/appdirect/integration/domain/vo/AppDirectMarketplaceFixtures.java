package com.belanger.appdirect.integration.domain.vo;

import org.springframework.stereotype.Component;

@Component
public class AppDirectMarketplaceFixtures {

	public AppDirectMarketplace createBase() {
		AppDirectMarketplace marketplace = new AppDirectMarketplace();
		marketplace.setPartner("test");
		marketplace.setBaseUrl("http://test.appdirect.com");
		
		return marketplace;
	}

}
