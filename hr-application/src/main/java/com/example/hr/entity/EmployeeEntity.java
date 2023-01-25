package com.example.hr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

import com.example.hr.domain.FiatCurrency;
import com.example.validation.Iban;
import com.example.validation.TcKimlikNo;

import lombok.Data;

@Entity
@Table(name="employees")
@DynamicUpdate
@Data
public class EmployeeEntity {
	@TcKimlikNo
	@Id
	private String identity;
	@NotEmpty
	@Column(name="fname")
	private String firstName;
	@NotEmpty
	@Column(name="lname")
	private String lastName;
	@Iban
	private String iban;
	@Min(8_500)
	private double salary;
	@Column(name="byear")
	private int birthYear;
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private FiatCurrency currency;
	@NotNull
	private String department;
	@NotNull
	@Lob
	@Column(columnDefinition = "longblob")
	private byte[] photo;
	@NotNull
	private String jobStyle;
}
