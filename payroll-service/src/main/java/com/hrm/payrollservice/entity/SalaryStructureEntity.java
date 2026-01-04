package com.hrm.payrollservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "salary_structure",
        indexes = {
                @Index(name = "idx_salary_employee", columnList = "employee_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryStructureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "employee_id", nullable = false)
    Long employeeId;

    @Column(nullable = false)
    Double baseSalary;

    @Column(nullable = false)
    Double allowance;

    @Column(nullable = false)
    Double otRate;

    @Column(nullable = false)
    Double latePenaltyPerMin;

    @Column(nullable = false)
    Double earlyPenaltyPerMin;

    @Column(nullable = false)
    LocalDate effectiveFrom;

    @Column(nullable = false)
    LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
