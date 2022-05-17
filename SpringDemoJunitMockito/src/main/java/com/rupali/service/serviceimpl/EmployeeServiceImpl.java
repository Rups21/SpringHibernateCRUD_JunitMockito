package com.rupali.service.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rupali.Model.Employee;
import com.rupali.exception.ResourceNotFoundException;
import com.rupali.repository.EmployeeRepository;
import com.rupali.service.EmployeeService;


@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private EmployeeRepository empRepo;
	
	

	public EmployeeServiceImpl(EmployeeRepository empRepo) {
		super();
		this.empRepo = empRepo;
	}



	@Override
	public Employee saveEmployee(Employee employee) {
		return empRepo.save(employee);
	}



	@Override
	public List<Employee> getAllEmployees() {
		return empRepo.findAll();
	}



	@Override
	public Employee getEmpById(Long Id) {
		Optional<Employee> employee = empRepo.findById(Id);
		if(employee.isPresent()) {
			
			return employee.get();
		}
		else {
			throw new ResourceNotFoundException("Employee", "Id", Id);
		}
		
	}



	@Override
	public Employee updateEmployee(Employee employee, long id) {
		
		Employee existingEmployee = empRepo.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Employee","Id",id));
		
		existingEmployee.setFirstname(employee.getFirstname());
		existingEmployee.setLastname(employee.getLastname());
		existingEmployee.setEmail(employee.getEmail());
		
		empRepo.save(existingEmployee);
		return existingEmployee;
	}



	@Override
	public void deleteEmployee(Long Id) {
		
		empRepo.findById(Id).orElseThrow(
				()-> new ResourceNotFoundException("Employee","Id",Id));
		
		empRepo.deleteById(Id);
		
	}
	
}
