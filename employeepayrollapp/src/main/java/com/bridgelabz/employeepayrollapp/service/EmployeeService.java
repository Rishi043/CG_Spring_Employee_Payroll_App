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

@Slf4j
@Service // UC-17: Business logic layer for Employee
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository; // UC-15: Injecting JPA Repository

    // UC-17: Retrieve Employees from Sales Department
    public List<Employee> getEmployeesBySalesDepartment() {
        log.info("Fetching employees from Sales department");
        return repository.findEmployeesBySalesDepartment();
    }

    // UC-16: Get All Employees
    public List<Employee> getAllEmployees() {
        log.info("Fetching all employees from DB");
        return repository.findAll();
    }

    // UC-16: Get Employee by ID
    public Optional<Employee> getEmployeeById(Long id) {
        log.info("Fetching employee with ID: {}", id);
        return repository.findById(id);
    }

    // UC-10: Save a new employee using DTO
    public Employee saveEmployeeDTO(EmployeeDTO employeeDTO) {
        log.info("Saving new employee to DB: {}", employeeDTO);

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

    // UC-16: Update Employee
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

        return repository.save(employee);
    }

    // UC-16: Delete Employee
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with ID: {}", id);
        if (!repository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}
