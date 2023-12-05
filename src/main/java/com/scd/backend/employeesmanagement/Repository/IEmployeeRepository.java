package com.scd.backend.employeesmanagement.Repository;

import com.scd.backend.employeesmanagement.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee,Long> {
    @Query("SELECT e FROM Employee e WHERE e.department.id = :departmentId " +
            "OR e.department.parentDepartment.id = :departmentId " +
            "OR e.department.id IN (SELECT d.id FROM Department d WHERE d.parentDepartment.id = :departmentId)")
    List<Employee> findAllEmployeesInDepartmentAndSubdepartments(@Param("departmentId") Long departmentId);


    List<Employee> findByManagerId(Long managerId);
}