package com.example.hr.configuration;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.dto.request.HireEmployeeRequest;
import com.example.hr.dto.response.EmployeeResponse;
import com.example.hr.entity.EmployeeEntity;

@Configuration
public class ModelMapperConfig {

	private static final Converter<HireEmployeeRequest,Employee> 
	HIRE_EMPLOYEE_REQUEST_TO_EMPLOYEE_CONVERTER = (context) -> {
		var request = context.getSource();
		   return new Employee.Builder(TcKimlikNo.valueOf(request.getIdentity()))
	               .fullname(request.getFirstName(), request.getLastName())
	               .salary(request.getSalary(), request.getCurrency())
	               .iban(request.getIban())
	               .birthYear(request.getBirthYear())
	               .photo(request.getPhoto())
	               .department(request.getDepartment())
	               .jobStyle(request.getJobStyle())
	              .build();		
	};
	
	private static final Converter<EmployeeEntity,Employee> 
	EMPLOYEE_ENTITY_TO_EMPLOYEE_CONVERTER = (context) -> {
		var request = context.getSource();
		return new Employee.Builder(TcKimlikNo.valueOf(request.getIdentity()))
				.fullname(request.getFirstName(), request.getLastName())
				.salary(request.getSalary(), request.getCurrency())
				.iban(request.getIban())
				.birthYear(request.getBirthYear())
				.photo(request.getPhoto())
				.department(request.getDepartment())
				.jobStyle(request.getJobStyle())
				.build();		
	};
	
	private static final Converter<Employee,EmployeeResponse> 
	EMPLOYEE_TO_EMPLOYEE_RESPONSE_CONVERTER = (context) -> {
		var response = new EmployeeResponse();
		var employee = context.getSource();
		response.setIdentity(employee.getIdentityNo().getValue());
		response.setFirstName(employee.getFullname().firstName());
		response.setLastName(employee.getFullname().lastName());
		response.setIban(employee.getIban().getValue());
		response.setSalary(employee.getSalary().amount());
		response.setCurrency(employee.getSalary().currency());
		response.setBirthYear(employee.getBirthYear().value());
		response.setDepartment(employee.getDepartment().name());
		response.setJobStyle(employee.getJobStyle().name());
		response.setPhoto(employee.getPhoto().getBase64Values());
		return response;	
	};
	
	private static final Converter<Employee,EmployeeEntity> 
	EMPLOYEE_TO_EMPLOYEE_ENTITY_CONVERTER = (context) -> {
		var response = new EmployeeEntity();
		var employee = context.getSource();
		response.setIdentity(employee.getIdentityNo().getValue());
		response.setFirstName(employee.getFullname().firstName());
		response.setLastName(employee.getFullname().lastName());
		response.setIban(employee.getIban().getValue());
		response.setSalary(employee.getSalary().amount());
		response.setCurrency(employee.getSalary().currency());
		response.setBirthYear(employee.getBirthYear().value());
		response.setDepartment(employee.getDepartment().name());
		response.setJobStyle(employee.getJobStyle().name());
		response.setPhoto(employee.getPhoto().getValues());
		return response;	
	};

	@Bean
	ModelMapper createModelMapper() {
		var mapper = new ModelMapper();
		mapper.addConverter(HIRE_EMPLOYEE_REQUEST_TO_EMPLOYEE_CONVERTER, 
				HireEmployeeRequest.class, Employee.class);
		mapper.addConverter(EMPLOYEE_TO_EMPLOYEE_RESPONSE_CONVERTER, 
				Employee.class, EmployeeResponse.class);
		mapper.addConverter(EMPLOYEE_ENTITY_TO_EMPLOYEE_CONVERTER, 
				EmployeeEntity.class, Employee.class);
		mapper.addConverter(EMPLOYEE_TO_EMPLOYEE_ENTITY_CONVERTER, 
				Employee.class, EmployeeEntity.class);
		return mapper;
	}
}
