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

@RestController // UC-17: Marks this as a REST Controller
@RequestMapping("/employees")
@Slf4j // Enabling logging for EmployeeController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // UC-17: Retrieve Employees from Sales Department
    @GetMapping("/department/sales")
    public ResponseEntity<List<Employee>> getSalesEmployees() {
        log.info("Fetching employees from Sales department");
        return ResponseEntity.ok(employeeService.getEmployeesBySalesDepartment());
    }

    // UC-16: Get All Employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        log.info("Fetching all employees");
        return employeeService.getAllEmployees();
    }

    // UC-16: Get Employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        log.info("Fetching employee with ID: {}", id);
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UC-10: Create a new employee with validation
    @PostMapping
    public Employee createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Creating new employee: {}", employeeDTO);
        return employeeService.saveEmployeeDTO(employeeDTO);
    }

    // UC-10: Update an existing employee with validation
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Updating employee with ID: {} - New Data: {}", id, employeeDTO);
        return employeeService.updateEmployee(id, employeeDTO);
    }

    // UC-16: Delete Employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.info("Deleting employee with ID: {}", id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
