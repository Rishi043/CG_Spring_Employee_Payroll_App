package com.bridgelabz.employeepayrollapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.bridgelabz.employeepayrollapp.model.Employee;
import org.springframework.stereotype.Repository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}