package com.example.hr.dto.error;

public class ErrorMessage {
	private final String message;
	private final String status;
	// private final int i18n; // for ui

	public ErrorMessage(String message, String status) {
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public String getStatus() {
		return status;
	}

}
