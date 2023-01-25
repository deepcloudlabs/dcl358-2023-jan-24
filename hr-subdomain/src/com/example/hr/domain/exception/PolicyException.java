package com.example.hr.domain.exception;

import com.example.ddd.BusinessException;

@SuppressWarnings("serial")
@BusinessException
public class PolicyException extends Exception {

	public PolicyException(String reason) {
		super(reason);
	}

}
