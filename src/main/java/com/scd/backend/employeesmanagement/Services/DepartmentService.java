package com.scd.backend.employeesmanagement.Services;

import com.scd.backend.employeesmanagement.Dtos.DepartmentRequest;
import com.scd.backend.employeesmanagement.Dtos.DepartmentResponse;
import com.scd.backend.employeesmanagement.Dtos.EmployeeResponse;
import com.scd.backend.employeesmanagement.Entity.Department;
import com.scd.backend.employeesmanagement.Entity.Employee;
import com.scd.backend.employeesmanagement.Exception.departmentExceptions.DepartmentCreationException;
import com.scd.backend.employeesmanagement.Exception.departmentExceptions.DepartmentNotFoundException;
import com.scd.backend.employeesmanagement.Exception.departmentExceptions.DepartmentUpdateException;
import com.scd.backend.employeesmanagement.Exception.employeeExceptions.EmployeeNotFoundException;
import com.scd.backend.employeesmanagement.Repository.IDepartmentRepository;
import com.scd.backend.employeesmanagement.Repository.IEmployeeRepository;
import com.scd.backend.employeesmanagement.Utils.DepartmentMapper;
import com.scd.backend.employeesmanagement.Utils.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final IDepartmentRepository departmentRepository;
    private final IEmployeeRepository employeeRepository;

    public ResponseEntity<Object> saveDepartment(DepartmentRequest departmentRequest) {
        try {
            Department department = Department.builder()
                    .description(departmentRequest.getDescription())
                    .imageUri(departmentRequest.getImageUri())
                    .build();
            departmentRepository.save(department);
            return ResponseEntity.ok("Department saved successfully.");
        } catch (DepartmentCreationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public List<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(DepartmentMapper::mapToDepartmentResponse).toList();
    }

    public DepartmentResponse getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException(id));
        return DepartmentMapper.mapToDepartmentResponse(department);
    }

    public DepartmentResponse updateDepartment(Long id, DepartmentRequest departmentRequest) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
        existingDepartment.setDescription(departmentRequest.getDescription());
        existingDepartment.setImageUri(departmentRequest.getImageUri());
        if(existingDepartment.getDescription() != null) {
            departmentRepository.saveAndFlush(existingDepartment);
            return DepartmentMapper.mapToDepartmentResponse(existingDepartment);
        }
        else {
            throw new DepartmentUpdateException();
        }
    }

    public ResponseEntity<Object> deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException(id));

        for (Employee employee : department.getEmployees()) {
            employee.setDepartment(null);
        }

        Department parentDepartment = department.getParentDepartment();
        if (parentDepartment != null) {
            parentDepartment.getSubdepartments().remove(department);
        }

        for (Department subdepartment : department.getSubdepartments()) {
            subdepartment.setParentDepartment(null);
        }

        departmentRepository.delete(department);
        return ResponseEntity.ok("Department deleted successfully.");
    }


    public List<EmployeeResponse> getEmployeesInDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException(departmentId));
        return department.getEmployees().stream().map(EmployeeMapper::mapToEmployeeResponse).toList();
    }

    public ResponseEntity<Object> addEmployeeToDepartment(Long employeeId, Long departmentId) {
        try {
            Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException(departmentId));
            Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));

            department.getEmployees().add(employee);
            departmentRepository.saveAndFlush(department);

            employee.setDepartment(department);
            employeeRepository.saveAndFlush(employee);

            return ResponseEntity.ok("Employee added to the department successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<Object> addSubdepartment(Long departmentId, Long subdepartmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException(departmentId));
        Department subdepartment = departmentRepository.findById(subdepartmentId).orElseThrow(() -> new DepartmentNotFoundException(subdepartmentId));

        department.getSubdepartments().add(subdepartment);
        departmentRepository.save(department);

        subdepartment.setParentDepartment(department);
        departmentRepository.save(subdepartment);

        return ResponseEntity.ok("Subdepartment added to department successfully");
    }

    public List<DepartmentResponse> getAllSubdepartments(Long parentDepartmentId){
        List<Department> subdepartments = departmentRepository.findByParentDepartmentId(parentDepartmentId);
        return subdepartments.stream()
                .map(DepartmentMapper::mapToDepartmentResponse)
                .collect(Collectors.toList());
    }
}