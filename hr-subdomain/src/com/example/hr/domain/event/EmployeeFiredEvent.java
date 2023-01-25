package com.example.hr.domain.event;

import com.example.ddd.DomainEvent;
import com.example.hr.domain.Employee;

@DomainEvent
public class EmployeeFiredEvent extends HrDomainEvent {
	private final Employee employee;

	public EmployeeFiredEvent(Employee employee) {
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

}
