package com.hrm.notificationservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "notification_event_log",
        indexes = {
                @Index(name = "idx_event_log_employee", columnList = "target_employee_id"),
                @Index(name = "idx_event_log_event", columnList = "event_type")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationEventLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "notification_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_event_log_notification")
    )
    NotificationEntity notification;

    @Column(name = "event_type", nullable = false)
    String eventType;

    @Column(name = "target_employee_id", nullable = false)
    Long targetEmployeeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    NotificationEventStatus status;

    @Column(name = "error_message")
    String errorMessage;

    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
