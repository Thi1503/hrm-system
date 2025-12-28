package com.hrm.attendanceservice.entity;

import com.hrm.attendanceservice.entity.AttendanceStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "attendance_daily_summary",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_employee_work_date",
                columnNames = {"employee_id", "work_date"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceDailySummaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "employee_id", nullable = false)
    Long employeeId;

    @Column(name = "work_date", nullable = false)
    LocalDate workDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_id", nullable = false)
    AttendanceShiftEntity shift;

    @Column(name = "check_in_time")
    LocalDateTime checkInTime;

    @Column(name = "check_out_time")
    LocalDateTime checkOutTime;

    @Column(name = "late_minutes", nullable = false)
    Integer lateMinutes;

    @Column(name = "early_minutes", nullable = false)
    Integer earlyMinutes;

    @Column(name = "work_minutes", nullable = false)
    Integer workMinutes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    AttendanceStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;
}
