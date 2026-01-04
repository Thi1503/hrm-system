package com.hrm.payrollservice.entity;

import com.hrm.payrollservice.enums.TimesheetStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "timesheet_month",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_timesheet_employee_month",
                columnNames = {"employee_id", "month"}
        ),
        indexes = {
                @Index(name = "idx_timesheet_month_employee", columnList = "employee_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimesheetMonthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "employee_id", nullable = false)
    Long employeeId;

    @Column(nullable = false, length = 7)
    String month; // YYYY-MM

    @Column(nullable = false)
    Double totalWorkDays;

    @Column(nullable = false)
    Integer totalWorkMinutes;

    @Column(nullable = false)
    Integer lateMinutes;

    @Column(nullable = false)
    Integer earlyMinutes;

    @Column(nullable = false)
    Double otHours;

    @Column(nullable = false)
    Double leaveDays;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TimesheetStatus status;

    @Column(nullable = false)
    LocalDateTime createdAt;

    @Column(nullable = false)
    LocalDateTime updatedAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        if (status == null) status = TimesheetStatus.DRAFT;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
