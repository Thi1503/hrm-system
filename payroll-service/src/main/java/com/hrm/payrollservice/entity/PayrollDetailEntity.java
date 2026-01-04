package com.hrm.payrollservice.entity;

import com.hrm.payrollservice.enums.PayrollComponentType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "payroll_detail",
        indexes = {
                @Index(name = "idx_payroll_detail_payroll", columnList = "payroll_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "payroll_id", nullable = false)
    Long payrollId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    PayrollComponentType componentType;

    @Column(nullable = false)
    BigDecimal amount;

    @Column(length = 255)
    String description;

    @Column(nullable = false)
    LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
