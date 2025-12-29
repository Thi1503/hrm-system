package com.hrm.requestapprovalservice.entity;

import com.hrm.requestapprovalservice.enums.ApprovalAction;
import com.hrm.requestapprovalservice.enums.ApproverRole;
import com.hrm.requestapprovalservice.enums.RequestType;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;


@Entity
@Table(name = "approval_history",
        indexes = {
                @Index(name = "idx_approval_request",
                        columnList = "request_type, request_id"),
                @Index(name = "idx_approval_approver",
                        columnList = "approver_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApprovalHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type", nullable = false)
    RequestType requestType;

    @Column(name = "request_id", nullable = false)
    Long requestId;

    @Column(name = "approver_id", nullable = false)
    Long approverId;

    @Enumerated(EnumType.STRING)
    @Column(name = "approver_role", nullable = false)
    ApproverRole approverRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ApprovalAction action;

    @Column(columnDefinition = "TEXT")
    String comment;

    @Column(name = "approved_at", nullable = false)
    LocalDateTime approvedAt;

    @PrePersist
    void prePersist() {
        approvedAt = LocalDateTime.now();
    }
}

