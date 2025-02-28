package com.bridgelabz.employeepayrollapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Name must contain only alphabets and spaces")
    private String name;

    @Min(value = 10000, message = "Salary must be at least 10000")
    private double salary;

    // UC-13: Adding gender field with validation
    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
    private String gender;

    // UC-13: Adding startDate field
    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    // UC-13: Adding note field
    private String note;

    // UC-13: Adding profilePic field
    private String profilePic;

    // UC-13: Adding department as a list (an employee can belong to multiple departments)
    @NotNull(message = "Department is required")
    private List<String> department;
}
