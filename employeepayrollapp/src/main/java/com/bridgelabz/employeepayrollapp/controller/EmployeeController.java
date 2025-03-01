package com.bridgelabz.employeepayrollapp.controller;

import com.bridgelabz.employeepayrollapp.dto.EmployeeDTO;
import com.bridgelabz.employeepayrollapp.model.Employee;
import com.bridgelabz.employeepayrollapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j; // Importing Lombok @Slf4j for logging
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@Slf4j // Enabling logging for EmployeeController
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    // UC-16: Get All Employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        log.info("Fetching all employees");
        return service.getAllEmployees();
    }

    // UC-16: Get All Employees by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        log.info("Fetching employee with ID: {}", id);
        Optional<Employee> employee = service.getEmployeeById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Creating a new employee with validation

    // UC-10: Enable validation for create request
    @PostMapping
    public Employee createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Creating new employee: {}", employeeDTO);
        return service.saveEmployeeDTO(employeeDTO);
    }

    // Updating an existing employee with validation
    @PutMapping("/{id}")

    // UC-10: Enable validation for update request
    public Employee updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Updating employee with ID: {} - New Data: {}", id, employeeDTO);
        return service.updateEmployee(id, employeeDTO);
    }

    // UC-16: Delete Employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.info("Deleting employee with ID: {}", id);
        service.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}