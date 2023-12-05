package com.scd.backend.employeesmanagement.Controller;

import com.scd.backend.employeesmanagement.Dtos.EmployeeRequest;
import com.scd.backend.employeesmanagement.Dtos.EmployeeResponse;
import com.scd.backend.employeesmanagement.Services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        List<EmployeeResponse> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        EmployeeResponse employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity<String> createEmployee(@Valid @RequestBody EmployeeRequest employee) {
        employeeService.saveEmployee(employee);
        return ResponseEntity.ok("Employee saved");
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest employee) {
        employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }

    @GetMapping("/in-department/{departmentId}")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployeesInDepartmentAndSubdepartment(@PathVariable Long departmentId) {
        List<EmployeeResponse> employees = employeeService.getAllEmployeesInDepartmentAndSubdepartments(departmentId);
        return ResponseEntity.ok(employees);
    }
}
