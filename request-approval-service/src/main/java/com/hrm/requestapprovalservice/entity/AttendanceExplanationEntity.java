package com.hrm.requestapprovalservice.entity;

import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.enums.ExplanationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance_explanation",
        indexes = @Index(name = "idx_explanation_employee_date",
                columnList = "employee_id, work_date"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceExplanationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "employee_id", nullable = false)
    Long employeeId;

    @Column(name = "work_date", nullable = false)
    LocalDate workDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "explanation_type", nullable = false)
    ExplanationType explanationType;

    @Column(nullable = false, columnDefinition = "TEXT")
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
