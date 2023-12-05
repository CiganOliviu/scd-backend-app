package com.scd.backend.employeesmanagement.Entity;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomExceptionResponse {
    private LocalDateTime timestamp;
    private int errorCode;
    private String error;
    private String details;
    private String path;
}
