package com.example.hr.domain.exception;

import com.example.hr.domain.TcKimlikNo;

@SuppressWarnings("serial")
public class EmployeeAlreadyExists extends RuntimeException {
	private final TcKimlikNo identityNo;

	public EmployeeAlreadyExists(TcKimlikNo identityNo) {
		super("Employee already exists (%s)".formatted(identityNo.getValue()));
		this.identityNo = identityNo;
	}

	public TcKimlikNo getIdentityNo() {
		return identityNo;
	}

}
