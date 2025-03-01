// Creating Class with name EmployeeService to handle business logic for Employee operations
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
@Slf4j // Enabling logging for EmployeeService
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    // Fetching all employees
    public List<Employee> getAllEmployees() {
        log.info("Fetching all employees from database");
        return repository.findAll();
    }

    // Fetching employee by ID
    public Optional<Employee> getEmployeeById(Long id) {
        log.info("Fetching employee with ID: {}", id);
        return repository.findById(id);
    }

    // Saving a new employee with validations
    public Employee saveEmployeeDTO(EmployeeDTO employeeDTO) {
        log.info("Saving new employee: {}", employeeDTO);

        Employee employee = new Employee(
                employeeDTO.getName(),
                employeeDTO.getSalary(),
                employeeDTO.getGender(),
                employeeDTO.getStartDate(),
                employeeDTO.getNote(),
                employeeDTO.getProfilePic(),
                employeeDTO.getDepartment()
        );

        return repository.save(employee);
    }

    // Updating an existing employee with validations
    public Employee updateEmployee(Long id, EmployeeDTO employeeDetails) {
        log.info("Updating employee with ID: {}", id);

        Employee employee = repository.findById(id).orElseThrow(() ->
                new EmployeeNotFoundException("Employee not found with ID: " + id));

        employee.setName(employeeDetails.getName());
        employee.setSalary(employeeDetails.getSalary());
        employee.setGender(employeeDetails.getGender());
        employee.setStartDate(employeeDetails.getStartDate());
        employee.setNote(employeeDetails.getNote());
        employee.setProfilePic(employeeDetails.getProfilePic());
        employee.setDepartment(employeeDetails.getDepartment());

        return repository.save(employee);
    }

    // Deleting an employee by ID
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}
