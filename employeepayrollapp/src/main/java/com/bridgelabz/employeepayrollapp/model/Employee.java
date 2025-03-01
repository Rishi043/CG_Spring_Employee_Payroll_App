package com.bridgelabz.employeepayrollapp.model;

import com.fasterxml.jackson.annotation.JsonFormat; // UC-14: Importing for date format
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j; // UC-15: Importing Lombok Logging

import java.time.LocalDate;
import java.util.List;

@Entity // UC-15: Mark this class as a JPA Entity to store in MySQL
@Table(name = "employees") // UC-15: Maps to 'employees' table in MySQL
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j // UC-15: Enable logging for database operations
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // UC-15: Auto-generates unique ID
    private Long id;

    @NotBlank(message = "Name is required") // UC-14: Validation for non-blank name
    private String name;

    @Min(value = 10000, message = "Salary must be at least 10000") // UC-14: Minimum salary validation
    private double salary;

    @NotBlank(message = "Gender is required") // UC-14: Gender validation
    @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other") // UC-14: Gender pattern check
    private String gender;

    @NotNull(message = "Start date is required") // UC-14: Start date cannot be null
    @PastOrPresent(message = "Start date cannot be in the future") // UC-14: Ensures past or present date
    @JsonFormat(pattern = "dd MMM yyyy") // UC-14: Store date in '28 Feb 2024' format
    @Column(name = "start_date", nullable = false) // UC-14: Maps to 'start_date' column
    private LocalDate startDate;

    @NotBlank(message = "Note cannot be blank") // UC-14: Validation for non-empty note
    private String note;

    @NotBlank(message = "Profile picture URL cannot be blank") // UC-14: Validation for non-empty profilePic
    private String profilePic;

    @NotEmpty(message = "Department is required") // UC-14: Ensures at least one department is selected
    @ElementCollection // UC-15: Store list in a separate table
    @CollectionTable(name = "employee_departments", joinColumns = @JoinColumn(name = "employee_id")) // UC-15: Create department table
    @Column(name = "department") // UC-15: Maps column name
    private List<String> department;

    // UC-15: Constructor for creating an employee with all fields
    public Employee(String name, double salary, String gender, LocalDate startDate, String note, String profilePic, List<String> department) {
        log.info("Creating Employee: {}", name); // UC-15: Log employee creation
        this.name = name;
        this.salary = salary;
        this.gender = gender;
        this.startDate = startDate;
        this.note = note;
        this.profilePic = profilePic;
        this.department = department;
    }
}
