package com.rupali.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import com.rupali.Model.Employee;
import com.rupali.exception.ResourceNotFoundException;
import com.rupali.repository.EmployeeRepository;
import com.rupali.service.serviceimpl.EmployeeServiceImpl;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {


    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    
    

    @BeforeEach
    public void setup(){
        
        employee = Employee.builder()
                .id(1L)
                .firstname("Rupali")
                .lastname("Shinde")
                .email("Rupali@gmail.com")
                .build();
    }
    
    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void saveEmployeeTest(){
        // given - precondition or setup
//        given(employeeRepository.findById(employee.getId()))
//                .willReturn(Optional.empty());

        given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        // when -  action or the behaviour that we are going test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        System.out.println(savedEmployee);
        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method which throws exception")
    @Test
    public void whenExistingIdthenthrowException(){
        given(employeeRepository.findById(employee.getId()))
                .willReturn(Optional.of(employee));

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        // when 
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        // then
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    // JUnit test for getAllEmployees method
    @Test
    public void getAllEmployeeTest(){

        Employee employee1 = Employee.builder()
                .id(2L)
                .firstname("Tony")
                .lastname("Stark")
                .email("tony@gmail.com")
                .build();
        
        List<Employee> emp = new ArrayList<Employee>();
        
        emp.add(employee1);
        emp.add(employee);
        

        given(employeeRepository.findAll()).willReturn(emp);

        List<Employee> employeeList = employeeService.getAllEmployees();

        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    // JUnit test for getAllEmployees method
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList(){

        Employee employee1 = Employee.builder()
                .id(2L)
                .firstname("Tony")
                .lastname("Stark")
                .email("tony@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        List<Employee> employeeList = employeeService.getAllEmployees();

        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    // JUnit test for getEmployeeById method
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject(){
        // given
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when
        Employee savedEmployee = employeeService.getEmpById(employee.getId());

        // then
        assertThat(savedEmployee).isNotNull();

    }

    // JUnit test for updateEmployee method
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("ram@gmail.com");
        employee.setFirstname("Ram");
        
        Employee updatedEmployee = employeeService.updateEmployee(employee, employee.getId());

        assertThat(updatedEmployee.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(updatedEmployee.getFirstname()).isEqualTo("Ram");
    }

    // JUnit test for deleteEmployee method
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing(){
        long employeeId = 1L;

        willDoNothing().given(employeeRepository).deleteById(employeeId);

        employeeService.deleteEmployee(employeeId);

        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

}
