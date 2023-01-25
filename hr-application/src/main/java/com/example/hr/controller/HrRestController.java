package com.example.hr.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.hr.dto.request.HireEmployeeRequest;
import com.example.hr.dto.response.EmployeeResponse;
import com.example.hr.dto.response.FireEmployeeResponse;
import com.example.hr.dto.response.HireEmployeeResponse;
import com.example.hr.service.HrService;
import com.example.validation.TcKimlikNo;

@RestController
@RequestScope
@RequestMapping("/employees")
@CrossOrigin
@Validated
public class HrRestController {
	private final HrService hrService;
	
	public HrRestController(HrService hrService) {
		this.hrService = hrService;
	}

	@PostMapping
	public HireEmployeeResponse hireEmployee(
			@RequestBody @Validated HireEmployeeRequest hireEmployeeRequest) {
		return hrService.hireEmployee(hireEmployeeRequest);
	}
	
	@DeleteMapping("{identityNo}")
	public FireEmployeeResponse fireEmployee(
			@PathVariable @TcKimlikNo String identityNo) {
		return hrService.fireEmployee(identityNo);
	}
	
	@GetMapping("{identityNo}")
	public EmployeeResponse getEmployee(
			@PathVariable @TcKimlikNo String identityNo) {
		 return hrService.findEmployeeByIdentity(identityNo);
	}
}
