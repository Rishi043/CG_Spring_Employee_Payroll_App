package com.bridgelabz.employeepayrollapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.time.LocalDate;

@Entity
@Table(name = "employees") // Maps to 'employees' table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 10000, message = "Salary must be at least 10000")
    private double salary;

    // UC-13: Adding gender field
    @NotBlank(message = "Gender is required")
    private String gender;

    // UC-13: Adding startDate field
    @NotNull(message = "Start date is required")
    @Column(name = "start_date", nullable = false) // Ensures proper column mapping
    private LocalDate startDate;

    // UC-13: Adding note field
    private String note;

    // UC-13: Adding profilePic field
    private String profilePic;

    // UC-13: Adding department as a list (stored as separate table for normalization)
    @ElementCollection
    @CollectionTable(name = "employee_departments", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "department")
    private List<String> department;

    // Constructor for creating an employee with new fields
    public Employee(String name, double salary, String gender, LocalDate startDate, String note, String profilePic, List<String> department) {
        this.name = name;
        this.salary = salary;
        this.gender = gender;
        this.startDate = startDate;
        this.note = note;
        this.profilePic = profilePic;
        this.department = department;
    }
}
