package com.example.hr.domain;

import java.util.Objects;

import com.example.ddd.DomainEntity;
import com.example.hr.domain.exception.PolicyException;

// Ubiquitous language: Employee, TcKimlikNo, FullName, Photo, JobStyle
// Entity Class: i. identity, ii. Mutable
@DomainEntity(identity = "identityNo", aggregate = true)
public class Employee {
	public static final Money MIN_SALARY=new Money(8_500); 
	private final TcKimlikNo identityNo;
	private FullName fullname;
	private Money salary;
	private Iban iban;
	private final BirthYear birthYear;
	private Photo photo;
	private Department department;
	private JobStyle jobStyle;

	public Employee(TcKimlikNo identityNo, FullName fullname, Money salary, Iban iban, BirthYear birthYear, Photo photo,
			Department department, JobStyle jobStyle) {
		this.identityNo = identityNo;
		this.fullname = fullname;
		this.salary = salary;
		this.iban = iban;
		this.birthYear = birthYear;
		this.photo = photo;
		this.department = department;
		this.jobStyle = jobStyle;
	}

	public Employee(Builder builder) {
		this.identityNo = builder.identityNo;
		this.fullname = builder.fullname;
		this.salary = builder.salary;
		this.iban = builder.iban;
		this.birthYear = builder.birthYear;
		this.photo = builder.photo;
		this.department = builder.department;
		this.jobStyle = builder.jobStyle;
	}

	public static class Builder {
		private TcKimlikNo identityNo;
		private FullName fullname;
		private Money salary;
		private Iban iban;
		private BirthYear birthYear;
		private Photo photo;
		private Department department;
		private JobStyle jobStyle;

		public Builder(TcKimlikNo identityNo) {
			this.identityNo = identityNo;
		}

		public Builder fullname(String firstName, String lastName) {
			this.fullname = new FullName(firstName, lastName);
			return this;
		}

		public Builder salary(double amount, FiatCurrency currency) {
			this.salary = new Money(amount, currency);
			return this;
		}

		public Builder salary(double amount) {
			return salary(amount, FiatCurrency.TL);
		}

		public Builder iban(String value) {
			this.iban = Iban.of(value);
			return this;
		}

		public Builder birthYear(int value) {
			this.birthYear = new BirthYear(value);
			return this;
		}

		public Builder department(String value) {
			this.department = Department.valueOf(value);
			return this;
		}

		public Builder jobStyle(String value) {
			this.jobStyle = JobStyle.valueOf(value);
			return this;
		}

		public Builder photo(byte[] values) {
			this.photo = Photo.of(values);
			return this;
		}

		public Builder photo(String values) {
			this.photo = Photo.of(values);
			return this;
		}
		public Employee build() {
			// Business Rule
			// Validation
			// Constraint
			// Policy
			// Invariant
			return new Employee(this);
		}
		public void increaseSalary(double rate) throws PolicyException {
			this.salary = this.salary.multiply(1+rate/100.);	
			// Business Rule
			// Validation
			// Constraint
			// Policy
			if (this.department.equals(Department.IT) 
					&& this.salary.lessThan(MIN_SALARY.multiply(400)))
				throw new PolicyException("IT Salary must be high.");
			// Invariant
		}
		
		public void changePhoto(Photo photo) {
			// Test whether human face 
			// Test for the standard IEEE-42
			this.photo = photo;
		}
		
		public void changeDepartment(Department department) {
			// 
			this.department = department;
		}
	}

	public static Money getMinSalary() {
		return MIN_SALARY;
	}

	public TcKimlikNo getIdentityNo() {
		return identityNo;
	}

	public FullName getFullname() {
		return fullname;
	}

	public Money getSalary() {
		return salary;
	}

	public Iban getIban() {
		return iban;
	}

	public BirthYear getBirthYear() {
		return birthYear;
	}

	public Photo getPhoto() {
		return photo;
	}

	public Department getDepartment() {
		return department;
	}

	public JobStyle getJobStyle() {
		return jobStyle;
	}

	@Override
	public int hashCode() {
		return Objects.hash(identityNo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(identityNo, other.identityNo);
	}

	@Override
	public String toString() {
		return "Employee [identityNo=" + identityNo + ", fullname=" + fullname + ", salary=" + salary + ", iban=" + iban
				+ ", birthYear=" + birthYear + ", department=" + department + ", jobStyle=" + jobStyle + "]";
	}

}
