package com.belanger.appdirect.integration.domain.vo;

public class AppDirectNotice {
	private AppDirectNoticeType type;
	private String message;

	public AppDirectNoticeType getType() {
		return type;
	}

	public void setType(AppDirectNoticeType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
