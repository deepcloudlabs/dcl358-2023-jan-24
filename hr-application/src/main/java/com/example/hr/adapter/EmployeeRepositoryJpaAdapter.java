package com.example.hr.adapter;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.entity.EmployeeEntity;
import com.example.hr.repository.EmployeeEntityRepository;
import com.example.hr.respoitory.EmployeeRepository;

@Repository
public class EmployeeRepositoryJpaAdapter implements EmployeeRepository {
	private final EmployeeEntityRepository empRepo;
	private final ModelMapper modelMapper;
	
	public EmployeeRepositoryJpaAdapter(EmployeeEntityRepository empRepo, ModelMapper modelMapper) {
		this.empRepo = empRepo;
		this.modelMapper = modelMapper;
	}

	@Override
	public Optional<Employee> findEmployeeByIdentityNo(TcKimlikNo identityNo) {
		var employeeEntity = empRepo.findById(identityNo.getValue());
		if (employeeEntity.isEmpty())
			return Optional.empty();
		return Optional.of(modelMapper.map(employeeEntity.get(),Employee.class));
	}

	@Override
	@Transactional
	public Employee persist(Employee employee) {
		var persistedEmployee = empRepo.save(modelMapper.map(employee, EmployeeEntity.class));
		return modelMapper.map(persistedEmployee, Employee.class);
	}

	@Override
	@Transactional
	public Optional<Employee> removeEmployee(TcKimlikNo identity) {
		var employeeEntity = empRepo.findById(identity.getValue());
		if (employeeEntity.isEmpty())
			return Optional.empty();
		empRepo.deleteById(identity.getValue());
		return Optional.of(modelMapper.map(employeeEntity.get(), Employee.class));
	}

}
