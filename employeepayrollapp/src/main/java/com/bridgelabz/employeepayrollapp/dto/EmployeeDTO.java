package com.bridgelabz.employeepayrollapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

// UC-6 Use Lombok Library to auto generate getters and setters for the DTO
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data

public class EmployeeDTO {

// UC-10: Validate that the name field is not empty
          // Validate that the name field follows the pattern
    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Employee name invalid!!")
    private String name;


    private double salary;
}