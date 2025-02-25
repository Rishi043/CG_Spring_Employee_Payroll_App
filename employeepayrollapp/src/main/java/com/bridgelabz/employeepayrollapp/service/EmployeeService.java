package com.bridgelabz.employeepayrollapp.service;

import com.bridgelabz.employeepayrollapp.dto.EmployeeDTO;
import com.bridgelabz.employeepayrollapp.model.Employee;
import com.bridgelabz.employeepayrollapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return repository.findById(id);
    }

    public Employee saveEmployee(Employee employee) {
        return repository.save(employee);
    }
    // Update existing employee
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = repository.findById(id).orElseThrow();
        employee.setName(employeeDetails.getName());
        employee.setSalary(employeeDetails.getSalary());
        return repository.save(employee);
    }
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }


    //uc3


    public EmployeeDTO saveEmployeeDTO(EmployeeDTO employeeDTO) {
        Employee employee = new Employee(employeeDTO.getName1(), employeeDTO.getSalary1());

        Employee savedEmployee = repository.save(employee);

        // Now this will work because EmployeeDTO has a matching constructor
        return new EmployeeDTO(savedEmployee.getName(), savedEmployee.getSalary());
    }



    public List<EmployeeDTO> getAllEmployeesDTO() {
        return repository.findAll().stream()
                .map(emp -> new EmployeeDTO(emp.getName(), emp.getSalary()))
                .collect(Collectors.toList());
    }
}