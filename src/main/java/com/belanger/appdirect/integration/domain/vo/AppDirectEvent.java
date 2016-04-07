package com.belanger.appdirect.integration.domain.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("event")
public class AppDirectEvent {

	private AppDirectEventType type;
	private AppDirectMarketplace marketplace;
	private String applicationUuid;
	private AppDirectFlag flag;

	private AppDirectUser creator;
	private AppDirectPayload payload;
	private String returnUrl;
	private List<String> links;

	public AppDirectEventType getType() {
		return type;
	}

	public void setType(AppDirectEventType type) {
		this.type = type;
	}

	public AppDirectMarketplace getMarketplace() {
		return marketplace;
	}

	public void setMarketplace(AppDirectMarketplace marketplace) {
		this.marketplace = marketplace;
	}

	public AppDirectFlag getFlag() {
		return flag;
	}

	public void setFlag(AppDirectFlag flag) {
		this.flag = flag;
	}

	public AppDirectUser getCreator() {
		return creator;
	}

	public void setCreator(AppDirectUser creator) {
		this.creator = creator;
	}

	public AppDirectPayload getPayload() {
		return payload;
	}

	public void setPayload(AppDirectPayload payload) {
		this.payload = payload;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getApplicationUuid() {
		return applicationUuid;
	}

	public void setApplicationUuid(String applicationUuid) {
		this.applicationUuid = applicationUuid;
	}

	public List<String> getLinks() {
		return links;
	}

	public void setLinks(List<String> links) {
		this.links = links;
	}
}
