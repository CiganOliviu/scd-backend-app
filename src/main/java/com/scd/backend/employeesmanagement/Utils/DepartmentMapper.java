package com.scd.backend.employeesmanagement.Utils;

import com.scd.backend.employeesmanagement.Dtos.DepartmentResponse;
import com.scd.backend.employeesmanagement.Entity.Department;
import com.scd.backend.employeesmanagement.Entity.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentMapper {
    public static DepartmentResponse mapToDepartmentResponse(Department department) {
        List<Long> employeeIds = department.getEmployees()
                .stream()
                .map(Employee::getId)
                .collect(Collectors.toList());

        List<Long> subdepartmentIds = department.getSubdepartments()
                .stream()
                .map(Department::getId)
                .collect(Collectors.toList());

        Long parentDepartmentId = department.getParentDepartment() != null ? department.getParentDepartment().getId() : null;

        return DepartmentResponse.builder()
                .id(department.getId())
                .description(department.getDescription())
                .imageUri(department.getImageUri())
                .employeeIds(employeeIds)
                .subdepartmentIds(subdepartmentIds)
                .parentDepartmentId(parentDepartmentId)
                .build();
    }
}