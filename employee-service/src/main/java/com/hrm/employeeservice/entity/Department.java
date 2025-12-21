package com.hrm.employeeservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "department")
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "head_employee_id")
    private Long headEmployeeId;

    private String description;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}

