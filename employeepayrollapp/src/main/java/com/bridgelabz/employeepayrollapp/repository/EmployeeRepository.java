package com.bridgelabz.employeepayrollapp.repository;

import com.bridgelabz.employeepayrollapp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository // UC-15: Marking as Repository Layer
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // UC-17: Custom Query to get employees belonging to the Sales department
    @Query("SELECT e FROM Employee e WHERE 'Sales' MEMBER OF e.department")
    List<Employee> findEmployeesBySalesDepartment();
}
