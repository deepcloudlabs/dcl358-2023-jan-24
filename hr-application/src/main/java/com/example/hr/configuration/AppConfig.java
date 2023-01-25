package com.example.hr.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.hr.application.HrApplication;
import com.example.hr.application.business.StandardHrApplication;
import com.example.hr.domain.event.HrDomainEvent;
import com.example.hr.infrastructure.EventPublisher;
import com.example.hr.respoitory.EmployeeRepository;

@Configuration
public class AppConfig {

	@Bean
	HrApplication createHrApp(
			EmployeeRepository employeeRepository, 
			EventPublisher<HrDomainEvent> eventPublisher) {
		return new StandardHrApplication(employeeRepository, eventPublisher);
	}
}
