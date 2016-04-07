package com.belanger.appdirect.integration.domain.vo;

public enum AppDirectErrorCodes {
	USER_ALREADY_EXISTS("This error code is typically used when AppDirect admins try to buy subscriptions for apps they have already purchased directly from the application vendor. In this scenario, we will show the user an error message and prompt them to link their account."),
	USER_NOT_FOUND("This error code is typically used when AppDirect admins try to unassign users not found in the application vendor's account."),
	ACCOUNT_NOT_FOUND("This error code is typically used when AppDirect admins try to add or remove users from an account not found in the application vendor's records."),
	MAX_USERS_REACHED("This error code is typically used when AppDirect admins try to assign users beyond the limit of the number of seats available. AppDirect will typically prevent that from happening by monitoring application usage."),
	UNAUTHORIZED("This error code is returned when users try any action that is not authorized for that particular application, for example, if an application does not allow the original creator to be unassigned."),
	OPERATION_CANCELED("This error code is returned when a user manually interrupts the operation (clicking cancel on the account creation page, etc.)."),
	CONFIGURATION_ERROR("This error code is returned when the vendor endpoint is not currently configured."),
	INVALID_RESPONSE("This error code is returned when the vendor was unable to process the event fetched from AppDirect."),
	PENDING("This error code is returned when the vendor was unable to process the event because the service is under provisioning."),
	FORBIDDEN("This error code is returned when the operation is not allowed."),
	BINDING_NOT_FOUND("This error code is returned when the specified binding cannot be found."),
	TRANSPORT_ERROR("This error code is returned when the there is a transport error, such as the server being unreachable."),
	UNKNOWN_ERROR("This error code may be used when none of the other error codes apply.");
	
	private String message;

	private AppDirectErrorCodes(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
