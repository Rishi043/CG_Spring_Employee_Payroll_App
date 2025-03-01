package com.bridgelabz.employeepayrollapp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

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

    // UC-14: Used @JsonFormat(pattern="dd MMM yyyy")
    @JsonFormat(pattern = "dd MMM yyyy") // Formatting startDate for JSON responses

    @PastOrPresent(message = "Start date must be in the past or present")
    private LocalDate startDate;

    // UC-13: Adding note field
    // Ensuring note is not blank
    @NotBlank(message = "Note cannot be blank")
    private String note;

    // UC-13: Adding profilePic field
    // Ensuring profilePic is not blank and follows a URL format
    @NotBlank(message = "Profile picture URL is required")
    @Pattern(regexp = "^(http|https)://.*$", message = "Profile picture must be a valid URL")
    private String profilePic;

    // UC-13: Adding department as a list (an employee can belong to multiple departments)
    // Ensuring department is not null or empty
    @NotNull(message = "Department is required")
    @Size(min = 1, message = "At least one department must be specified")
    private List<String> department;
}
