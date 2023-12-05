package com.scd.backend.employeesmanagement.Exception.departmentExceptions;

import com.scd.backend.employeesmanagement.Entity.CustomExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomDepartmentExceptionHandler {

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<CustomExceptionResponse> handleDepartmentNotFoundException(
            DepartmentNotFoundException ex, HttpServletRequest request) {
        String path = request.getRequestURI();
        CustomExceptionResponse response = CustomExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .errorCode(HttpStatus.NOT_FOUND.value())
                .details(ex.getMessage())
                .error("Not Found")
                .path(path)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DepartmentCreationException.class)
    public ResponseEntity<CustomExceptionResponse> handleDepartmentCreationException(
            DepartmentCreationException ex, HttpServletRequest request) {
        String path = request.getRequestURI();
        CustomExceptionResponse response = CustomExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .details(ex.getMessage())
                .error("Bad Request")
                .path(path)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }





}
