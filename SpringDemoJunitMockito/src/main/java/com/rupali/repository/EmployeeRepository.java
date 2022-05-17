package com.rupali.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rupali.Model.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	
}
