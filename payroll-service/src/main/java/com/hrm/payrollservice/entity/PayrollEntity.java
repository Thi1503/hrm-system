package com.hrm.payrollservice.entity;

import com.hrm.payrollservice.enums.PayrollStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "payroll",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_payroll_employee_month",
                columnNames = {"employee_id", "month"}
        ),
        indexes = {
                @Index(name = "idx_payroll_employee", columnList = "employee_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "employee_id", nullable = false)
    Long employeeId;

    @Column(nullable = false, length = 7)
    String month;

    @Column(nullable = false)
    BigDecimal grossSalary;

    @Column(nullable = false)
    BigDecimal  totalDeduction;

    @Column(nullable = false)
    BigDecimal  netSalary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    PayrollStatus status;

    @Column(nullable = false)
    LocalDateTime createdAt;

    LocalDateTime approvedAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
        if (status == null) status = PayrollStatus.DRAFT;
    }
}
