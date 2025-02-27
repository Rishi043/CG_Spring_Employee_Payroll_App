package com.bridgelabz.employeepayrollapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    // UC-10: Add validation annotations
    // UC-10: Validation to make name field required
    @NotBlank(message = "Name is required")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Name must contain only alphabets and spaces")
    private String name;

    // Validation for salary field
    @Min(value = 10000, message = "Min Wage should be more than 500")
    private double salary;
}