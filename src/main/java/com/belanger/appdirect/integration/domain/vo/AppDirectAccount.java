package com.belanger.appdirect.integration.domain.vo;

public class AppDirectAccount {
	private String accountIdentifier;
	private AppDirectAccountStatus status;
	private String parentAccountIdentifier;

	public String getAccountIdentifier() {
		return accountIdentifier;
	}

	public void setAccountIdentifier(String accountIdentifier) {
		this.accountIdentifier = accountIdentifier;
	}

	public AppDirectAccountStatus getStatus() {
		return status;
	}

	public void setStatus(AppDirectAccountStatus status) {
		this.status = status;
	}

	public String getParentAccountIdentifier() {
		return parentAccountIdentifier;
	}

	public void setParentAccountIdentifier(String parentAccountIdentifier) {
		this.parentAccountIdentifier = parentAccountIdentifier;
	}
}
