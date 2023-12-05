package com.scd.backend.employeesmanagement.Services;

import com.scd.backend.employeesmanagement.Dtos.EmployeeRequest;
import com.scd.backend.employeesmanagement.Dtos.EmployeeResponse;
import com.scd.backend.employeesmanagement.Entity.Employee;
import com.scd.backend.employeesmanagement.Exception.employeeExceptions.EmployeeCreationException;
import com.scd.backend.employeesmanagement.Exception.employeeExceptions.EmployeeNotFoundException;
import com.scd.backend.employeesmanagement.Exception.employeeExceptions.EmployeeUpdateException;
import com.scd.backend.employeesmanagement.Repository.IEmployeeRepository;
import com.scd.backend.employeesmanagement.Utils.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final IEmployeeRepository employeeRepository;

    public ResponseEntity<Object> saveEmployee(EmployeeRequest employeeRequest) {
        try {
            Employee employee = Employee.builder()
                    .email(employeeRequest.getEmail())
                    .name(employeeRequest.getName())
                    .imageUri(employeeRequest.getImageUri())
                    .build();
            employeeRepository.save(employee);
            return ResponseEntity.ok("Employee saved successfully");
        } catch (EmployeeCreationException e) {
            throw new EmployeeCreationException();
        }
    }

    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeMapper::mapToEmployeeResponse).toList();
    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return EmployeeMapper.mapToEmployeeResponse(employee);
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeRequest) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        existingEmployee.setName(employeeRequest.getName());
        existingEmployee.setEmail(employeeRequest.getEmail());
        existingEmployee.setImageUri(employeeRequest.getImageUri());
        if (existingEmployee.getEmail() != null && existingEmployee.getName() != null) {
            employeeRepository.saveAndFlush(existingEmployee);
            return EmployeeMapper.mapToEmployeeResponse(existingEmployee);
        } else {
            throw new EmployeeUpdateException();
        }
    }

    public ResponseEntity<Object> deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        Employee manager = employee.getManager();

        if (manager != null) {
            manager.getSubordinates().remove(employee);
            employee.setManager(null);
        }

        for (Employee subordinate : employee.getSubordinates()) {
            subordinate.setManager(null);
        }

        employeeRepository.delete(employee);

        return ResponseEntity.ok("Employee deleted successfully");
    }


    public List<EmployeeResponse> getAllEmployeesInDepartmentAndSubdepartments(Long departmentId) {
       return  employeeRepository.findAllEmployeesInDepartmentAndSubdepartments(departmentId).stream().map(EmployeeMapper::mapToEmployeeResponse).toList();
    }

    public ResponseEntity<Object> addEmployeeToManager(Long managerId, Long subordinateId) {
        Employee manager = employeeRepository.findById(managerId).orElseThrow(() -> new EmployeeNotFoundException(managerId));
        Employee subordinate = employeeRepository.findById(subordinateId).orElseThrow(() -> new EmployeeNotFoundException(subordinateId));

        manager.getSubordinates().add(subordinate);
        employeeRepository.save(manager);

        subordinate.setManager(manager);
        employeeRepository.save(subordinate);

        return ResponseEntity.ok("Subordinate added to Manager successfully.");
    }

    public List<EmployeeResponse> getAllSubordinates(Long managerId) {
        return employeeRepository.findByManagerId(managerId).stream().map(EmployeeMapper::mapToEmployeeResponse).toList();
    }
}


