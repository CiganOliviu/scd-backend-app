package com.scd.backend.employeesmanagement.Controller;

import com.scd.backend.employeesmanagement.Dtos.DepartmentRequest;
import com.scd.backend.employeesmanagement.Dtos.DepartmentResponse;
import com.scd.backend.employeesmanagement.Dtos.EmployeeResponse;
import com.scd.backend.employeesmanagement.Services.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {
        List<DepartmentResponse> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable Long id) {
        DepartmentResponse department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }

    @PostMapping
    public ResponseEntity<Object> createDepartment(@Valid @RequestBody DepartmentRequest department) {
        return departmentService.saveDepartment(department);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(@PathVariable Long id, @RequestBody DepartmentRequest department) {
        departmentService.updateDepartment(id, department);
        return ResponseEntity.ok(departmentService.updateDepartment(id, department));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDepartment(@PathVariable Long id) {
        return departmentService.deleteDepartment(id);
    }

    @PutMapping("/{departmentId}/{employeeId}")
    public ResponseEntity<Object> addEmployeeToDepartment(@PathVariable Long employeeId, @PathVariable Long departmentId) {
        return departmentService.addEmployeeToDepartment(employeeId,departmentId);
    }

    @GetMapping("/{departmentId}/employees")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesInDepartment(@PathVariable Long departmentId) {
        List<EmployeeResponse> employees = departmentService.getEmployeesInDepartment(departmentId);
        return ResponseEntity.ok(employees);
    }

    @PutMapping("subdepartment/{departmentId}/{subdepartmentId}")
    public ResponseEntity<Object> addSubdepartmentToDepartment(@PathVariable Long departmentId, @PathVariable Long subdepartmentId) {
        return ResponseEntity.ok(departmentService.addSubdepartment(departmentId,subdepartmentId));
    }

    @GetMapping("subdepartment/{parentDepartmentId}")
    public ResponseEntity<List<DepartmentResponse>> getAllSubdepartments(@PathVariable Long parentDepartmentId) {
        return ResponseEntity.ok(departmentService.getAllSubdepartments(parentDepartmentId));
    }
}
