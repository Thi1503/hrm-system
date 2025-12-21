package com.hrm.employeeservice.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_relationship")
@Getter @Setter
public class EmployeeRelationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "relationship_type", nullable = false)
    private String relationshipType;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "citizen_id")
    private String citizenId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}

