package com.example.hr.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.hr.domain.FiatCurrency;
import com.example.validation.Iban;
import com.example.validation.TcKimlikNo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class HireEmployeeRequest {
	@TcKimlikNo
	private String identity;
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	@Iban
	private String iban;
	@Min(8_500)
	private double salary;
	private int birthYear;
	@NotNull
	private FiatCurrency currency;
	@NotNull
	private String department;
	@NotNull
	private String photo;
	@NotNull
	private String jobStyle;
}
