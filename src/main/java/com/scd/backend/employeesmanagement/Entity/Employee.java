package com.scd.backend.employeesmanagement.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    private Long id;

    private String name;
    private String email;
    private String imageUri;

    @ManyToOne
    @JoinColumn(name = "department_id",  referencedColumnName = "id", foreignKey = @ForeignKey(name = "departmentKey"))
    private Department department;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @OneToMany(mappedBy = "manager")
    private List<Employee> subordinates = new ArrayList<>();
}