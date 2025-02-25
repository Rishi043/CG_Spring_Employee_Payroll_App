package com.bridgelabz.employeepayrollapp.service;

import com.bridgelabz.employeepayrollapp.dto.EmployeeDTO;
import com.bridgelabz.employeepayrollapp.model.Employee;
import com.bridgelabz.employeepayrollapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j; // UC-7: Importing Lombok @Slf4j annotation for logging

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Slf4j // UC-7: Lombok annotation to enable logging
public class EmployeeService {

    private static final boolean USE_DATABASE = true; 

    @Autowired
    private EmployeeRepository repository; // JPA Repository

    private final List<Employee> employeeList = new ArrayList<>(); // In-memory storage
    private final AtomicLong idCounter = new AtomicLong(1); // ID generator for in-memory mode

    // Get all employees
    public List<Employee> getAllEmployees() {
        log.info("Fetching all employees");
        if (USE_DATABASE) {
            return repository.findAll();
        }
        return employeeList;
    }

    // Get employee by ID
    public Optional<Employee> getEmployeeById(Long id) {
        log.info("Fetching employee with ID: {}", id);
        if (USE_DATABASE) {
            return repository.findById(id);
        }
        return employeeList.stream().filter(emp -> emp.getId().equals(id)).findFirst();
    }

    // Save a new employee
    public Employee saveEmployee(Employee employee) {
        log.info("Saving new employee: {}", employee);
        if (USE_DATABASE) {
            return repository.save(employee);
        }
        employee.setId(idCounter.getAndIncrement()); // Assign unique ID for in-memory storage
        employeeList.add(employee);
        return employee;
    }

    // Update an existing employee
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        log.info("Updating employee with ID: {}", id);
        if (USE_DATABASE) {
            Employee employee = repository.findById(id).orElseThrow();
            employee.setName(employeeDetails.getName());
            employee.setSalary(employeeDetails.getSalary());
            return repository.save(employee);
        }

        Optional<Employee> existingEmployee = getEmployeeById(id);
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            employee.setName(employeeDetails.getName());
            employee.setSalary(employeeDetails.getSalary());
            return employee;
        }
        throw new RuntimeException("Employee not found with ID: " + id);
    }

    // Delete an employee
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with ID: {}", id);
        if (USE_DATABASE) {
            repository.deleteById(id);
            return;
        }
        employeeList.removeIf(emp -> emp.getId().equals(id));
    }

    // Save Employee using DTO
    public EmployeeDTO saveEmployeeDTO(EmployeeDTO employeeDTO) {
        log.info("Saving new employee using DTO: {}", employeeDTO);
        if (USE_DATABASE) {
            Employee employee = new Employee(employeeDTO.getName(), employeeDTO.getSalary());
            Employee savedEmployee = repository.save(employee);
            return new EmployeeDTO(savedEmployee.getName(), savedEmployee.getSalary());
        }

        Employee employee = new Employee(employeeDTO.getName(), employeeDTO.getSalary());
        employee.setId(idCounter.getAndIncrement()); // Assign unique ID for in-memory storage
        employeeList.add(employee);
        return new EmployeeDTO(employee.getName(), employee.getSalary());
    }

    // Get all employees as DTO
    public List<EmployeeDTO> getAllEmployeesDTO() {
        log.info("Fetching all employees as DTOs");
        if (USE_DATABASE) {
            return repository.findAll().stream()
                    .map(emp -> new EmployeeDTO(emp.getName(), emp.getSalary()))
                    .collect(Collectors.toList());
        }
        return employeeList.stream()
                .map(emp -> new EmployeeDTO(emp.getName(), emp.getSalary()))
                .collect(Collectors.toList());
    }
}
