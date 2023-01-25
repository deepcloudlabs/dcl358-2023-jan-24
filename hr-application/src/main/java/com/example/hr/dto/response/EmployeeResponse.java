package com.example.hr.dto.response;

import com.example.hr.domain.FiatCurrency;

import lombok.Data;

@Data
public class EmployeeResponse {
	private String identity;
	private String firstName;
	private String lastName;
	private String iban;
	private double salary;
	private FiatCurrency currency;
	private String department;
	private String photo;
	private String jobStyle;
	private int birthYear;
}
