package com.rupali.service;

import java.util.List;

import com.rupali.Model.Employee;

public interface EmployeeService {

	Employee saveEmployee(Employee employee);
	 List<Employee> getAllEmployees();
	Employee getEmpById(Long Id);
	Employee updateEmployee(Employee employee, long id);
	void deleteEmployee(Long Id);
}
