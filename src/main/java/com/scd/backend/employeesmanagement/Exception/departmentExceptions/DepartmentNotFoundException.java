package com.scd.backend.employeesmanagement.Exception.departmentExceptions;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(Long id) {
        super("The requested department with ID " + id + " does not exist in the system.");
    }
}

