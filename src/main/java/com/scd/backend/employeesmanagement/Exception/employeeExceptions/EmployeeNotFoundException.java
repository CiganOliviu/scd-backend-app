package com.scd.backend.employeesmanagement.Exception.employeeExceptions;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long id) {
        super("The requested Employee with ID " + id + " does not exist in the system.");
    }
}

