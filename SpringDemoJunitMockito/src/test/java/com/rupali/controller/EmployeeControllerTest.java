package com.rupali.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rupali.Model.Employee;
import com.rupali.service.EmployeeService;


@WebMvcTest
class EmployeeControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void createEmployee_returnsavedEmployeeTest() throws Exception{
    	
    	 Employee employee = Employee.builder()
                 .firstname("Sharvil")
                 .lastname("Nitin")
                 .email("sharvil@gmail.com")
                 .build();
    	 
         given(employeeService.saveEmployee(any(Employee.class)))
                 .willAnswer((invocation)-> invocation.getArgument(0));

         // when - action or behaviour that we are going test
         ResultActions response = mockMvc.perform(post("/api/employees")
             .contentType(MediaType.APPLICATION_JSON)
             .content(objectMapper.writeValueAsString(employee)));

         // then - verify the result or output using assert statements
         response.andDo(print()).
                 andExpect(status().isCreated())
                 .andExpect(jsonPath("$.firstname",
                         is(employee.getFirstname())))
                 .andExpect(jsonPath("$.lastname",
                         is(employee.getLastname())))
                 .andExpect(jsonPath("$.email",
                         is(employee.getEmail())));

    }

	

@Test
public void getAllEmployeesListTest() throws Exception{
    // given - precondition or setup
    List<Employee> listOfEmployees = new ArrayList<>();
    listOfEmployees.add(Employee.builder().firstname("Rano").lastname("Prabhu").email("rano@gmail.com").build());
    listOfEmployees.add(Employee.builder().firstname("Charle").lastname("Roy").email("Charle@gmail.com").build());
   
    //mocking
    given(employeeService.getAllEmployees()).willReturn(listOfEmployees);

    // when -  action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(get("/api/employees"));

    // then - verify the output
    response.andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.size()",
                    is(listOfEmployees.size())));

}


@Test
public void getEmployeeByIdTest() throws Exception{
    // given - precondition or setup
    long employeeId = 1L;
    Employee employee = Employee.builder()
            .firstname("Rupali")
            .lastname("Shinde")
            .email("Rups@gmail.com")
            .build();
    
    //Stubb
    given(employeeService.getEmpById(employeeId)).willReturn(employee);

    // when 
    ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

    // then - verify the output
    response.andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.firstname", is(employee.getFirstname())))
            .andExpect(jsonPath("$.lastname", is(employee.getLastname())))
            .andExpect(jsonPath("$.email", is(employee.getEmail())));

}


@Test
public void NegativeScenario_GetEmployeeByIdTest() throws Exception{
    // given - precondition or setup
    long employeeId = 1L;
    
    Employee employee = null;
    
    given(employeeService.getEmpById(employeeId)).willReturn(employee);

    // when -  action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

    // then - verify the output
    response.andExpect(status().isNotFound())
            .andDo(print());

}

@Test
public void updateEmployeeTest() throws Exception{
    // given - precondition or setup
    long employeeId = 1L;
    Employee savedEmployee = Employee.builder()
            .firstname("Rupali")
            .lastname("Shinde")
            .email("rupali@gmail.com")
            .build();

    Employee updatedEmployee = Employee.builder()
            .firstname("Nitin")
            .lastname("Shelar")
            .email("nitin@gmail.com")
            .build();
    
    given(employeeService.getEmpById(employeeId)).willReturn(savedEmployee);
    given(employeeService.updateEmployee(updatedEmployee,employeeId))
            .willAnswer((invocation)-> invocation.getArgument(0));

    // when -  action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updatedEmployee)));


    // then - verify the output
    response.andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.firstname", is(updatedEmployee.getFirstname())))
            .andExpect(jsonPath("$.lastname", is(updatedEmployee.getLastname())))
            .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
}



//JUnit test for delete employee REST API
 @Test
 public void deleteEmployeeTest() throws Exception{
     // given - precondition or setup
     long employeeId = 1L;
     willDoNothing().given(employeeService).deleteEmployee(employeeId);

     // when -  action or the behaviour that we are going test
     ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employeeId));

     // then - verify the output
     response.andExpect(status().isOk())
             .andDo(print());
 }
}