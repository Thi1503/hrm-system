package com.hrm.payrollservice.entity;

import com.hrm.payrollservice.enums.WorkType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "timesheet_daily",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_timesheet_day",
                columnNames = {"timesheet_month_id", "work_date"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimesheetDailyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "timesheet_month_id", nullable = false)
    Long timesheetMonthId;

    @Column(name = "work_date", nullable = false)
    LocalDate workDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    WorkType workType;

    @Column(nullable = false)
    Integer workMinutes;

    @Column(nullable = false)
    Integer lateMinutes;

    @Column(nullable = false)
    Integer earlyMinutes;

    @Column(nullable = false)
    Integer otMinutes;

    @Column(columnDefinition = "TEXT")
    String note;

    @Column(nullable = false)
    LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
        if (workType == null) workType = WorkType.NORMAL;
    }
}
