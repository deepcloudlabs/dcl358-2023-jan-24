package com.example.hr.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hr.application.HrApplication;
import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.dto.request.HireEmployeeRequest;
import com.example.hr.dto.response.EmployeeResponse;
import com.example.hr.dto.response.FireEmployeeResponse;
import com.example.hr.dto.response.HireEmployeeResponse;

@Service
public class HrService {
	private final HrApplication hrApplication;
	private final ModelMapper modelMapper;
	

	public HrService(HrApplication hrApplication, ModelMapper modelMapper) {
		this.hrApplication = hrApplication;
		this.modelMapper = modelMapper;
	}

	@Transactional
	public HireEmployeeResponse hireEmployee(HireEmployeeRequest hireEmployeeRequest) {
		var employee = modelMapper.map(hireEmployeeRequest, Employee.class);
		hrApplication.hireEmployee(employee);
		return new HireEmployeeResponse("success");
	}

	@Transactional
	public FireEmployeeResponse fireEmployee(String identityNo) {
		var firedEmployee = hrApplication.fireEmployee(TcKimlikNo.valueOf(identityNo));
		if (firedEmployee.isPresent())
			return new FireEmployeeResponse("success");
		throw new IllegalArgumentException("Does not exist.");
	}

	public EmployeeResponse findEmployeeByIdentity(String identityNo) {
		var foundEmployee = hrApplication.getEmployee(TcKimlikNo.valueOf(identityNo));
		var employee = foundEmployee.orElseThrow(()-> new IllegalArgumentException("Does not exist."));
		return modelMapper.map(employee, EmployeeResponse.class);
	}

}
