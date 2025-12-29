package com.hrm.requestapprovalservice.entity;

import com.hrm.requestapprovalservice.enums.ApprovalStatus;
import com.hrm.requestapprovalservice.enums.RemoteWorkType;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "remote_request",
        indexes = @Index(name = "idx_remote_employee_date",
                columnList = "employee_id, remote_date"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemoteRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "employee_id", nullable = false)
    Long employeeId;

    @Column(name = "remote_date", nullable = false)
    LocalDate remoteDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_type", nullable = false)
    RemoteWorkType workType;

    @Column(nullable = false, columnDefinition = "TEXT")
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
