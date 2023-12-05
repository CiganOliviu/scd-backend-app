package com.scd.backend.employeesmanagement.Utils;

import com.scd.backend.employeesmanagement.Dtos.EmployeeResponse;
import com.scd.backend.employeesmanagement.Entity.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {
    public static EmployeeResponse mapToEmployeeResponse(Employee employee) {
        Long managerId = employee.getManager() != null ? employee.getManager().getId() : null;
        Long departmentId = employee.getDepartment() != null ? employee.getDepartment().getId() : null;

        List<Long> subordinateIDs = employee.getSubordinates()
                .stream()
                .map(Employee::getId)
                .collect(Collectors.toList());

        return EmployeeResponse.builder()
                .id(employee.getId())
                .email(employee.getEmail())
                .name(employee.getName())
                .managerId(managerId)
                .subordinateIDs(subordinateIDs)
                .departmentId(departmentId)
                .imageUri(employee.getImageUri())
                .build();
    }
}
