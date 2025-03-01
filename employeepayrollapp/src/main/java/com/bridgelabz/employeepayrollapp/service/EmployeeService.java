package com.bridgelabz.employeepayrollapp.service;

import com.bridgelabz.employeepayrollapp.dto.EmployeeDTO;
import com.bridgelabz.employeepayrollapp.exception.EmployeeNotFoundException;
import com.bridgelabz.employeepayrollapp.model.Employee;
import com.bridgelabz.employeepayrollapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository; // UC-15: Injecting JPA Repository

    // UC-16: Get All Employees
    public List<Employee> getAllEmployees() {
        log.info("Fetching all employees from DB"); // UC-15: Logging DB interaction
        return repository.findAll(); // UC-15: Fetch from DB
    }

    // UC-16: Get Employee by ID
    public Optional<Employee> getEmployeeById(Long id) {
        log.info("Fetching employee with ID: {}", id);
        return repository.findById(id); // UC-15: Fetch from DB
    }

    // Save a new employee using DTO
    public Employee saveEmployeeDTO(EmployeeDTO employeeDTO) {
        log.info("Saving new employee to DB: {}", employeeDTO); // UC-15: Log DB save

        Employee employee = new Employee(
                null, // UC-15: ID is auto-generated
                employeeDTO.getName(),
                employeeDTO.getSalary(),
                employeeDTO.getGender(),
                employeeDTO.getStartDate(),
                employeeDTO.getNote(),
                employeeDTO.getProfilePic(),
                employeeDTO.getDepartment()
        );

        return repository.save(employee); // UC-15: Save in DB
    }

    //  UC-16: Update Employee
    public Employee updateEmployee(Long id, EmployeeDTO employeeDetails) {
        log.info("Updating employee with ID: {}", id);

        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + id));

        // Updating employee details
        employee.setName(employeeDetails.getName());
        employee.setSalary(employeeDetails.getSalary());
        employee.setGender(employeeDetails.getGender());
        employee.setStartDate(employeeDetails.getStartDate());
        employee.setNote(employeeDetails.getNote());
        employee.setProfilePic(employeeDetails.getProfilePic());
        employee.setDepartment(employeeDetails.getDepartment());

        return repository.save(employee); // UC-15: Update in DB
    }

    // UC-16: Delete Employee
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with ID: {}", id);
        if (!repository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + id);
        }
        repository.deleteById(id); // UC-15: Delete from DB
    }
}