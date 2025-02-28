package com.bridgelabz.employeepayrollapp.service;

import com.bridgelabz.employeepayrollapp.dto.EmployeeDTO;
import com.bridgelabz.employeepayrollapp.exception.EmployeeNotFoundException; // UC-12: Import custom exception
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

    // UC-9 Add Database setting as Environment Variable
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

    // Save a new employee using DTO
    public Employee saveEmployeeDTO(EmployeeDTO employeeDTO) {
        log.info("Saving new employee using DTO: {}", employeeDTO);

        // Creating Employee object with all fields from EmployeeDTO
        Employee employee = new Employee(
                employeeDTO.getName(),
                employeeDTO.getSalary(),
                employeeDTO.getGender(),        // UC-13: Added gender field
                employeeDTO.getStartDate(),     // UC-13: Added startDate field
                employeeDTO.getNote(),          // UC-13: Added note field
                employeeDTO.getProfilePic(),    // UC-13: Added profilePic field
                employeeDTO.getDepartment()     // UC-13: Added department field
        );

        if (USE_DATABASE) {
            return repository.save(employee);
        }

        employee.setId(idCounter.getAndIncrement()); // Assign unique ID for in-memory storage
        employeeList.add(employee);
        return employee;
    }

    // Update an existing employee
    public Employee updateEmployee(Long id, EmployeeDTO employeeDetails) {
        log.info("Updating employee with ID: {}", id);
        if (USE_DATABASE) {
            // UC-12: Throw EmployeeNotFoundException if employee ID is not found
            Employee employee = repository.findById(id).orElseThrow(() ->
                    new EmployeeNotFoundException("Employee not found with ID: " + id)
            );

            // Updating employee details
            employee.setName(employeeDetails.getName());
            employee.setSalary(employeeDetails.getSalary());
            employee.setGender(employeeDetails.getGender());      // UC-13: Added gender field update
            employee.setStartDate(employeeDetails.getStartDate());// UC-13: Added startDate field update
            employee.setNote(employeeDetails.getNote());          // UC-13: Added note field update
            employee.setProfilePic(employeeDetails.getProfilePic());// UC-13: Added profilePic field update
            employee.setDepartment(employeeDetails.getDepartment());// UC-13: Added department field update

            return repository.save(employee);
        }

        Optional<Employee> existingEmployee = getEmployeeById(id);
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();

            // Updating employee details
            employee.setName(employeeDetails.getName());
            employee.setSalary(employeeDetails.getSalary());
            employee.setGender(employeeDetails.getGender());
            employee.setStartDate(employeeDetails.getStartDate());
            employee.setNote(employeeDetails.getNote());
            employee.setProfilePic(employeeDetails.getProfilePic());
            employee.setDepartment(employeeDetails.getDepartment());

            return employee;
        }
        throw new EmployeeNotFoundException("Employee not found with ID: " + id); // UC-12: Throw custom exception
    }

    // Delete an employee
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with ID: {}", id);
        if (USE_DATABASE) {
            // UC-12: Throw EmployeeNotFoundException if employee ID is not found
            if (!repository.existsById(id)) {
                throw new EmployeeNotFoundException("Employee not found with ID: " + id);
            }
            repository.deleteById(id);
            return;
        }
        employeeList.removeIf(emp -> emp.getId().equals(id));
    }

    // Get all employees as DTO
    public List<EmployeeDTO> getAllEmployeesDTO() {
        log.info("Fetching all employees as DTOs");
        if (USE_DATABASE) {
            return repository.findAll().stream()
                    .map(emp -> new EmployeeDTO(
                            emp.getName(),
                            emp.getSalary(),
                            emp.getGender(),       // UC-13: Added gender field in DTO
                            emp.getStartDate(),    // UC-13: Added startDate field in DTO
                            emp.getNote(),         // UC-13: Added note field in DTO
                            emp.getProfilePic(),   // UC-13: Added profilePic field in DTO
                            emp.getDepartment()    // UC-13: Added department field in DTO
                    ))
                    .collect(Collectors.toList());
        }
        return employeeList.stream()
                .map(emp -> new EmployeeDTO(
                        emp.getName(),
                        emp.getSalary(),
                        emp.getGender(),
                        emp.getStartDate(),
                        emp.getNote(),
                        emp.getProfilePic(),
                        emp.getDepartment()
                ))
                .collect(Collectors.toList());
    }
}
