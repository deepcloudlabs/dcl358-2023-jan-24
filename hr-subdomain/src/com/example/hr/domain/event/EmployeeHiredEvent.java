package com.example.hr.domain.event;

import com.example.ddd.DomainEvent;
import com.example.hr.domain.TcKimlikNo;

@DomainEvent
public class EmployeeHiredEvent extends HrDomainEvent {
	private final TcKimlikNo identityNo;

	public EmployeeHiredEvent(TcKimlikNo identityNo) {
		this.identityNo = identityNo;
	}

	public TcKimlikNo getIdentityNo() {
		return identityNo;
	}
	
}
