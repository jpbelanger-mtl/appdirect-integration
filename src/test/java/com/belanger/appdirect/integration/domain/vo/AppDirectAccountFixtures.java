package com.belanger.appdirect.integration.domain.vo;

import org.springframework.stereotype.Component;

@Component
public class AppDirectAccountFixtures {

	public AppDirectAccount createChange() {
		AppDirectAccount account = new AppDirectAccount();
		account.setAccountIdentifier("dummyID");
		account.setStatus(AppDirectAccountStatus.ACTIVE);
		
		return account;
	}

	public AppDirectAccount createCancel() {
		AppDirectAccount account = new AppDirectAccount();
		account.setAccountIdentifier("dummyID");
		account.setStatus(AppDirectAccountStatus.ACTIVE);
		
		return account;
	}

	public AppDirectAccount createStatus() {
		AppDirectAccount account = new AppDirectAccount();
		account.setAccountIdentifier("dummyID");
		account.setStatus(AppDirectAccountStatus.FREE_TRIAL_EXPIRED);

		return account;
	}

}
