package com.hrm.requestapprovalservice.entity;

import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.enums.LeaveType;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Table(name = "leave_request",
        indexes = @Index(name = "idx_leave_employee_date",
                columnList = "employee_id, from_date, to_date"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "employee_id", nullable = false)
    Long employeeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "leave_type", nullable = false)
    LeaveType leaveType;

    @Column(name = "from_date", nullable = false)
    LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    LocalDate toDate;

    @Column(name = "total_days", nullable = false, precision = 5, scale = 2)
    BigDecimal totalDays;

    @Column(columnDefinition = "TEXT")
    String reason;

    @Column(name = "attachment_url")
    String attachmentUrl;

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
