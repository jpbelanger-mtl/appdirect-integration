package com.belanger.appdirect.integration.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AppDirectNotificationResponse {
	private boolean success;
	private String accountIdentifier;
	private AppDirectErrorCodes errorCode;
	private String message;

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getAccountIdentifier() {
		return accountIdentifier;
	}

	public void setAccountIdentifier(String accountIdentifier) {
		this.accountIdentifier = accountIdentifier;
	}

	public AppDirectErrorCodes getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(AppDirectErrorCodes errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public AppDirectNotificationResponse successful(String accountId)
	{
		this.success = true;
		this.accountIdentifier = accountId;
		
		return this;
	}

	public AppDirectNotificationResponse successful()
	{
		this.success = true;
		
		return this;
	}

	public AppDirectNotificationResponse error(AppDirectErrorCodes errorCode, String message)
	{
		this.success = false;
		this.errorCode = errorCode;
		this.message = message;

		return this;
	}
}
