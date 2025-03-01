package com.bridgelabz.employeepayrollapp.repository;

import com.bridgelabz.employeepayrollapp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // UC-15: Marks this as a Spring Data Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // UC-15: JpaRepository provides built-in CRUD operations
}
