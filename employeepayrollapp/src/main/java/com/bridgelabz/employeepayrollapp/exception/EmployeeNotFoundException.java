package com.bridgelabz.employeepayrollapp.exception;

// UC-12: Custom Exception for Employee Not Found
public class EmployeeNotFoundException extends RuntimeException {

    // Constructor
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
