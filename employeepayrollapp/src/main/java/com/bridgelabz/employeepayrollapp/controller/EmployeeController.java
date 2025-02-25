package com.bridgelabz.employeepayrollapp.controller;

import com.bridgelabz.employeepayrollapp.dto.EmployeeDTO;
import com.bridgelabz.employeepayrollapp.model.Employee;
import com.bridgelabz.employeepayrollapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    // Get all employees - UC1
    @GetMapping
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = service.getEmployeeById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new employee
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        System.out.println("Received Employee: " + employee); // Debugging
        Employee savedEmployee = service.saveEmployee(employee);
        System.out.println("Saved Employee: " + savedEmployee); // Debugging
        return savedEmployee;
    }

    // Update an employee - UC2
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        return service.updateEmployee(id, employeeDetails);
    }

    // Delete an employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // UC3 - Handle EmployeeDTO separately with a unique endpoint
    @PostMapping("/dto")
    public EmployeeDTO createEmployeeDTO(@RequestBody EmployeeDTO employeeDTO) {
        return service.saveEmployeeDTO(employeeDTO);
    }

    @GetMapping("/dto")
    public List<EmployeeDTO> getEmployeesDTO() {
        return service.getAllEmployeesDTO();
    }
}