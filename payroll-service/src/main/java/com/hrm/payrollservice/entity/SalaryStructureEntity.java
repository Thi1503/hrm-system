package com.hrm.payrollservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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
    BigDecimal baseSalary;

    @Column(nullable = false)
    BigDecimal allowance;

    @Column(nullable = false)
    BigDecimal  otRate;

    @Column(nullable = false)
    BigDecimal latePenaltyPerMin;

    @Column(nullable = false)
    BigDecimal earlyPenaltyPerMin;

    @Column(nullable = false)
    LocalDate effectiveFrom;

    @Column(nullable = false)
    LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
