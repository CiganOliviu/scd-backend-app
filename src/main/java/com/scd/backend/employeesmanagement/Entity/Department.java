package com.scd.backend.employeesmanagement.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "department")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String description;
    private String imageUri;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_department_id")
    private Department parentDepartment;

    @OneToMany(mappedBy = "parentDepartment")
    private List<Department> subdepartments = new ArrayList<>();

}