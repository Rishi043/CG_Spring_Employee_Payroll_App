package com.bridgelabz.employeepayrollapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.bridgelabz.employeepayrollapp.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}