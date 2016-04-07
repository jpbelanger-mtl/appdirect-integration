package com.belanger.appdirect.integration.domain.vo;

import org.springframework.stereotype.Component;

@Component
public class AppDirectUserFixtures {

	public AppDirectUser createBase() {
		AppDirectUser user = new AppDirectUser();
		user.setUuid("2342423-fsd3-44324-4234-542h432h423");
		user.setOpenId("https://test.byappdirect.com/openid/id/2342423-fsd3-44324-4234-542h432h423");
		user.setEmail("john.doe@gmail.com");
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setLanguage("fr");
		
		return user;
	}

}
