package com.rupali.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rupali.Model.Employee;
import com.rupali.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private EmployeeService empService;

	public EmployeeController(EmployeeService empService) {
		super();
		this.empService = empService;
	}
	
	
	//create employee Restapi
	@PostMapping
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee){
		
		return new ResponseEntity<Employee>(empService.saveEmployee(employee),HttpStatus.CREATED);
	}
	
	//get all employee 
	@GetMapping
	public List<Employee> getAllEmployees(){
		
		return empService.getAllEmployees();
	}
	
	//get employee by Id
	@GetMapping("{id}")
	public ResponseEntity<Employee>getEmployeeById(@PathVariable("id") long empId){
		return new ResponseEntity<Employee>(empService.getEmpById(empId), HttpStatus.OK);
	}
	
	
	//update employee
	@PutMapping("{id}")
	public ResponseEntity<Employee>updateEmployee(@PathVariable("id") long empId, @RequestBody Employee employee){
		
		return new ResponseEntity<Employee>(empService.updateEmployee(employee, empId),HttpStatus.OK);
	}
	
	
	@DeleteMapping("{id}")
	public ResponseEntity<String>deleteEmployee(@PathVariable("id") long empid){
		empService.deleteEmployee(empid);
		return new ResponseEntity<String>("Employee delete success",HttpStatus.OK);
	}
}

