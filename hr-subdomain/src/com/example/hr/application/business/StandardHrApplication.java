package com.example.hr.application.business;

import java.util.Optional;

import com.example.hr.application.HrApplication;
import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.domain.event.EmployeeFiredEvent;
import com.example.hr.domain.event.EmployeeHiredEvent;
import com.example.hr.domain.event.HrDomainEvent;
import com.example.hr.domain.exception.EmployeeAlreadyExists;
import com.example.hr.infrastructure.EventPublisher;
import com.example.hr.respoitory.EmployeeRepository;

public class StandardHrApplication implements HrApplication {
	private final EmployeeRepository employeeRepository;
	private final EventPublisher<HrDomainEvent> eventPublisher;
	
	public StandardHrApplication(EmployeeRepository employeeRepository, EventPublisher<HrDomainEvent> eventPublisher) {
		this.employeeRepository = employeeRepository;
		this.eventPublisher = eventPublisher;
	}

	@Override
	public Employee hireEmployee(Employee employee) {
		var identityNo = employee.getIdentityNo();
		var foundEmployee = employeeRepository.findEmployeeByIdentityNo(identityNo);
		if(foundEmployee.isPresent())
			throw new EmployeeAlreadyExists(identityNo);
		var persistedEmployee = employeeRepository.persist(employee);
		var domainEvent = new EmployeeHiredEvent(identityNo);
		eventPublisher.publishEvent(domainEvent);
		return persistedEmployee;
	}

	@Override
	public Optional<Employee> fireEmployee(TcKimlikNo identityNo) {
		var foundEmployee = employeeRepository.findEmployeeByIdentityNo(identityNo);
		if(foundEmployee.isPresent()) {
			var firedEmployee = employeeRepository.removeEmployee(identityNo);
			var domainEvent = new EmployeeFiredEvent(firedEmployee.get());
			eventPublisher.publishEvent(domainEvent);
		}		
		return foundEmployee;
	}

	@Override
	public Optional<Employee> getEmployee(TcKimlikNo identityNo) {
		return employeeRepository.findEmployeeByIdentityNo(identityNo);
	}

}
