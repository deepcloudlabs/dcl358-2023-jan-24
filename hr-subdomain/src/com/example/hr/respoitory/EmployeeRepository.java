package com.example.hr.respoitory;

import java.util.Optional;

import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;

public interface EmployeeRepository {

	Optional<Employee> findEmployeeByIdentityNo(TcKimlikNo identityNo);

	Employee persist(Employee employee);

	Optional<Employee> removeEmployee(TcKimlikNo identity);

}
