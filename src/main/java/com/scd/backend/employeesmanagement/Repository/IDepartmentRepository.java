package com.scd.backend.employeesmanagement.Repository;

import com.scd.backend.employeesmanagement.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findByParentDepartmentId(Long parentDepartmentId);
}
