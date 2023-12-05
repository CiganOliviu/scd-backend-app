package com.scd.backend.employeesmanagement.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private Long id;
    private String name;
    private String email;
    private String imageUri;
    private Long departmentId;
    private Long managerId;
    private List<Long> subordinateIDs;
}
