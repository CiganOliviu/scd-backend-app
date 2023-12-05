package com.scd.backend.employeesmanagement.Dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequest{
    @NotBlank(message = "Description is mandatory")
    private String description;
    private String imageUri;
}
