package com.belanger.appdirect.integration.domain.vo;

import org.springframework.stereotype.Component;

@Component
public class AppDirectOrderFixtures {

	public AppDirectOrder createOrder() {
		AppDirectOrder order = new AppDirectOrder();
		
		order.setEditionCode("FREE");
		order.setPricingDuration("MONTHLY");
		
		return order;
	}

	public AppDirectOrder createChange() {
		AppDirectOrder order = new AppDirectOrder();
		
		order.setEditionCode("VIP");
		order.setPricingDuration("MONTHLY");
		
		return order;
	}

}
