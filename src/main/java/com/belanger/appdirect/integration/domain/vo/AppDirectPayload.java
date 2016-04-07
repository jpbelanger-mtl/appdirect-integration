package com.belanger.appdirect.integration.domain.vo;

import java.util.Map;

public class AppDirectPayload {

	private AppDirectUser user;
	private AppDirectAccount account;
	private AppDirectCompany company;
	private AppDirectAddon addonInstance;
	private AppDirectAddon addonBinding;
	private AppDirectNotice notice;
	private AppDirectOrder order;

	private Map<String, String> configuration;

	public AppDirectUser getUser() {
		return user;
	}

	public void setUser(AppDirectUser user) {
		this.user = user;
	}

	public AppDirectAccount getAccount() {
		return account;
	}

	public void setAccount(AppDirectAccount account) {
		this.account = account;
	}

	public AppDirectAddon getAddonInstance() {
		return addonInstance;
	}

	public void setAddonInstance(AppDirectAddon addonInstance) {
		this.addonInstance = addonInstance;
	}

	public AppDirectAddon getAddonBinding() {
		return addonBinding;
	}

	public void setAddonBinding(AppDirectAddon addonBinding) {
		this.addonBinding = addonBinding;
	}

	public AppDirectNotice getNotice() {
		return notice;
	}

	public void setNotice(AppDirectNotice notice) {
		this.notice = notice;
	}

	public Map<String, String> getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Map<String, String> configuration) {
		this.configuration = configuration;
	}

	public AppDirectCompany getCompany() {
		return company;
	}

	public void setCompany(AppDirectCompany company) {
		this.company = company;
	}

	public AppDirectOrder getOrder() {
		return order;
	}

	public void setOrder(AppDirectOrder order) {
		this.order = order;
	}
}
