package com.belanger.appdirect.integration.domain.vo;

import org.springframework.stereotype.Component;

@Component
public class AppDirectCompanyFixtures {

	public AppDirectCompany createBase() {
		AppDirectCompany company = new AppDirectCompany();
		company.setCountry("US");
		company.setEmail("abc@test.com");
		company.setExternalId("");
		company.setName("Some company name");
		company.setPhoneNumber("123-567-3343");
		company.setUuid("499e2704-4da0-44fc-991d-58fe2913d5d2");
		company.setWebsite(null);
		
		return company;
	}

}
