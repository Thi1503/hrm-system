package com.hrm.requestapprovalservice.entity;

import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "ot_request",
        indexes = @Index(name = "idx_ot_employee_date",
                columnList = "employee_id, ot_date"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "employee_id", nullable = false)
    Long employeeId;

    @Column(name = "ot_date", nullable = false)
    LocalDate otDate;

    @Column(name = "start_time", nullable = false)
    LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    LocalTime endTime;

    @Column(name = "total_hours", nullable = false, precision = 5, scale = 2)
    BigDecimal totalHours;

    @Column(columnDefinition = "TEXT")
    String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ApprovalStatus status;

    @Column(name = "created_at", updatable = false)
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        if (status == null) {
            status = ApprovalStatus.PENDING_MANAGER;
        }
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

