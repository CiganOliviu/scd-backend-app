package com.scd.backend.employeesmanagement.Dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailRequest {
    private List<String> emails;
    private String subject;
    private String message;
}
