package com.example.hr.domain.exception;

import com.example.hr.domain.TcKimlikNo;

@SuppressWarnings("serial")
public class EmployeeNotFoundException extends RuntimeException {
	private final TcKimlikNo identityNo;

	public EmployeeNotFoundException(TcKimlikNo identityNo) {
		super("Employee does not exists (%s)".formatted(identityNo.getValue()));
		this.identityNo = identityNo;
	}

	public TcKimlikNo getIdentityNo() {
		return identityNo;
	}

}
