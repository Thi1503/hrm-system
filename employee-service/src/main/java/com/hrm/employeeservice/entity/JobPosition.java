package com.hrm.employeeservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name = "job_position")
@Getter @Setter
public class JobPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer level;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}

